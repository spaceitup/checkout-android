/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.page;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.R;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.form.Charge;
import net.optile.payment.validation.Validator;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.InteractionReason;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.OperationType;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.widget.FormWidget;

/**
 * The PaymentPagePresenter implementing the presenter part of the MVP
 */
final class PaymentPagePresenter {

    private final static String TAG = "pay_PayPresenter";

    private final PaymentPageView view;
    private final PaymentPageService service;

    private Validator validator;
    private PaymentSession session;
    private String listUrl;
    private Interaction reloadInteraction;

    /**
     * Create a new PaymentPagePresenter
     *
     * @param view The PaymentPageView displaying the payment list
     */
    PaymentPagePresenter(PaymentPageView view) {
        this.view = view;
        this.service = new PaymentPageService(this);
    }

    void onStop() {
        service.stop();
    }

    /**
     * Load the PaymentSession from the Payment API. once loaded, populate the View with the newly loaded groups of payment methods.
     * If a previous session with the same listUrl is available then reuse the existing one.
     *
     * @param context context in which this presenter is running
     * @param listUrl the url pointing to the ListResult in the Payment API
     */
    
    void load(Context context, String listUrl) {

        if (service.isActive()) {
            return;
        }
        this.listUrl = listUrl;

        if (validator != null && session != null && session.isListUrl(listUrl)) {
            // show the cached payment session when all have been previously loaded
            view.showPaymentSession(session);
            return;
        }
        view.showLoading(true);

        if (validator == null) {
            service.loadValidator(context);
        } else {
            loadPaymentSession(listUrl);
        }
    }

    /**
     * Perform the operation specified in the paymentSession for the selected PaymentCard and widgets
     *
     * @param card the PaymentCard containing the operation URL
     * @param widgets containing the user input data
     */
    void performOperation(PaymentCard card, Map<String, FormWidget> widgets) {

        if (service.isActive()) {
            return;
        }
        switch (session.getOperationType()) {
            case OperationType.CHARGE:
                performChargeOperation(card, widgets);
                break;
            default:
                Log.w(TAG, "OperationType not supported");
        }
    }

    /** 
     * Return the Validator stored in this presenter.
     * 
     * @return validator validator used to validate user input values 
     */
    Validator getValidator() {
        return validator;
    }

    /** 
     * Callback from the service when the validator has been successfully loaded
     * 
     * @param validator that has been loaded
     */
    void onValidatorSuccess(Validator validator) {

        if (!view.isActive()) {
            return;
        }
        this.validator = validator;
        loadPaymentSession(this.listUrl);
    }

    /** 
     * Callback from the service that the validator could not be loaded
     * 
     * @param cause containing the error
     */
    void onValidatorError(Throwable cause) {
        closeSessionWithError(R.string.pmpage_error_unknown, cause);
    }
    
    /** 
     * Callback from the service that the PaymentSession has successfully been loaded
     * 
     * @param session that has been loaded from the Payment API
     */
    void onPaymentSessionSuccess(PaymentSession session) {
        Interaction interaction = session.listResult.getInteraction();

        switch (interaction.getCode()) {
            case InteractionCode.PROCEED:
                handleLoadInteractionProceed(session);
                break;
            default:
                PaymentResult result = new PaymentResult(session.listResult.getResultInfo(), interaction);
                cancelSession(result);
        }
    }

    /** 
     * Callback from the service that the PaymentSession failed to load
     * 
     * @param cause containing the reason why the loading failed
     */
    void onPaymentSessionError(Throwable cause) {
        if (cause instanceof PaymentException) {
            handleLoadPaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithError(R.string.pmpage_error_unknown, cause);
    }

    /** 
     * Callback from the service that the charge request was successfull.
     * REMIND: We should rename Charge to Operation. 
     *
     * @param operation operation explaining the result of the charge request
     */
    void onChargeSuccess(OperationResult operation) {
        PaymentResult result = new PaymentResult(operation);

        switch (operation.getInteraction().getCode()) {
            case InteractionCode.PROCEED:
                closeSession(result);
                break;
            default:
                handleChargeInteractionError(result);
        }
    }

    private void onChargeError(Throwable cause) {

        if (cause instanceof PaymentException) {
            handleChargePaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithError(R.string.pmpage_error_unknown, cause);
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
        PaymentResult result;

        if (info != null) {
            cancelSession(new PaymentResult(info.getResultInfo(), info.getInteraction()));
        } else {
            int msgResId;
            switch (error.errorType) {
                case PaymentError.CONN_ERROR:
                    msgResId = R.string.pmpage_error_connection;
                    break;
                default:
                    msgResId = R.string.pmpage_error_unknown;
            }
            closeSessionWithError(msgResId, cause);
        }
    }

    private void performChargeOperation(PaymentCard card, Map<String, FormWidget> widgets) {
        URL url = card.getOperationLink();
        Charge charge = new Charge();

        try {
            boolean error = false;
            for (FormWidget widget : widgets.values()) {

                if (widget.validate()) {
                    widget.putValue(charge);
                } else {
                    error = true;
                }
            }
            if (!error) {
                view.showLoading(true);
                postChargeRequest(url, charge);
            }
        } catch (PaymentException e) {
            closeSessionWithError(R.string.pmpage_error_unknown, e);
        }
    }

    private void reloadPaymentSession(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        this.reloadInteraction = result.getInteraction();
        loadPaymentSession(this.listUrl);
    }

    private void callbackChargeError(Throwable cause) {

        if (cause instanceof PaymentException) {
            handleChargePaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithError(R.string.pmpage_error_unknown, cause);
    }

    private void handleChargePaymentError(PaymentException cause) {
        PaymentError error = cause.error;
        ErrorInfo info = error.errorInfo;

        if (info != null) {
            handleChargeInteractionError(new PaymentResult(info.getResultInfo(), info.getInteraction()));
        } else {
            switch (error.errorType) {
                case PaymentError.CONN_ERROR:
                    continueSessionWithWarning(R.string.pmpage_error_connection, cause);
                    break;
                default:
                    closeSessionWithError(R.string.pmpage_error_unknown, cause);
            }
        }
    }
    
    private void handleChargeInteractionError(PaymentResult result) {
        Interaction interaction = result.getInteraction();

        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_NETWORK:
                reloadPaymentSession(result);
                break;
            case InteractionCode.RETRY:
            case InteractionCode.TRY_OTHER_ACCOUNT:
                continueSessionWithWarning(result);
                break;
            case InteractionCode.ABORT:
                handleChargeInteractionAbort(result);
                break;
            default:
                cancelSession(result);
        }
    }

    private void handleChargeInteractionAbort(PaymentResult result) {
        Interaction interaction = result.getInteraction();

        switch (interaction.getReason()) {
            case InteractionReason.DUPLICATE_OPERATION:
                closeSession(result);
                break;
            default:
                cancelSession(result);
        }
    }

    private void showInteractionMessage(Interaction interaction) {
        String msg = translateInteraction(interaction, null);

        if (!TextUtils.isEmpty(msg)) {
            view.showMessage(msg);
        }
    }

    private void continueSessionWithWarning(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        view.showPaymentSession(this.session);
        showInteractionMessage(result.getInteraction());
    }

    private void continueSessionWithWarning(int msgResId, PaymentException cause) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, new PaymentResult(cause.getMessage(), cause.error));
        view.showPaymentSession(this.session);
        view.showMessage(view.getStringRes(msgResId));
    }

    private void closeSession(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_OK, result);
        view.closePage();
    }

    private void cancelSession(PaymentResult result) {
        String msg = translateInteraction(result.getInteraction(), view.getStringRes(R.string.pmpage_error_unknown));
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        view.closePageWithMessage(msg);
    }

    private void closeSessionWithError(int msgResId, Throwable cause) {
        PaymentResult result;

        if (cause instanceof PaymentException) {
            PaymentException pe = (PaymentException)cause;
            result = new PaymentResult(pe.getMessage(), pe.error);
        } else {
            String resultInfo = cause.toString();
            PaymentError error = new PaymentError("PaymentPage", PaymentError.INTERNAL_ERROR, resultInfo);
            result = new PaymentResult(resultInfo, error);
        }
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        view.closePageWithMessage(view.getStringRes(msgResId));
    }
        
    private String translateInteraction(Interaction interaction, String defMessage) {

        if (session == null || interaction == null) {
            return defMessage;
        }
        String msg = session.getLang().translateInteraction(interaction);
        return TextUtils.isEmpty(msg) ? defMessage : msg;
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.clear();
        service.loadPaymentSession(listUrl);
    }

    private void postChargeRequest(final URL url, final Charge charge) {
        view.showLoading(true);
        service.postChargeRequest(url, charge);
    }
}
