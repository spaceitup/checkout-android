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
        } else {
            showPaymentSession(this.session);
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
     * Notify this presenter that the user has clicked the action button in the PaymentCard.
     * The presenter will validate if the operation is supported and then post it to the Payment API.
     *
     * @param card the PaymentCard containing the operation URL
     * @param widgets containing the user input data
     */
    void onActionClicked(PaymentCard card, Map<String, FormWidget> widgets) {

        if (session.getPresetCard() == card) {
            PaymentResult result = new PaymentResult("Same presetAccount selected");
            closeSessionWithOkCode(result);
            return;
        }
        if (!validateWidgets(card, widgets)) {
            return;
        }
        try {
            operation = createOperation(card, widgets);
            networkService = NetworkServiceLookup.getService(card.getCode());
            networkService.setPresenter(this);
            networkService.preparePayment(view.getActivity(), 0, session, operation);                
        } catch (PaymentException e) {
            closeSessionWithErrorCode(R.string.pmdialog_error_unknown, e);
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
                handleLoadInteractionProceed(session);
                break;
            default:
                PaymentResult result = new PaymentResult(listResult.getResultInfo(), interaction);
                closeSessionWithCanceledCode(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionError(Throwable cause) {
        if (cause instanceof PaymentException) {
            handleLoadPaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithErrorCode(R.string.pmdialog_error_unknown, cause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress() {
        view.showProgress();
    }

    @Override
    public void showConnErrorDialog(final ThemedDialogListener listener) {
        view.showConnErrorDialog(listener);
    }

    @Override
    public void showMessageDialog(final String message, final ThemedDialogListener listener) {
        view.showMessageDialog(message, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPreparePaymentResult(int resultCode, PaymentResult paymentResult) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                networkService.processPayment(view.getActivity(), PROCESSPAYMENT_REQUEST_CODE, session, operation);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                view.passOnPaymentResult(PaymentUI.RESULT_CODE_ERROR, paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handlePaymentResultCanceled(paymentResult);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onProcessPaymentResult(int resultCode, PaymentResult paymentResult) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
            case PaymentUI.RESULT_CODE_ERROR:
                view.passOnPaymentResult(resultCode, paymentResult);
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handlePaymentResultCanceled(paymentResult);
                break;
        }
    }

    private void showPaymentSession(PaymentSession cachedSession) {
        if (cachedSession != null && cachedSession.isListUrl(this.listUrl)) {
            this.session = cachedSession;
            view.showPaymentSession(cachedSession);
        } else {
            loadPaymentSession(this.listUrl);
        }
    }

    private void handleActivityResult(ActivityResult result) {
        // REMIND: This is an error since we expect the session, operation and networkService to be present.
        // Here we should show the user an error and exit the page.
        if (session == null) {
            return;
        }
        switch (result.requestCode) {
            case PREPAREPAYMENT_REQUEST_CODE:
                onPreparePaymentResult(result.resultCode, result.paymentResult);
                break;
            case PROCESSPAYMENT_REQUEST_CODE:
                onProcessPaymentResult(result.resultCode, result.paymentResult);
                break;
            default:
                showPaymentSession(this.session);
        }
    }

    private void handlePaymentResultCanceled(PaymentResult result) {
        Interaction interaction = result.getInteraction();
        if (interaction == null) {
            showPaymentSession(this.session);
            return;
        }
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                loadPaymentSession(this.listUrl);
                break;
            case InteractionCode.RETRY:
                showPaymentSession(this.session);
                break;
            default:
                view.passOnPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        }
    }

    private void handleLoadInteractionProceed(PaymentSession session) {
        this.session = session;

        if (reloadInteraction != null) {
            showInteractionMessage(reloadInteraction);
            reloadInteraction = null;
        }
        view.showPaymentSession(session);
    }

    private void handleLoadPaymentError(PaymentException cause) {
        PaymentError error = cause.error;
        ErrorInfo info = error.errorInfo;

        if (info != null) {
            closeSessionWithCanceledCode(new PaymentResult(info.getResultInfo(), info.getInteraction()));
        } else if (error.isError(PaymentError.CONN_ERROR)) {
            handleLoadConnError(cause);
        } else {
            closeSessionWithErrorCode(R.string.pmdialog_error_unknown, cause);
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
        Operation operation = new Operation(url);

        for (FormWidget widget : widgets.values()) {
            widget.putValue(operation);
        }
        return operation;
    }
    
    private void reloadPaymentSession(PaymentResult result) {
        this.reloadInteraction = result.getInteraction();
        loadPaymentSession(this.listUrl);
    }

    private void showInteractionMessage(Interaction interaction) {
        String msg = translateInteraction(interaction, null);
        if (!TextUtils.isEmpty(msg)) {
            view.showMessageDialog(msg, null);
        }
    }

    private void continueSessionWithWarning(PaymentResult result) {
        view.showPaymentSession(this.session);
        showInteractionMessage(result.getInteraction());
    }

    private void closeSessionWithOkCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_OK, result);
        view.closePage();
    }

    private void closeSessionWithCanceledCode(PaymentResult result) {
        String msg = translateInteraction(result.getInteraction(), getString(R.string.pmdialog_error_unknown));
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        closePageWithMessage(msg);
    }

    private void closeSessionWithErrorCode(int msgResId, Throwable cause) {
        PaymentResult result;

        if (cause instanceof PaymentException) {
            PaymentException pe = (PaymentException) cause;
            result = new PaymentResult(pe.getMessage(), pe.error);
        } else {
            String resultInfo = cause.toString();
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, resultInfo);
            result = new PaymentResult(resultInfo, error);
        }
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        closePageWithMessage(getString(msgResId));
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.clearList();
        view.showProgress();
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void handleLoadConnError(final PaymentException pe) {
        PaymentResult result = new PaymentResult(pe.getMessage(), pe.error);
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        view.showConnErrorDialog(new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                switch (which) {
                    case ThemedDialogFragment.BUTTON_POSITIVE:
                        loadPaymentSession(listUrl);
                        break;
                    default:
                        view.closePage();
                }
            }
            @Override
            public void onDismissed(ThemedDialogFragment dialog) {
                view.closePage();
            }
        });
    }

    private String translateInteraction(Interaction interaction, String defMessage) {
        if (session == null || interaction == null) {
            return defMessage;
        }
        String msg = session.getLang().translateInteraction(interaction);
        return TextUtils.isEmpty(msg) ? defMessage : msg;
    }

    private void closePageWithMessage(String message) {
        view.showMessageDialog(message, new ThemedDialogListener() {
            @Override
            public void onButtonClicked(ThemedDialogFragment dialog, int which) {
                view.closePage();
            }
            @Override
            public void onDismissed(ThemedDialogFragment dialog) {
                view.closePage();
            }
        });
    }

    private String getString(int resId) {
        return view.getActivity().getString(resId);
    }
}
