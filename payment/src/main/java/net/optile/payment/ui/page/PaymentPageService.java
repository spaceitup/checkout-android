/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.form.Operation;
import net.optile.payment.model.AccountRegistration;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.network.ListConnection;
import net.optile.payment.network.PaymentConnection;
import net.optile.payment.resource.PaymentGroup;
import net.optile.payment.resource.ResourceLoader;
import net.optile.payment.service.ListService;
import net.optile.payment.service.PaymentService;
import net.optile.payment.service.ValidatorService;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.model.PresetCard;
import net.optile.payment.validation.Validator;

/**
 * The PaymentPageService providing asynchronize initializing of the PaymentPage and communication with the Payment API .
 * This service makes callbacks in the presenter to notify of request completions.
 */
final class PaymentPageService implements ValidatorService.ValidatorListener, PaymentService.PaymentListener, ListService.ListListener {

    private final PaymentPagePresenter presenter;
    private final ValidatorService validatorService;
    private final PaymentService paymentService;
    private final ListService listService;
    private Validator validator;
    
    /**
     * Create a new PaymentPageService, this service is used to communicate with the Payment API
     */
    PaymentPageService(PaymentPagePresenter presenter) {
        this.presenter = presenter;
        paymentService = new PaymentService();
        paymentService.setListener(this);
        
        listService = new ListService();
        listService.setListener(this);

        validatorService = new ValidatorService();
        validatorService.setListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValidatorSuccess(Validator validator) {
        this.validator = validator;
        asyncLoadPaymentSession(this.listUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValidatorError(Throwable cause) {
        presenter.onPaymentSessionError(cause);
    }

    
    void stop() {
        validatorService.stop();
        paymentService.stop();
        listService.stop();
    }

    Validator getValidator() {
        return validator;
    }
    
    boolean isPerformingOperation() {
        return paymentService.isActive();
    }

    boolean isActive() {
        return validatorService.isActive() || paymentService.isActive() || listService.isActive();
    }

    void loadPaymentSession(String listUrl) {

        if (isActive()) {
            throw new IllegalStateException("Already active, stop first");
        }
        if (validator == null) {
            initializeValidator(listUrl);
        } else {
            asyncLoadPaymentSession(listUrl);
        }
    }

    void postOperation(Operation operation) {
    }
    
    /**
     * Initialize the validator
     *
     * @return the validator
     */
    private void initializeValidator(final String listUrl) {

        validatorService.setListener(new ValidatorService.ValidatorListener() {
                @Override
                public void onValidatorSuccess(Validator validator) {
                    PaymentPageService.this.validator = validator;
                    asyncLoadPaymentSession(listUrl);
                }
                @Override
                public void onValidatorError(Throwable cause) {
                    presenter.onPaymentSessionError(cause);
                }
            });
    }

    private void asyncLoadPaymentSession(String listUrl) {

        
    }
    
    private PaymentSession asyncLoadPaymentSession0(String listUrl) throws PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);
        Map<String, PaymentNetwork> networks = loadPaymentNetworks(listResult);

        PresetCard presetCard = createPresetCard(listResult, networks);
        List<AccountCard> accountCards = createAccountCards(listResult, networks);
        List<NetworkCard> networkCards = createNetworkCards(networks);

        PaymentSession session = new PaymentSession(listResult, presetCard, accountCards, networkCards);
        session.setLang(loadPaymentPageLanguageFile(networks));
        return session;
    }

    private OperationResult asyncPostOperation(Operation operation) throws PaymentException {
        return paymentConnection.postOperation(operation);
    }

    private List<NetworkCard> createNetworkCards(Map<String, PaymentNetwork> networks) throws PaymentException {
        Map<String, PaymentGroup> groups = loadPaymentGroups();
        Map<String, NetworkCard> cards = new LinkedHashMap<>();

        NetworkCard card;
        PaymentGroup group;
        String code;

        for (PaymentNetwork network : networks.values()) {
            code = network.getCode();

            if ((group = groups.get(code)) == null) {
                addNetworkCard(cards, code, network);
                continue;
            }
            network.setSmartSelectionRegex(group.getSmartSelectionRegex(code));
            card = cards.get(group.getId());

            if (card == null) {
                addNetworkCard(cards, group.getId(), network);
            } else if (!card.addPaymentNetwork(network)) {
                addNetworkCard(cards, code, network);
            }
        }
        return new ArrayList<>(cards.values());
    }

    private void addNetworkCard(Map<String, NetworkCard> cards, String cardId, PaymentNetwork network) {
        NetworkCard card = new NetworkCard();
        card.addPaymentNetwork(network);
        cards.put(cardId, card);
    }

    private List<AccountCard> createAccountCards(ListResult listResult, Map<String, PaymentNetwork> networks) {
        List<AccountCard> cards = new ArrayList<>();
        List<AccountRegistration> accounts = listResult.getAccounts();

        if (accounts == null || accounts.size() == 0) {
            return cards;
        }
        for (AccountRegistration account : accounts) {
            PaymentNetwork pn = networks.get(account.getCode());

            if (pn != null) {
                cards.add(createAccountCard(account, pn));
            }
        }
        return cards;
    }

    private PresetCard createPresetCard(ListResult listResult, Map<String, PaymentNetwork> networks) {
        PresetAccount account = listResult.getPresetAccount();

        if (account == null) {
            return null;
        }
        PaymentNetwork pn = networks.get(account.getCode());
        if (pn == null) {
            return null;
        }
        PresetCard card = new PresetCard(account, pn.network);
        card.setLang(pn.getLang());
        return card;
    }

    private Map<String, PaymentNetwork> loadPaymentNetworks(ListResult listResult) throws PaymentException {
        LinkedHashMap<String, PaymentNetwork> items = new LinkedHashMap<>();
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
                items.put(network.getCode(), loadPaymentNetwork(network));
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

    /**
     * This method loads the payment page language file.
     * The URL for the paymentpage language file is constructed from the URL of one of the ApplicableNetwork entries.
     *
     * @param networks contains the list of PaymentNetwork elements
     * @return the properties object containing the language entries
     */
    private LanguageFile loadPaymentPageLanguageFile(Map<String, PaymentNetwork> networks) throws PaymentException {
        LanguageFile file = new LanguageFile();

        if (networks.size() == 0) {
            return file;
        }
        PaymentNetwork network = networks.entrySet().iterator().next().getValue();
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

    private Map<String, PaymentGroup> loadPaymentGroups() throws PaymentException {
        int groupResId = PaymentUI.getInstance().getGroupResId();
        Resources res = presenter.getContext().getResources();
        return ResourceLoader.loadPaymentGroups(res, groupResId);
    }

    private boolean isSupported(ApplicableNetwork network) {
        String button = network.getButton();
        return (TextUtils.isEmpty(button) || !button.contains("activate")) && !network.getRedirect();
    }

    private PaymentException createPaymentException(String message, Throwable cause) {
        Log.w(TAG, cause);
        final PaymentError error = new PaymentError("PaymentPage", PaymentError.INTERNAL_ERROR, message);
        return new PaymentException(error, message, cause);
    }
}
