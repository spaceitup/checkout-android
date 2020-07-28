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

import android.content.Context;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.OperationType;
import net.optile.payment.model.Redirect;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.service.LocalizationLoaderListener;
import net.optile.payment.ui.service.LocalizationLoaderService;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.NetworkServiceLookup;
import net.optile.payment.ui.service.NetworkServicePresenter;
import net.optile.payment.ui.service.PaymentSessionListener;
import net.optile.payment.ui.service.PaymentSessionService;
import net.optile.payment.ui.widget.FormWidget;

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

        localizationService = new LocalizationLoaderService();
        localizationService.setListener(this);
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
        if (!validateWidgets(card, widgets)) {
            return;
        }
        try {
            operation = createOperation(card, widgets);
            String code = card.getCode();
            String method = card.getPaymentMethod();
            networkService = NetworkServiceLookup.createService(code, method);
            networkService.setPresenter(this);
            preparePayment();
        } catch (PaymentException e) {
            closeWithErrorMessage(e.error);
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
            case InteractionCode.PROCEED:
                handleLoadPaymentSessionOk(session);
                break;
            default:
                PaymentResult result = new PaymentResult(listResult.getResultInfo(), interaction);
                closeWithErrorMessage(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocalizationSuccess(Localization localization) {
        Localization.setInstance(localization);

        if (reloadInteraction != null) {
            showInteractionDialog(reloadInteraction);
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

    private void handleLoadPaymentSessionOk(PaymentSession session) {
        this.operation = null;
        this.session = session;
        loadLocalizations(session);
    }

    private void loadLocalizations(PaymentSession session) {
        Context context = view.getActivity();
        localizationService.loadLocalizations(context, session);
    }

    private void handleLoadingError(Throwable cause) {
        PaymentError error = PaymentError.fromThrowable(cause);
        if (error.isNetworkFailure()) {
            handleLoadingNetworkFailure(error);
        } else {
            closeWithErrorMessage(error);
        }
    }

    private void handleLoadingNetworkFailure(final PaymentError error) {
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
                closeWithCanceledCode(error);
            }

            @Override
            public void onDismissed() {
                closeWithCanceledCode(error);
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

    private void handleActivityResult(ActivityResult activityResult) {
        if (this.session == null) {
            PaymentError error = new PaymentError("Missing cached PaymentSession in PaymentListPresenter");
            closeWithErrorMessage(error);
            return;
        }
        PaymentResult result = activityResult.paymentResult;
        int resultCode = activityResult.resultCode;
        switch (activityResult.requestCode) {
            case PREPAREPAYMENT_REQUEST_CODE:
                onPreparePaymentResult(resultCode, result);
                break;
            case PROCESSPAYMENT_REQUEST_CODE:
                onProcessPaymentResult(resultCode, result);
                break;
            case CHARGEPAYMENT_REQUEST_CODE:
                onChargeActivityResult(activityResult);
                break;
            default:
                view.showPaymentSession(this.session);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPreparePaymentResult(int resultCode, PaymentResult result) {
        switch (resultCode) {
            case PaymentUI.RESULT_CODE_OK:
                handlePreparePaymentOk();
                break;
            case PaymentUI.RESULT_CODE_CANCELED:
                handlePreparePaymentCanceled(result);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redirectPayment(Redirect redirect) throws PaymentException {
        throw new PaymentException("Redirects are not supported by this presenter");
    }

    private void handlePreparePaymentOk() {
        if (OperationType.CHARGE.equals(operation.getType())) {
            view.showChargePaymentScreen(CHARGEPAYMENT_REQUEST_CODE, operation);
        } else {
            processPayment();
        }
    }

    private void handlePreparePaymentCanceled(PaymentResult result) {
        if (result.hasNetworkFailureError()) {
            handlePrepareNetworkFailure(result);
            return;
        }
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
                closeWithErrorMessage(result);
        }
    }

    private void handlePrepareNetworkFailure(PaymentResult result) {
        view.showConnectionErrorDialog(new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                preparePayment();
            }

            @Override
            public void onNegativeButtonClicked() {
                closeWithCanceledCode(result);
            }

            @Override
            public void onDismissed() {
                closeWithCanceledCode(result);
            }
        });
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
                handleProcessPaymentCanceled(result);
                break;
        }
    }

    private void handleProcessPaymentCanceled(PaymentResult result) {
        if (result.hasNetworkFailureError()) {
            handleProcessNetworkFailure(result);
            return;
        }
        this.operation = null;
        Interaction interaction = result.getInteraction();
        if (interaction == null) {
            view.showPaymentSession(this.session);
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
                closeWithErrorMessage(result);
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
                closeWithCanceledCode(result);
            }

            @Override
            public void onDismissed() {
                closeWithCanceledCode(result);
            }
        });
    }

    private void preparePayment() {
        try {
            networkService.preparePayment(view.getActivity(), PREPAREPAYMENT_REQUEST_CODE, operation);
        } catch (PaymentException e) {
            closeWithErrorMessage(e.error);
        }
    }

    private void processPayment() {
        try {
            networkService.processPayment(view.getActivity(), PROCESSPAYMENT_REQUEST_CODE, operation);
        } catch (PaymentException e) {
            closeWithErrorMessage(e.error);
        }
    }

    /**
     * The Charge result is received from the ChargePaymentActivity. Error messages are not displayed by this presenter since
     * the ChargePaymentActivity has taken care of displaying error and warning messages.
     *
     * @param activityResult result received after a charge has been performed
     */
    private void onChargeActivityResult(ActivityResult activityResult) {
        this.operation = null;
        switch (activityResult.resultCode) {
            case PaymentUI.RESULT_CODE_CANCELED:
                handleChargeCanceled(activityResult);
                break;
            default:
                view.passOnActivityResult(activityResult);
        }
    }

    private void handleChargeCanceled(ActivityResult activityResult) {
        Interaction interaction = activityResult.paymentResult.getInteraction();
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
                view.passOnActivityResult(activityResult);
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

    private void loadPaymentSession(String listUrl) {
        this.session = null;
        view.clearList();
        view.showProgress(true);
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void reloadPaymentSession(PaymentResult result) {
        this.reloadInteraction = result.getInteraction();
        loadPaymentSession(this.listUrl);
    }

    private void closeWithOkCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_OK, result);
        view.close();
    }

    private void closeWithCanceledCode(PaymentError error) {
        PaymentResult result = PaymentResult.fromPaymentError(error);
        closeWithCanceledCode(result);
    }

    private void closeWithCanceledCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        view.close();
    }

    private void closeWithErrorMessage(PaymentError error) {
        closeWithErrorMessage(PaymentResult.fromPaymentError(error));
    }

    private void closeWithErrorMessage(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_CANCELED, result);
        Interaction interaction = result.getInteraction();
        PaymentDialogListener listener = new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                view.close();
            }

            @Override
            public void onNegativeButtonClicked() {
                view.close();
            }

            @Override
            public void onDismissed() {
                view.close();
            }
        };
        if (Localization.hasInteraction(interaction)) {
            view.showInteractionDialog(interaction, listener);
        } else {
            view.showDefaultErrorDialog(listener);
        }
    }

    private void showInteractionDialog(Interaction interaction) {
        if (Localization.hasInteraction(interaction)) {
            view.showInteractionDialog(interaction, null);
        }
    }

    private void showPaymentSessionWithMessage(PaymentResult result) {
        view.showPaymentSession(this.session);
        showInteractionDialog(result.getInteraction());
    }
}
