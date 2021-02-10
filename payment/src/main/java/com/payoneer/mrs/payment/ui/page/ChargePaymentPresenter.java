/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.page;

import android.content.Context;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.form.Operation;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.model.ErrorInfo;
import com.payoneer.mrs.payment.model.Interaction;
import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.ListResult;
import com.payoneer.mrs.payment.model.OperationResult;
import com.payoneer.mrs.payment.ui.PaymentActivityResult;
import com.payoneer.mrs.payment.ui.PaymentResult;
import com.payoneer.mrs.payment.ui.PaymentUI;
import com.payoneer.mrs.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import com.payoneer.mrs.payment.ui.model.PaymentSession;
import com.payoneer.mrs.payment.ui.redirect.RedirectRequest;
import com.payoneer.mrs.payment.ui.redirect.RedirectService;
import com.payoneer.mrs.payment.ui.service.LocalizationLoaderListener;
import com.payoneer.mrs.payment.ui.service.LocalizationLoaderService;
import com.payoneer.mrs.payment.ui.service.NetworkService;
import com.payoneer.mrs.payment.ui.service.NetworkServiceLookup;
import com.payoneer.mrs.payment.ui.service.NetworkServicePresenter;
import com.payoneer.mrs.payment.ui.service.PaymentSessionListener;
import com.payoneer.mrs.payment.ui.service.PaymentSessionService;
import com.payoneer.mrs.payment.util.PaymentResultHelper;

import java.util.Objects;

import static com.payoneer.mrs.payment.localization.LocalizationKey.CHARGE_INTERRUPTED;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.mrs.payment.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

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
    private PaymentActivityResult paymentActivityResult;
    private NetworkService networkService;
    private boolean redirected;

    /**
     * Create a new ChargePaymentPresenter
     *
     * @param view The ChargePaymentView displaying the progress animation
     */
    ChargePaymentPresenter(PaymentView view) {
        this.view = view;
        sessionService = new PaymentSessionService(view.getActivity());
        sessionService.setListener(this);

        localizationService = new LocalizationLoaderService(view.getActivity());
        localizationService.setListener(this);
    }

    void onStart(Operation operation) {
        this.operation = operation;
        this.listUrl = PaymentUI.getInstance().getListUrl();

        if (redirected) {
            handleRedirectResult();
            redirected = false;
        } else if (paymentActivityResult != null) {
            handlePaymentActivityResult(paymentActivityResult);
            paymentActivityResult = null;
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
     * {@inheritDoc}
     */
    @Override
    public void onPaymentSessionSuccess(PaymentSession session) {
        ListResult listResult = session.getListResult();
        Interaction interaction = listResult.getInteraction();

        if (Objects.equals(InteractionCode.PROCEED, interaction.getCode())) {
            handleLoadSessionProceed(session);
        } else {
            ErrorInfo errorInfo = new ErrorInfo(listResult.getResultInfo(), interaction);
            PaymentResult result = new PaymentResult(errorInfo, null);
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
        String networkCode = operation.getNetworkCode();
        String paymentMethod = operation.getPaymentMethod();
        networkService = NetworkServiceLookup.createService(view.getActivity(), networkCode, paymentMethod);
        if (networkService == null) {
            closeWithErrorCode("NetworkService lookup failed for: " + networkCode + ", " + paymentMethod);
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
        PaymentResult result = PaymentResultHelper.fromThrowable(InteractionCode.ABORT, cause);

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
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redirect(RedirectRequest redirectRequest) throws PaymentException {
        Context context = view.getActivity();
        if (!RedirectService.supports(context, redirectRequest)) {
            throw new PaymentException("The Redirect payment method is not supported by the Android-SDK");
        }
        view.showProgress(false);
        RedirectService.redirect(context, redirectRequest);
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
        if (result.isNetworkFailure()) {
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
        networkService.processPayment(view.getActivity(), CHARGE_REQUEST_CODE, operation);
    }

    private void handlePaymentActivityResult(PaymentActivityResult result) {
        if (session == null) {
            handleMissingCachedSession();
            return;
        }
        if (result.getRequestCode() == CHARGE_REQUEST_CODE) {
            onProcessPaymentResult(result.getResultCode(), result.getPaymentResult());
        }
    }

    private void loadPaymentSession(final String listUrl) {
        this.session = null;
        view.showProgress(true);
        sessionService.loadPaymentSession(listUrl, view.getActivity());
    }

    private void closeWithProceedCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_PROCEED, result);
        view.close();
    }

    private void closeWithErrorCode(String message) {
        PaymentResult result = PaymentResultHelper.fromErrorMessage(message);
        closeWithErrorCode(result);
    }

    private void closeWithErrorCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_ERROR, result);
        view.close();
    }

    private void showErrorAndCloseWithErrorCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_ERROR, result);
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
