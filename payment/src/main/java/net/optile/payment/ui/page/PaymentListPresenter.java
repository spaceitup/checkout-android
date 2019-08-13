/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import java.net.URL;
import java.util.Map;

import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.OperationResult;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.NetworkServiceLookup;
import net.optile.payment.ui.service.NetworkServicePresenter;
import net.optile.payment.ui.service.PaymentSessionService;
import net.optile.payment.ui.service.PaymentSessionListener;
import net.optile.payment.ui.widget.FormWidget;

/**
 * The PaymentListPresenter implementing the presenter part of the MVP
 */
final class PaymentListPresenter implements PaymentSessionListener, NetworkServicePresenter {

    private final static int PREPAREPAYMENT_REQUEST_CODE = 1;
    private final static int PROCESSPAYMENT_REQUEST_CODE = 2;
    private final static int CHARGEPAYMENT_REQUEST_CODE = 3;

    private final PaymentListView view;
    private final PaymentSessionService sessionService;

    private PaymentSession session;
    private String listUrl;
    private Interaction reloadInteraction;

    private Operation operation;
    private ActivityResult activityResult;
    private NetworkService networkService;
    
    /**
     * Create a new PaymentListPresenter
     *
     * @param view The PaymentListView displaying the payment list
     */
    PaymentListPresenter(PaymentListView view) {
        this.view = view;
        sessionService = new PaymentSessionService();
        sessionService.setListener(this);
    }

    /**
     * Start the presenter
     */
    void onStart() {
        this.listUrl = PaymentUI.getInstance().getListUrl();

        if (activityResult != null) {
            handleActivityResult(activityResult);
            activityResult = null;
        } else if (this.session != null && this.session.isListUrl(this.listUrl)) {
            view.showPaymentSession(this.session);
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
     * Notify this presenter that the user has clicked the action button in a PaymentCard.
     * The presenter will validate the widgets and if valid, post the operation to the Payment API
     * using one of the network services.
     *
     * @param card the PaymentCard containing the operation URL
     * @param widgets containing the user input data
     */
    void onActionClicked(PaymentCard card, Map<String, FormWidget> widgets) {

        if (operation != null) {
            return;
        }
        if (session.getPresetCard() == card) {
            PaymentResult result = new PaymentResult("Same presetAccount selected");
            closeWithOkCode(result);
            return;
        }
        if (validateWidgets(card, widgets)) {
            try {
                operation = createOperation(card, widgets);
                networkService = NetworkServiceLookup.getService(card.getCode());
                networkService.setPresenter(this);
                networkService.preparePayment(view.getActivity(), 0, operation);                
            } catch (PaymentException e) {
                closeWithErrorCode(e);
            }
        }
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
        if (cause instanceof PaymentException) {
            handleLoadSessionError((PaymentException) cause);
            return;
        }
        closeWithErrorCode(cause);
    }

    private void handleLoadSessionOk(PaymentSession session) {
        this.operation = null;
        this.session = session;

        if (reloadInteraction != null) {
            showInteractionDialog(reloadInteraction);
            reloadInteraction = null;
        }
        view.showPaymentSession(session);
    }

    private void handleLoadSessionError(PaymentException cause) {
        PaymentError error = cause.error;
        ErrorInfo info = error.errorInfo;

        if (info != null) {
            closeWithCanceledCode(new PaymentResult(info.getResultInfo(), info.getInteraction()));
        } else if (error.isError(PaymentError.CONN_ERROR)) {
            handleLoadSessionConnError(cause);
        } else {
            closeWithErrorCode(cause.getMessage(), error);
        }
    }

    private void handleLoadSessionConnError(PaymentException pe) {
        PaymentResult result = new PaymentResult(pe.getMessage(), pe.error);
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

    private void handleActivityResult(ActivityResult result) {
        if (this.session == null) {
            String message = "Missing cached session in PaymentListPresenter";
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
            closeWithErrorCode(message, error);
            return;
        }
        switch (result.requestCode) {
            case PREPAREPAYMENT_REQUEST_CODE:
                onPreparePaymentResult(result.resultCode, result.paymentResult);
                break;
            case PROCESSPAYMENT_REQUEST_CODE:
                onProcessPaymentResult(result.resultCode, result.paymentResult);
                break;
            case CHARGEPAYMENT_REQUEST_CODE:
                onChargePaymentResult(result.resultCode, result.paymentResult);
                break;
            default:
                view.showPaymentSession(this.session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onPreparePaymentResult(int resultCode, PaymentResult paymentResult) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                handlePreparePaymentOk(paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handlePreparePaymentCanceled(paymentResult);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handlePreparePaymentError(paymentResult);
                break;
        }
    }

    private void handlePreparePaymentOk(PaymentResult result) {
        if (Operation.CHARGE.equals(operation.getType())) {
            view.showChargePaymentScreen(CHARGEPAYMENT_REQUEST_CODE, operation);
        } else {
            networkService.processPayment(view.getActivity(), PROCESSPAYMENT_REQUEST_CODE, operation);
        }
    }

    private void handlePreparePaymentCanceled(PaymentResult result) {
        this.operation = null;
        Interaction interaction = result.getInteraction();
        if (interaction == null) {
            view.showPaymentSession(session);
            return;
        }
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                reloadPaymentSession(result);
                break;
            case InteractionCode.RETRY:
                showPaymentSessionWithMessage(result);
                break;
            default:
                closeWithCanceledCode(result);
        }
    }

    private void handlePreparePaymentError(PaymentResult result) {
        this.operation = null;
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        if (!result.getPaymentError().isError(PaymentError.CONN_ERROR)) {
            view.close();
            return;
        }
        view.showConnectionDialog(new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                switch (which) {
                    case ThemedDialogFragment.BUTTON_POSITIVE:
                        networkService.preparePayment(view.getActivity(), PREPAREPAYMENT_REQUEST_CODE, operation);
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
    public void onProcessPaymentResult(int resultCode, PaymentResult paymentResult) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                closeWithOkCode(paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handleProcessPaymentCanceled(paymentResult);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handleProcessPaymentError(paymentResult);
                break;
        }
    }

    private void handleProcessPaymentCanceled(PaymentResult paymentResult) {
        this.operation = null;
        Interaction interaction = paymentResult.getInteraction();
        if (interaction == null) {
            view.showPaymentSession(this.session);
            return;
        }
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                reloadPaymentSession(paymentResult);
                break;
            case InteractionCode.RETRY:
                showPaymentSessionWithMessage(paymentResult);
                break;
            default:
                closeWithCanceledCode(paymentResult);
        }
    }

    private void handleProcessPaymentError(PaymentResult paymentResult) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, paymentResult);
        if (!paymentResult.getPaymentError().isError(PaymentError.CONN_ERROR)) {
            view.close();
            return;
        }
        view.showConnectionDialog(new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                switch (which) {
                    case ThemedDialogFragment.BUTTON_POSITIVE:
                        networkService.processPayment(view.getActivity(), PROCESSPAYMENT_REQUEST_CODE, operation);
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
     * The Charge payment result is received from the ChargePaymentActivity. Error messages are not displayed by this presenter since 
     * the ChargePaymentActivity has taken care of displaying error and warning messages.   
     * 
     * @param resultCode
     * @param paymentResult 
     */
    private void onChargePaymentResult(int resultCode, PaymentResult paymentResult) {
        this.operation = null;
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
            case PaymentUI.RESULT_CODE_ERROR:
                view.passOnPaymentResult(resultCode, paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handleChargePaymentCanceled(paymentResult);
                break;
        }
    }

    private void handleChargePaymentCanceled(PaymentResult paymentResult) {
        Interaction interaction = paymentResult.getInteraction();
        if (interaction == null) {
            view.showPaymentSession(this.session);
            return;
        }
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                loadPaymentSession(this.listUrl);
                break;
            case InteractionCode.RETRY:
                view.showPaymentSession(this.session);
                break;
            default:
                view.passOnPaymentResult(PaymentUI.RESULT_CODE_CANCELED, paymentResult);
        }
    }

    private boolean validateWidgets(PaymentCard card, Map<String, FormWidget> widgets) {
        boolean error = false;
        for (FormWidget widget : widgets.values()) {
            if (!widget.validate()) {
                error = true;
            }
            widget.clearFocus();
        }
        return !error;
    }

    private Operation createOperation(PaymentCard card, Map<String, FormWidget> widgets) throws PaymentException {
        URL url = card.getOperationLink();
        Operation operation = new Operation(card.getCode(), url);

        for (FormWidget widget : widgets.values()) {
            widget.putValue(operation);
        }
        return operation;
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

    private void closeWithErrorCode(Throwable cause) {
        String message = cause.toString();
        PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, message);
        closeWithErrorCode(message, error);
    }

    private void closeWithErrorCode(String message, PaymentError error) {
        PaymentResult result = new PaymentResult(message, error);        
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        closeWithMessage(getString(R.string.pmdialog_error_unknown));        
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

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.clearList();
        view.showProgress();
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void reloadPaymentSession(PaymentResult result) {
        this.reloadInteraction = result.getInteraction();
        loadPaymentSession(this.listUrl);
    }

    private void showPaymentSessionWithMessage(PaymentResult result) {
        view.showPaymentSession(this.session);
        showInteractionDialog(result.getInteraction());
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
