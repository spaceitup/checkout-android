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

import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.R;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.form.Charge;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.InteractionReason;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.OperationType;
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

    private PaymentSession session;
    private int groupType;
    private WorkerTask<OperationResult> chargeTask;
    private WorkerTask<PaymentSession> loadTask;
    private String listUrl;
    private Interaction reloadInteraction;

    /**
     * Create a new PaymentPagePresenter
     *
     * @param view The PaymentPageView displaying the payment list
     */
    PaymentPagePresenter(PaymentPageView view) {
        this.view = view;
        this.service = new PaymentPageService();
    }

    void onStop() {
        if (loadTask != null) {
            loadTask.unsubscribe();
            loadTask = null;
        }
        if (chargeTask != null) {
            chargeTask.unsubscribe();
            chargeTask = null;
        }
    }

    /**
     * Load the PaymentSession from the Payment API. once loaded, populate the View with the newly loaded groups of payment methods.
     * If a previous session with the same listUrl is available then reuse the existing one.
     *
     * @param listUrl the url pointing to the ListResult in the Payment API
     */
    void load(String listUrl) {

        if (loadTask != null) {
            return;
        }
        this.listUrl = listUrl;

        if (session != null && session.isListUrl(listUrl)) {
            view.showPaymentSession(session);
        } else {
            view.showLoading(true);
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

        switch (session.getOperationType()) {
            case OperationType.CHARGE:
                performChargeOperation(card, widgets);
                break;
            default:
                Log.w(TAG, "OperationType not supported");
        }
    }

    private void performChargeOperation(PaymentCard card, Map<String, FormWidget> widgets) {

        if (chargeTask != null) {
            return;
        }
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
            PaymentResult result = new PaymentResult(e.getMessage(), e.error);
            closeSessionWithError(R.string.pmpage_error_unknown, result);
        }
    }

    private void callbackLoadSuccess(PaymentSession session) {
        this.loadTask = null;
        Interaction interaction = session.listResult.getInteraction();

        switch (interaction.getCode()) {
            case InteractionCode.PROCEED:
                handleLoadInteractionProceed(session);
                break;
            default:
                PaymentResult result = new PaymentResult(session.listResult.getResultInfo(), interaction);
                closeSessionWithError(result);
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

    private void callbackLoadError(Throwable cause) {
        this.loadTask = null;

        if (cause instanceof PaymentException) {
            handleLoadPaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithError(createPaymentResult(cause));
    }

    private void handleLoadPaymentError(PaymentException cause) {
        PaymentError error = cause.error;
        ErrorInfo info = error.errorInfo;
        PaymentResult result;

        if (info != null) {
            result = new PaymentResult(info.getResultInfo(), info.getInteraction());
            closeSessionWithError(result);
        } else {
            result = new PaymentResult(cause.getMessage(), error);
            int msgResId;
            switch (error.errorType) {
                case PaymentError.CONN_ERROR:
                    msgResId = R.string.pmpage_error_connection;
                    break;
                default:
                    msgResId = R.string.pmpage_error_unknown;
            }
            closeSessionWithError(msgResId, result);
        }
    }

    private void callbackChargeSuccess(OperationResult operation) {
        this.chargeTask = null;
        PaymentResult result = new PaymentResult(operation);

        switch (operation.getInteraction().getCode()) {
            case InteractionCode.PROCEED:
                closeSessionWithSuccess(result);
                break;
            default:
                handleChargeInteractionError(result);
        }
    }

    private void reloadPaymentSession(PaymentResult result) {
        view.setPaymentResult(false, result);
        this.reloadInteraction = result.getInteraction();
        loadPaymentSession(this.listUrl);
    }

    private void callbackChargeError(Throwable cause) {
        this.chargeTask = null;

        if (cause instanceof PaymentException) {
            handleChargePaymentError((PaymentException) cause);
            return;
        }
        closeSessionWithError(createPaymentResult(cause));
    }

    private void handleChargePaymentError(PaymentException cause) {
        PaymentError error = cause.error;
        ErrorInfo info = error.errorInfo;
        PaymentResult result;

        if (info != null) {
            result = new PaymentResult(info.getResultInfo(), info.getInteraction());
            handleChargeInteractionError(result);
        } else {
            result = new PaymentResult(cause.getMessage(), error);
            switch (error.errorType) {
                case PaymentError.CONN_ERROR:
                    continueSessionWithWarning(R.string.pmpage_error_connection, result);
                    break;
                default:
                    closeSessionWithError(R.string.pmpage_error_unknown, result);
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
                closeSessionWithError(result);
        }
    }

    private void handleChargeInteractionAbort(PaymentResult result) {
        Interaction interaction = result.getInteraction();

        switch (interaction.getReason()) {
            case InteractionReason.DUPLICATE_OPERATION:
                closeSessionWithSuccess(result);
                break;
            default:
                closeSessionWithError(result);
        }
    }

    private void showInteractionMessage(Interaction interaction) {
        String msg = translateInteraction(interaction, null);

        if (!TextUtils.isEmpty(msg)) {
            view.showMessage(msg);
        }
    }

    private void continueSessionWithWarning(PaymentResult result) {
        view.setPaymentResult(false, result);
        view.showPaymentSession(this.session);
        showInteractionMessage(result.getInteraction());
    }

    private void continueSessionWithWarning(int msgResId, PaymentResult result) {
        view.setPaymentResult(false, result);
        view.showPaymentSession(this.session);
        view.showMessage(view.getStringRes(msgResId));
    }

    private void closeSessionWithSuccess(PaymentResult result) {
        view.setPaymentResult(true, result);
        view.closePage();
    }

    private void closeSessionWithError(PaymentResult result) {
        String msg = translateInteraction(result.getInteraction(), view.getStringRes(R.string.pmpage_error_unknown));
        view.setPaymentResult(false, result);
        view.closePageWithMessage(msg);
    }

    private void closeSessionWithError(int msgResId, PaymentResult result) {
        view.setPaymentResult(false, result);
        view.closePageWithMessage(view.getStringRes(msgResId));
    }

    private String translateInteraction(Interaction interaction, String defMessage) {

        if (session == null || interaction == null) {
            return defMessage;
        }
        String msg = session.getLang().translateInteraction(interaction);
        return TextUtils.isEmpty(msg) ? defMessage : msg;
    }

    private int nextGroupType() {
        return groupType++;
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.clear();

        loadTask = WorkerTask.fromCallable(new Callable<PaymentSession>() {
            @Override
            public PaymentSession call() throws PaymentException {
                return service.loadPaymentSession(listUrl);
            }
        });
        loadTask.subscribe(new WorkerSubscriber<PaymentSession>() {
            @Override
            public void onSuccess(PaymentSession paymentSession) {
                callbackLoadSuccess(paymentSession);
            }

            @Override
            public void onError(Throwable cause) {
                callbackLoadError(cause);
            }
        });
        Workers.getInstance().forNetworkTasks().execute(loadTask);
    }

    private void postChargeRequest(final URL url, final Charge charge) {
        view.showLoading(true);

        chargeTask = WorkerTask.fromCallable(new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws PaymentException {
                return service.postChargeRequest(url, charge);
            }
        });
        chargeTask.subscribe(new WorkerSubscriber<OperationResult>() {
            @Override
            public void onSuccess(OperationResult result) {
                callbackChargeSuccess(result);
            }

            @Override
            public void onError(Throwable cause) {
                callbackChargeError(cause);
            }
        });
        Workers.getInstance().forNetworkTasks().execute(chargeTask);
    }

    private PaymentResult createPaymentResult(Throwable cause) {
        String resultInfo = cause.toString();
        PaymentError error = new PaymentError("PaymentPage", PaymentError.INTERNAL_ERROR, resultInfo);
        return new PaymentResult(resultInfo, error);
    }
}
