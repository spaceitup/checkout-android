/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.page;

import static com.payoneer.mrs.payment.model.InteractionCode.PROCEED;
import static com.payoneer.mrs.payment.model.NetworkOperationType.CHARGE;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;
import static com.payoneer.mrs.payment.ui.redirect.RedirectService.INTERACTION_CODE;
import static com.payoneer.mrs.payment.ui.redirect.RedirectService.INTERACTION_REASON;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.form.Operation;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.model.ErrorInfo;
import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.ListResult;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.model.Parameter;
import com.payoneer.mrs.payment.model.Redirect;
import com.payoneer.mrs.payment.ui.PaymentActivityResult;
import com.payoneer.mrs.payment.ui.PaymentResult;
import com.payoneer.mrs.payment.ui.PaymentUI;
import com.payoneer.mrs.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import com.payoneer.mrs.payment.ui.model.PaymentCard;
import com.payoneer.mrs.payment.ui.model.PaymentSession;
import com.payoneer.mrs.payment.ui.model.PresetCard;
import com.payoneer.mrs.payment.ui.redirect.RedirectRequest;
import com.payoneer.mrs.payment.ui.service.LocalizationLoaderListener;
import com.payoneer.mrs.payment.ui.service.LocalizationLoaderService;
import com.payoneer.mrs.payment.ui.service.NetworkService;
import com.payoneer.mrs.payment.ui.service.NetworkServiceLookup;
import com.payoneer.mrs.payment.ui.service.NetworkServicePresenter;
import com.payoneer.mrs.payment.ui.service.PaymentSessionListener;
import com.payoneer.mrs.payment.ui.service.PaymentSessionService;
import com.payoneer.mrs.payment.ui.widget.FormWidget;
import com.payoneer.mrs.payment.util.PaymentResultHelper;
import com.payoneer.mrs.payment.util.PaymentUtils;

import android.app.Activity;
import android.text.TextUtils;

/**
 * The PaymentListPresenter implementing the presenter part of the MVP
 */
final class PaymentListPresenter implements PaymentSessionListener, LocalizationLoaderListener, NetworkServicePresenter {

    private final static int PREPAREPAYMENT_REQUEST_CODE = 1;
    private final static int PROCESSPAYMENT_REQUEST_CODE = 2;
    private final static int CHARGEPAYMENT_REQUEST_CODE = 3;

    private final PaymentListView view;
    private final PaymentSessionService sessionService;
    private final LocalizationLoaderService localizationService;

    private PaymentSession session;
    private String listUrl;
    private Interaction reloadInteraction;

    private Operation operation;
    private PaymentActivityResult paymentActivityResult;
    private NetworkService networkService;

    /**
     * Create a new PaymentListPresenter
     *
     * @param view The PaymentListView displaying the payment list
     */
    PaymentListPresenter(PaymentListView view) {
        this.view = view;
        Activity activity = view.getActivity();
        sessionService = new PaymentSessionService(activity);
        sessionService.setListener(this);

        localizationService = new LocalizationLoaderService(activity);
        localizationService.setListener(this);
    }

    /**
     * Start the presenter
     */
    void onStart() {
        this.listUrl = PaymentUI.getInstance().getListUrl();

        if (paymentActivityResult != null) {
            handlePaymentActivityResult(paymentActivityResult);
            paymentActivityResult = null;
        } else if (this.session != null && this.session.isListUrl(this.listUrl)) {
            loadLocalizations(this.session);
        } else {
            loadPaymentSession(this.listUrl);
        }
    }

    /**
     * Notify this presenter that it should stop and cleanup its resources
     */
    void onStop() {
        sessionService.stop();
        localizationService.stop();

        if (networkService != null) {
            networkService.stop();
        }
    }

    /**
     * Set the payment result received through the onActivityResult method
     *
     * @param paymentActivityResult result to be set and handled when the presenter is started.
     */
    void setPaymentActivityResult(PaymentActivityResult paymentActivityResult) {
        this.paymentActivityResult = paymentActivityResult;
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
            onPresetCardSelected((PresetCard) card);
            return;
        }
        if (!validateWidgets(card, widgets)) {
            return;
        }
        try {
            operation = createOperation(card, widgets);
            String code = card.getCode();
            String method = card.getPaymentMethod();
            networkService = NetworkServiceLookup.createService(view.getActivity(), code, method);
            networkService.setPresenter(this);

            if (CHARGE.equals(operation.getOperationType())) {
                view.showChargePaymentScreen(CHARGEPAYMENT_REQUEST_CODE, operation);
            } else {
                processPayment();
            }
        } catch (PaymentException e) {
            closeWithErrorCode(PaymentResultHelper.fromThrowable(e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionError(Throwable cause) {
        handleLoadingError(cause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionSuccess(PaymentSession session) {
        ListResult listResult = session.getListResult();
        Interaction interaction = listResult.getInteraction();

        switch (interaction.getCode()) {
            case PROCEED:
                handleLoadPaymentSessionProceed(session);
                break;
            default:
                ErrorInfo errorInfo = new ErrorInfo(listResult.getResultInfo(), interaction);
                closeWithErrorCode(new PaymentResult(errorInfo));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocalizationSuccess(Localization localization) {
        Localization.setInstance(localization);

        if (reloadInteraction != null) {
            view.showInteractionDialog(reloadInteraction, null);
            reloadInteraction = null;
        }
        view.showPaymentSession(this.session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocalizationError(Throwable cause) {
        handleLoadingError(cause);
    }

    private void handleLoadPaymentSessionProceed(PaymentSession session) {
        if (session.isEmpty()) {
            closeWithErrorCode("There are no payment methods available");
            return;
        }
        this.operation = null;
        this.session = session;
        loadLocalizations(session);
    }

    private void loadLocalizations(PaymentSession session) {
        localizationService.loadLocalizations(view.getActivity(), session);
    }

    private void handleLoadingError(Throwable cause) {
        PaymentResult result = PaymentResultHelper.fromThrowable(cause);
        if (result.isNetworkFailure()) {
            handleLoadingNetworkFailure(result);
        } else {
            closeWithErrorCode(result);
        }
    }

    private void handleLoadingNetworkFailure(final PaymentResult result) {
        view.showConnectionErrorDialog(new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                if (session == null) {
                    loadPaymentSession(listUrl);
                } else {
                    loadLocalizations(session);
                }
            }

            @Override
            public void onNegativeButtonClicked() {
                closeWithErrorCode(result);
            }

            @Override
            public void onDismissed() {
                closeWithErrorCode(result);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress(boolean visible) {
        view.showProgress(visible);
    }

    private void handlePaymentActivityResult(PaymentActivityResult paymentActivityResult) {
        if (this.session == null) {
            closeWithErrorCode("Missing cached PaymentSession in PaymentListPresenter");
            return;
        }
        switch (paymentActivityResult.getRequestCode()) {
            case PROCESSPAYMENT_REQUEST_CODE:
                onProcessPaymentResult(paymentActivityResult.getResultCode(), paymentActivityResult.getPaymentResult());
                break;
            case CHARGEPAYMENT_REQUEST_CODE:
                onChargeActivityResult(paymentActivityResult);
                break;
            default:
                view.showPaymentSession(this.session);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redirect(RedirectRequest request) throws PaymentException {
        throw new PaymentException("Redirects are not supported by this presenter");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onProcessPaymentResult(int resultCode, PaymentResult result) {
        switch (resultCode) {
            case RESULT_CODE_PROCEED:
                closeWithProceedCode(result);
                break;
            case RESULT_CODE_ERROR:
                handleProcessPaymentError(result);
                break;
            default:
                view.showPaymentSession(session);
        }
    }

    private void handleProcessPaymentError(PaymentResult result) {
        if (result.isNetworkFailure()) {
            handleProcessNetworkFailure(result);
            return;
        }
        this.operation = null;
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
                reloadPaymentSession(null);
                break;
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                reloadPaymentSession(interaction);
                break;
            case InteractionCode.RETRY:
                showErrorAndPaymentSession(interaction);
                break;
            default:
                closeWithErrorCode(result);
        }
    }

    private void handleProcessNetworkFailure(final PaymentResult result) {
        view.showConnectionErrorDialog(new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                processPayment();
            }

            @Override
            public void onNegativeButtonClicked() {
                closeWithErrorCode(result);
            }

            @Override
            public void onDismissed() {
                closeWithErrorCode(result);
            }
        });
    }

    private void processPayment() {
        try {
            networkService.processPayment(view.getActivity(), PROCESSPAYMENT_REQUEST_CODE, operation);
        } catch (PaymentException e) {
            closeWithErrorCode(e);
        }
    }

    /**
     * The Charge result is received from the ChargePaymentActivity. Error messages are not displayed by this presenter since
     * the ChargePaymentActivity has taken care of displaying error and warning messages.
     *
     * @param paymentActivityResult result received after a charge has been performed
     */
    private void onChargeActivityResult(PaymentActivityResult paymentActivityResult) {
        this.operation = null;
        switch (paymentActivityResult.getResultCode()) {
            case RESULT_CODE_ERROR:
                handleChargeError(paymentActivityResult);
                break;
            case RESULT_CODE_PROCEED:
                view.passOnActivityResult(paymentActivityResult);
                break;
            default:
                view.showPaymentSession(this.session);
        }
    }

    private void handleChargeError(PaymentActivityResult paymentActivityResult) {
        Interaction interaction = paymentActivityResult.getPaymentResult().getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.RELOAD:
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
                reloadPaymentSession(null);
                break;
            case InteractionCode.RETRY:
                view.showPaymentSession(this.session);
                break;
            default:
                view.passOnActivityResult(paymentActivityResult);
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
        Operation operation = new Operation(card.getCode(), card.getPaymentMethod(), card.getOperationType(), url);

        for (FormWidget widget : widgets.values()) {
            widget.putValue(operation);
        }
        return operation;
    }

    private void onPresetCardSelected(PresetCard card) {
        Redirect redirect = card.getPresetAccount().getRedirect();
        List<Parameter> parameters = redirect.getParameters();

        String code = PaymentUtils.getParameterValue(INTERACTION_CODE, parameters);
        String reason = PaymentUtils.getParameterValue(INTERACTION_REASON, parameters);
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(reason)) {
            closeWithErrorCode("Missing Interaction code and reason inside PresetAccount.redirect");
            return;
        }
        OperationResult result = new OperationResult();
        result.setResultInfo("PresetAccount selected");
        result.setInteraction(new Interaction(code, reason));
        result.setRedirect(redirect);
        closeWithProceedCode(new PaymentResult(result));
    }

    private void loadPaymentSession(String listUrl) {
        this.session = null;
        view.clearList();
        view.showProgress(true);
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void reloadPaymentSession(Interaction interaction) {
        this.reloadInteraction = interaction;
        loadPaymentSession(this.listUrl);
    }

    private void closeWithProceedCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_PROCEED, result);
        view.close();
    }

    private void closeWithErrorCode(String errorMessage) {
        PaymentResult result = PaymentResultHelper.fromErrorMessage(errorMessage);
        closeWithErrorCode(result);
    }

    private void closeWithErrorCode(Throwable cause) {
        PaymentResult result = PaymentResultHelper.fromThrowable(cause);
        closeWithErrorCode(result);
    }

    private void closeWithErrorCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_ERROR, result);
        view.close();
    }

    private void showErrorAndPaymentSession(Interaction interaction) {
        view.showInteractionDialog(interaction, null);
        view.showPaymentSession(this.session);
    }
}
