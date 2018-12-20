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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.AccountRegistration;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.model.OperationResult;
import net.optile.payment.network.ChargeConnection;
import net.optile.payment.network.ListConnection;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The PaymentPageService implementing the communication with the Payment API
 */
final class PaymentPageService {

    private final PaymentPagePresenter presenter;
    private final ListConnection listConnection;
    private final ChargeConnection chargeConnection;
    
    private WorkerTask<OperationResult> chargeTask;
    private WorkerTask<PaymentSession> loadTask;
    private WorkerTask<Validator> validatorTask;

    /**
     * Create a new PaymentPageService, this service is used to communicate with the Payment API
     */
    PaymentPageService(PaymentPagePresenter presenter) {
        this.presenter = presenter;
        this.listConnection = new ListConnection();
        this.chargeConnection = new ChargeConnection();
    }

    void stop() {

        if (loadTask != null) {
            loadTask.unsubscribe();
            loadTask = null;
        }
        if (chargeTask != null) {
            chargeTask.unsubscribe();
            chargeTask = null;
        }
        if (validatorTask != null) {
            validatorTask.unsubscribe();
            validatorTask = null;
        }
    }

    boolean isLoading() {
        return loadTask != null || chargeTask != null || validatorTask != null;
    }

    void loadValidator() {

        if (validatorTask != null) {
            throw new IllegalStateException("Already loading validator, stop first");
        }
        validatorTask = WorkerTask.fromCallable(new Callable<Validator>() {
            @Override
            public Validator call() throws PaymentException {
                return asyncLoadValidator();
            }
        });
        validatorTask.subscribe(new WorkerSubscriber<Validator>() {
            @Override
            public void onSuccess(Validator validator) {
                validatorTask = null;
                presenter.onValidatorSuccess(validator);
            }

            @Override
            public void onError(Throwable cause) {
                validatorTask = null;
                presenter.onValidatorError(cause);
            }
        });
        Workers.getInstance().forNetworkTasks().execute(validatorTask);
    }
    
    void loadPaymentSession(final String listUrl) {

        if (loadTask != null) {
            throw new IllegalStateException("Already loading payment session, stop first");
        }
        loadTask = WorkerTask.fromCallable(new Callable<PaymentSession>() {
            @Override
            public PaymentSession call() throws PaymentException {
                return asyncLoadPaymentSession(listUrl);
            }
        });
        loadTask.subscribe(new WorkerSubscriber<PaymentSession>() {
            @Override
            public void onSuccess(PaymentSession paymentSession) {
                loadTask = null;
                presenter.onPaymentSessionSuccess(paymentSession);
            }

            @Override
            public void onError(Throwable cause) {
                loadTask = null;
                presenter.onPaymentSessionError(cause);
            }
        });
        Workers.getInstance().forNetworkTasks().execute(loadTask);
    }

    void postChargeRequest(final URL url, final Charge charge) {

        if (chargeTask != null) {
            throw new IllegalStateException("Already posting charge, stop first");
        }
        chargeTask = WorkerTask.fromCallable(new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws PaymentException {
                return asyncPostChargeRequest(url, charge);
            }
        });
        chargeTask.subscribe(new WorkerSubscriber<OperationResult>() {
            @Override
            public void onSuccess(OperationResult result) {
                chargeTask = null;
                presenter.onChargeSuccess(result);
            }

            @Override
            public void onError(Throwable cause) {
                chargeTask = null;
                presenter.onChargeError(cause);
            }
        });
        Workers.getInstance().forNetworkTasks().execute(chargeTask);
    }

    /**
     * Load the Validator in the background including the validations settings file.
     *
     * @return the validator
     */
    private Validator loadValidator() throws PaymentException {
        return null;
    }
    
    /**
     * Load the PaymentSession from the Payment API
     *
     * @param listUrl unique list url of the payment session
     * @return the payment session obtained from the Payment API
     */
    private PaymentSession asyncLoadPaymentSession(String listUrl) throws PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);
        List<PaymentNetwork> networks = loadPaymentNetworks(listResult);
        List<NetworkCard> networkCards = loadNetworkCards(networks);
        List<AccountCard> accountCards = loadAccountCards(listResult, networks);

        PaymentSession session = new PaymentSession(listResult, accountCards, networkCards);
        session.setLang(loadPaymentPageLanguageFile(networks));
        return session;
    }

    /**
     * Post a ChargeRequest to the Payment API
     *
     * @param url the url of the charge request
     * @param charge the object containing the charge details
     * @return operation result containing information about the charge request
     */
    private OperationResult asyncPostChargeRequest(URL url, Charge charge) throws PaymentException {
        return chargeConnection.createCharge(url, charge);
    }

    private List<NetworkCard> loadNetworkCards(List<PaymentNetwork> networks) {
        List<NetworkCard> cards = new ArrayList<>();

        for (PaymentNetwork network : networks) {
            cards.add(createNetworkCard(network));
        }
        return cards;
    }

    private List<AccountCard> loadAccountCards(ListResult listResult, List<PaymentNetwork> networks) {
        List<AccountCard> cards = new ArrayList<>();
        List<AccountRegistration> accounts = listResult.getAccounts();

        if (accounts == null || accounts.size() == 0) {
            return cards;
        }
        for (AccountRegistration account : accounts) {
            PaymentNetwork pn = findPaymentNetwork(networks, account.getCode());

            if (pn != null) {
                cards.add(createAccountCard(account, pn));
            }
        }
        return cards;
    }

    private PaymentNetwork findPaymentNetwork(List<PaymentNetwork> networks, String code) {
        for (PaymentNetwork pn : networks) {
            if (pn.network.getCode().equals(code)) {
                return pn;
            }
        }
        return null;
    }

    private List<PaymentNetwork> loadPaymentNetworks(ListResult listResult) throws PaymentException {
        List<PaymentNetwork> items = new ArrayList<>();
        Networks nw = listResult.getNetworks();

        if (nw == null) {
            return items;
        }
        List<ApplicableNetwork> an = nw.getApplicable();

        if (an == null || an.size() == 0) {
            return items;
        }
        for (ApplicableNetwork network : an) {
            if (isSupported(network)) {
                items.add(loadPaymentNetwork(network));
            }
        }
        return items;
    }

    private PaymentNetwork loadPaymentNetwork(ApplicableNetwork network) throws PaymentException {
        PaymentNetwork paymentNetwork = new PaymentNetwork(network);
        URL langUrl = paymentNetwork.getLink("lang");

        if (langUrl == null) {
            throw createPaymentException("Missing 'lang' link in ApplicableNetwork", null);
        }
        paymentNetwork.setLang(listConnection.loadLanguageFile(langUrl, new LanguageFile()));
        return paymentNetwork;
    }

    private AccountCard createAccountCard(AccountRegistration registration, PaymentNetwork paymentNetwork) {
        AccountCard card = new AccountCard(registration, paymentNetwork.network);
        card.setLang(paymentNetwork.getLang());
        return card;
    }

    private NetworkCard createNetworkCard(PaymentNetwork paymentNetwork) {
        List<InputElement> elements = paymentNetwork.getInputElements();
        return new NetworkCard(paymentNetwork, elements);
    }

    /**
     * This method loads the payment page language file.
     * The URL for the paymentpage language file is constructed from the URL of one of the ApplicableNetwork entries.
     *
     * @param networks contains the list of PaymentNetwork elements
     * @return the properties object containing the language entries
     */
    private LanguageFile loadPaymentPageLanguageFile(List<PaymentNetwork> networks) throws PaymentException {
        LanguageFile file = new LanguageFile();

        if (networks.size() == 0) {
            return file;
        }
        PaymentNetwork network = networks.get(0);
        URL langUrl = network.getLink("lang");

        if (langUrl == null) {
            throw createPaymentException("Missing 'lang' link in ApplicableNetwork", null);
        }
        try {
            String newUrl = langUrl.toString().replaceAll(network.getCode(), "paymentpage");
            return listConnection.loadLanguageFile(new URL(newUrl), file);
        } catch (MalformedURLException e) {
            throw createPaymentException("Malformed language URL", e);
        }
    }

    private boolean isSupported(ApplicableNetwork network) {
        String button = network.getButton();
        return (TextUtils.isEmpty(button) || !button.contains("activate")) && !network.getRedirect();
    }

    private PaymentException createPaymentException(String message, Throwable cause) {
        final PaymentError error = new PaymentError("PaymentPage", PaymentError.INTERNAL_ERROR, message);
        return new PaymentException(error, message, cause);
    }
}
