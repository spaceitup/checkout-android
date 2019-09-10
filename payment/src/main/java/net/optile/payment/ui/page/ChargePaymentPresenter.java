/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.NetworkServiceLookup;
import net.optile.payment.ui.service.NetworkServicePresenter;
import net.optile.payment.ui.service.PaymentSessionListener;
import net.optile.payment.ui.service.PaymentSessionService;

/**
 * The ChargePaymentPresenter takes care of posting the operation to the Payment API.
 * First this presenter will load the list, checks if the operation is present in the list and then post the operation to the Payment API.
 */
final class ChargePaymentPresenter implements PaymentSessionListener, NetworkServicePresenter {

    private final static int CHARGE_REQUEST_CODE = 1;

    private final ChargePaymentView view;
    private final PaymentSessionService sessionService;

    private PaymentSession session;
    private String listUrl;

    private Operation operation;
    private ActivityResult activityResult;
    private NetworkService networkService;

    /**
     * Create a new ChargePaymentPresenter
     *
     * @param view The ChargePaymentView displaying the progress animation
     */
    ChargePaymentPresenter(ChargePaymentView view) {
        this.view = view;
        sessionService = new PaymentSessionService();
        sessionService.setListener(this);
    }

    void onStart(Operation operation) {
        this.operation = operation;
        this.listUrl = PaymentUI.getInstance().getListUrl();

        if (activityResult != null) {
            handleActivityResult(activityResult);
            activityResult = null;
        } else {
            loadPaymentSession(this.listUrl);
        }
    }

    /**
     * Notify this presenter that it should stop and cleanup its resources
     */
    void onStop() {
        sessionService.stop();

        if (networkService != null) {
            networkService.stop();
        }
    }

    /**
     * Set the payment result received through the onActivityResult method
     *
     * @param activityResult result to be set and handled when the presenter is started.
     */
    void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionSuccess(PaymentSession session) {
        ListResult listResult = session.getListResult();
        Interaction interaction = listResult.getInteraction();

        switch (interaction.getCode()) {
            case InteractionCode.PROCEED:
                handleLoadSessionOk(session);
                break;
            default:
                PaymentResult result = new PaymentResult(listResult.getResultInfo(), interaction);
                closeWithCanceledCode(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionError(Throwable cause) {
        PaymentResult result = PaymentResult.fromThrowable(cause);
        if (result.hasError()) {
            handleLoadSessionError(result);
        } else {
            closeWithCanceledCode(result);
        }
    }

    private void handleLoadSessionOk(PaymentSession session) {
        if (!session.containsLink("operation", operation.getURL())) {
            String message = "operation not found in ListResult";
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
            closeWithErrorCode(message, error);
            return;
        }
        this.session = session;
        networkService = NetworkServiceLookup.getService(operation.getCode());
        networkService.setPresenter(this);
        processPayment();
    }

    private void handleLoadSessionError(PaymentResult result) {
        PaymentError error = result.getPaymentError();
        if (error.isError(PaymentError.CONN_ERROR)) {
            handleLoadSessionConnError(result);
        } else {
            closeWithErrorCode(result);
        }
    }

    private void handleLoadSessionConnError(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        view.showConnectionDialog(new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                switch (which) {
                    case ThemedDialogFragment.BUTTON_POSITIVE:
                        loadPaymentSession(listUrl);
                        break;
                    default:
                        view.close();
                }
            }

            @Override
            public void onDismissed(ThemedDialogFragment dialog) {
                view.close();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress() {
        view.showProgress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPreparePaymentResult(int resultCode, PaymentResult result) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onProcessPaymentResult(int resultCode, PaymentResult result) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                closeWithOkCode(result);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                closeWithCanceledCode(result);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handleProcessPaymentError(result);
                break;
        }
    }

    private void handleProcessPaymentError(PaymentResult result) {
        if (!result.getPaymentError().isError(PaymentError.CONN_ERROR)) {
            closeWithErrorCode(result);
            return;
        }
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        view.showConnectionDialog(new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                switch (which) {
                    case ThemedDialogFragment.BUTTON_POSITIVE:
                        processPayment();
                        break;
                    default:
                        view.close();
                }
            }

            @Override
            public void onDismissed(ThemedDialogFragment dialog) {
                view.close();
            }
        });
    }

    /**
     * Let this presenter handle the back pressed.
     *
     * @return true when this presenter handled the back press, false otherwise
     */
    boolean onBackPressed() {
        view.showWarningMessage(getString(R.string.pmsnackbar_operation_interrupted));
        return true;
    }

    private void processPayment() {
        try {
            networkService.processPayment(view.getActivity(), CHARGE_REQUEST_CODE, operation);
        } catch (PaymentException e) {
            closeWithErrorCode(e.getMessage(), e.error);
        }
    }

    private void handleActivityResult(ActivityResult result) {
        if (session == null) {
            String message = "Missing cached session in ChargePaymentPresenter";
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
            closeWithErrorCode(message, error);
            return;
        }
        if (result.requestCode == CHARGE_REQUEST_CODE) {
            onProcessPaymentResult(result.resultCode, result.paymentResult);
        }
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.showProgress();
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void closeWithOkCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_OK, result);
        view.close();
    }

    private void closeWithCanceledCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);

        String msg = translateInteraction(result.getInteraction());
        if (TextUtils.isEmpty(msg)) {
            msg = getString(R.string.pmdialog_error_unknown);
        }
        closeWithMessage(msg);
    }

    private void closeWithErrorCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        closeWithMessage(getString(R.string.pmdialog_error_unknown));
    }

    private void closeWithErrorCode(String resultInfo, PaymentError error) {
        closeWithErrorCode(new PaymentResult(resultInfo, error));
    }

    private void closeWithMessage(String message) {
        view.showMessageDialog(message, new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                view.close();
            }

            @Override
            public void onDismissed(ThemedDialogFragment dialog) {
                view.close();
            }
        });
    }

    private void showInteractionDialog(Interaction interaction) {
        String msg = translateInteraction(interaction);
        if (!TextUtils.isEmpty(msg)) {
            view.showMessageDialog(msg, null);
        }
    }

    private String translateInteraction(Interaction interaction) {
        if (session == null || interaction == null) {
            return null;
        }
        return session.getLang().translateInteraction(interaction);
    }

    private String getString(int resId) {
        return view.getActivity().getString(resId);
    }
}
