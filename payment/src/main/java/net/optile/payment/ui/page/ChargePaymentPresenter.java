/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import static net.optile.payment.localization.LocalizationKey.CHARGE_INTERRUPTED;

import android.content.Context;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Redirect;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.redirect.RedirectService;
import net.optile.payment.ui.service.LocalizationLoaderListener;
import net.optile.payment.ui.service.LocalizationLoaderService;
import net.optile.payment.ui.service.NetworkService;
import net.optile.payment.ui.service.NetworkServiceLookup;
import net.optile.payment.ui.service.NetworkServicePresenter;
import net.optile.payment.ui.service.PaymentSessionListener;
import net.optile.payment.ui.service.PaymentSessionService;

/**
 * The ChargePaymentPresenter takes care of posting the operation to the Payment API.
 * First this presenter will load the list, checks if the operation is present in the list and then post the operation to the Payment API.
 */
final class ChargePaymentPresenter implements PaymentSessionListener, NetworkServicePresenter, LocalizationLoaderListener {

    private final static int CHARGE_REQUEST_CODE = 1;
    private final PaymentView view;
    private final PaymentSessionService sessionService;
    private final LocalizationLoaderService localizationService;

    private PaymentSession session;
    private String listUrl;
    private Operation operation;
    private ActivityResult activityResult;
    private NetworkService networkService;
    private boolean redirected;

    /**
     * Create a new ChargePaymentPresenter
     *
     * @param view The ChargePaymentView displaying the progress animation
     */
    ChargePaymentPresenter(PaymentView view) {
        this.view = view;
        sessionService = new PaymentSessionService();
        sessionService.setListener(this);

        localizationService = new LocalizationLoaderService();
        localizationService.setListener(this);
    }

    void onStart(Operation operation) {
        this.operation = operation;
        this.listUrl = PaymentUI.getInstance().getListUrl();

        if (redirected) {
            handleRedirectResult();
            redirected = false;
        } else if (activityResult != null) {
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
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionSuccess(PaymentSession session) {
        ListResult listResult = session.getListResult();
        Interaction interaction = listResult.getInteraction();

        switch (interaction.getCode()) {
            case InteractionCode.PROCEED:
                handleLoadSessionProceed(session);
                break;
            default:
                PaymentResult result = new PaymentResult(listResult.getResultInfo(), interaction);
                closeWithErrorCode(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionError(Throwable cause) {
        handleLoadingError(cause);
    }

    private void handleLoadSessionProceed(PaymentSession session) {
        if (!session.containsLink("operation", operation.getURL())) {
            closeWithErrorCode("operation not found in ListResult");
            return;
        }
        this.session = session;
        loadLocalizations(session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocalizationSuccess(Localization localization) {
        Localization.setInstance(localization);
        String code = operation.getCode();
        String method = session.lookupPaymentMethod(code);
        if (method == null) {
            closeWithErrorCode("Payment method lookup failed for code: " + code);
            return;
        }
        networkService = NetworkServiceLookup.createService(code, method);
        if (networkService == null) {
            closeWithErrorCode("NetworkService lookup failed for: " + code + ", " + method);
            return;
        }
        networkService.setPresenter(this);
        processPayment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocalizationError(Throwable cause) {
        handleLoadingError(cause);
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
            closeWithErrorCode(error);
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
                closeWithErrorCode(error);
            }

            @Override
            public void onDismissed() {
                closeWithErrorCode(error);
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
            case PaymentUI.RESULT_CODE_PROCEED:
                closeWithProceedCode(result);
                break;
            case PaymentUI.RESULT_CODE_ERROR:
                handleProcessPaymentError(result);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redirectPayment(Redirect redirect) throws PaymentException {
        Context context = view.getActivity();
        if (!RedirectService.isSupported(context, redirect)) {
            throw new PaymentException("This Redirect payment method is not supported by the Android-SDK");
        }
        view.showProgress(false);
        RedirectService.open(context, redirect);
        this.redirected = true;
    }

    private void handleRedirectResult() {
        if (session == null) {
            handleMissingCachedSession();
            return;
        }
        OperationResult result = RedirectService.getRedirectResult();
        if (result != null) {
            networkService.onRedirectSuccess(result);
        } else {
            networkService.onRedirectError();
        }
    }

    private void handleMissingCachedSession() {
        closeWithErrorCode("Missing cached session in ChargePaymentPresenter");
    }

    private void handleProcessPaymentError(PaymentResult result) {
        if (result.hasNetworkFailureError()) {
            handleProcessNetworkFailure(result);
            return;
        }
        Interaction interaction = result.getInteraction();
        switch (interaction.getCode()) {
            case InteractionCode.TRY_OTHER_ACCOUNT:
            case InteractionCode.TRY_OTHER_NETWORK:
            case InteractionCode.RETRY:
                showErrorAndCloseWithErrorCode(result);
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

    /**
     * Let this presenter handle the back pressed.
     *
     * @return true when this presenter handled the back press, false otherwise
     */
    boolean onBackPressed() {
        view.showWarningMessage(Localization.translate(CHARGE_INTERRUPTED));
        return true;
    }

    private void processPayment() {
        try {
            networkService.processPayment(view.getActivity(), CHARGE_REQUEST_CODE, operation);
        } catch (PaymentException e) {
            closeWithErrorCode(e.error);
        }
    }

    private void handleActivityResult(ActivityResult result) {
        if (session == null) {
            handleMissingCachedSession();
            return;
        }
        if (result.requestCode == CHARGE_REQUEST_CODE) {
            onProcessPaymentResult(result.resultCode, result.paymentResult);
        }
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.showProgress(true);
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void closeWithProceedCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_PROCEED, result);
        view.close();
    }

    private void closeWithErrorCode(String message) {
        PaymentError error = new PaymentError(message);
        closeWithErrorCode(error);
    }

    private void closeWithErrorCode(PaymentError error) {
        PaymentResult result = PaymentResult.fromPaymentError(error);
        closeWithErrorCode(result);
    }

    private void closeWithErrorCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
        view.close();
    }

    private void showErrorAndCloseWithErrorCode(PaymentResult result) {
        view.setPaymentResult(PaymentUI.RESULT_CODE_ERROR, result);
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
        view.showInteractionDialog(interaction, listener);
    }
}
