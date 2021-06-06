/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.page;

import static com.payoneer.checkout.model.InteractionCode.PROCEED;
import static com.payoneer.checkout.model.NetworkOperationType.CHARGE;
import static com.payoneer.checkout.redirect.RedirectService.INTERACTION_CODE;
import static com.payoneer.checkout.redirect.RedirectService.INTERACTION_REASON;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_ERROR;
import static com.payoneer.checkout.ui.PaymentActivityResult.RESULT_CODE_PROCEED;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.AccountRegistration;
import com.payoneer.checkout.model.ErrorInfo;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.model.InteractionCode;
import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.model.Parameter;
import com.payoneer.checkout.model.Redirect;
import com.payoneer.checkout.redirect.RedirectRequest;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.PaymentUI;
import com.payoneer.checkout.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import com.payoneer.checkout.ui.list.PaymentListListener;
import com.payoneer.checkout.ui.model.AccountCard;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.model.PaymentSession;
import com.payoneer.checkout.ui.model.PresetCard;
import com.payoneer.checkout.ui.service.NetworkService;
import com.payoneer.checkout.ui.service.NetworkServiceListener;
import com.payoneer.checkout.ui.service.NetworkServiceLookup;
import com.payoneer.checkout.ui.service.PaymentSessionListener;
import com.payoneer.checkout.ui.service.PaymentSessionService;
import com.payoneer.checkout.ui.widget.FormWidget;
import com.payoneer.checkout.util.PaymentResultHelper;
import com.payoneer.checkout.util.PaymentUtils;

import android.text.TextUtils;

/**
 * The PaymentListPresenter implementing the presenter part of the MVP
 */
final class PaymentListPresenter extends BasePaymentPresenter
    implements PaymentSessionListener, NetworkServiceListener, PaymentListListener {

    private final static int CHARGEPAYMENT_REQUEST_CODE = 1;
    private final PaymentSessionService sessionService;
    private final PaymentListView view;

    private PaymentSession session;
    private Interaction reloadInteraction;
    private Operation operation;
    private PaymentActivityResult activityResult;
    private NetworkService networkService;

    /**
     * Create a new PaymentListPresenter
     *
     * @param view The PaymentListView displaying the payment list
     */
    PaymentListPresenter(PaymentListView view) {
        super(PaymentUI.getInstance().getListUrl());
        this.view = view;

        sessionService = new PaymentSessionService(view.getActivity());
        sessionService.setListener(this);
    }

    void onStart() {
        setState(STARTED);

        if (activityResult != null) {
            handlePaymentActivityResult(activityResult);
            activityResult = null;
        } else if (session == null || !session.getListUrl().equals(listUrl)) {
            loadPaymentSession(listUrl);
        } else {
            view.showPaymentSession(this.session);
        }
    }

    void onStop() {
        setState(STOPPED);
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
    void setPaymentActivityResult(PaymentActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    @Override
    public void onActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets) {
        if (!checkState(STARTED)) {
            return;
        }
        if (paymentCard instanceof PresetCard) {
            onPresetCardSelected((PresetCard) paymentCard);
            return;
        }
        try {
            operation = createOperation(paymentCard, widgets);
            initNetworkService(paymentCard.getCode(), paymentCard.getPaymentMethod());

            if (CHARGE.equals(operation.getOperationType())) {
                view.showChargePaymentScreen(CHARGEPAYMENT_REQUEST_CODE, operation);
            } else {
                processPayment();
            }
        } catch (PaymentException e) {
            closeWithErrorCode(PaymentResultHelper.fromThrowable(e));
        }
    }

    @Override
    public void onDeleteClicked(PaymentCard paymentCard) {
        if (!checkState(STARTED)) {
            return;
        }
        AccountRegistration account = ((AccountCard) paymentCard).getAccountRegistration();
        view.showDeleteDialog(new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                deleteAccountRegistration(account);
            }

            @Override
            public void onNegativeButtonClicked() {
            }

            @Override
            public void onDismissed() {
            }
        });
    }

    @Override
    public void onHintClicked(String networkCode, String type) {
        view.showHintDialog(networkCode, type, null);
    }

    @Override
    public void onPaymentSessionSuccess(PaymentSession session) {
        ListResult listResult = session.getListResult();
        Interaction interaction = listResult.getInteraction();

        if (Objects.equals(interaction.getCode(), PROCEED)) {
            handleLoadPaymentSessionProceed(session);
        } else {
            ErrorInfo errorInfo = new ErrorInfo(listResult.getResultInfo(), interaction);
            closeWithErrorCode(new PaymentResult(errorInfo));
        }
    }

    @Override
    public void onPaymentSessionError(Throwable cause) {
        PaymentResult result = PaymentResultHelper.fromThrowable(cause);
        if (result.isNetworkFailure()) {
            handleLoadingNetworkFailure(result);
        } else {
            closeWithErrorCode(result);
        }
    }

    private void handleLoadPaymentSessionProceed(PaymentSession session) {
        if (session.isEmpty()) {
            closeWithErrorCode("There are no payment methods available");
            return;
        }
        this.operation = null;
        this.session = session;

        if (reloadInteraction != null) {
            view.showInteractionDialog(reloadInteraction, null);
            reloadInteraction = null;
        }
        view.showPaymentSession(this.session);
    }

    private void handleLoadingNetworkFailure(final PaymentResult result) {
        view.showConnectionErrorDialog(new PaymentDialogListener() {
            @Override
            public void onPositiveButtonClicked() {
                loadPaymentSession(listUrl);
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

    private void handlePaymentActivityResult(PaymentActivityResult activityResult) {
        if (this.session == null) {
            closeWithErrorCode("Missing cached PaymentSession in PaymentListPresenter");
            return;
        }
        int requestCode = activityResult.getRequestCode();
        if (requestCode == CHARGEPAYMENT_REQUEST_CODE) {
            handleChargeActivityResult(activityResult);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleteAccountResult(int resultCode, PaymentResult result) {
        setState(STARTED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onProcessPaymentResult(int resultCode, PaymentResult result) {
        setState(STARTED);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void redirect(RedirectRequest request) throws PaymentException {
        throw new PaymentException("Redirects are not supported by this presenter");
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
        setState(PROCESS);
        networkService.processPayment(operation);
    }

    private void deleteAccountRegistration(AccountRegistration account) {
        try {
            setState(PROCESS);
            initNetworkService(account.getCode(), account.getMethod());
            networkService.deleteAccount(account);
        } catch (PaymentException e) {
            closeWithErrorCode(PaymentResultHelper.fromThrowable(e));
        }
    }

    /**
     * The Charge result is received from the ChargePaymentActivity. Error messages are not displayed by this presenter since
     * the ChargePaymentActivity has taken care of displaying error and warning messages.
     *
     * @param paymentActivityResult result received after a charge has been performed
     */
    private void handleChargeActivityResult(PaymentActivityResult paymentActivityResult) {
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

    private void initNetworkService(String code, String method) throws PaymentException {
        networkService = NetworkServiceLookup.createService(view.getActivity(), code, method);
        if (networkService == null) {
            throw new PaymentException("Missing NetworkService for network: " + code + ", " + method);
        }
        networkService.setListener(this);
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

    private void closeWithErrorCode(PaymentResult result) {
        view.setPaymentResult(RESULT_CODE_ERROR, result);
        view.close();
    }

    private void showErrorAndPaymentSession(Interaction interaction) {
        view.showInteractionDialog(interaction, null);
        view.showPaymentSession(this.session);
    }
}
