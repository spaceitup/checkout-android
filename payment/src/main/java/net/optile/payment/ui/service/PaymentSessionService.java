/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import android.text.TextUtils;
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
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.model.PresetCard;
import net.optile.payment.validation.Validator;

/**
 * The PaymentSessionService providing asynchronize loading of the PaymentSession and communication with the Payment API.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class PaymentSessionService {

    private static final String PAYMENTPAGE = "paymentpage";
    private static final String KEYLANG = "lang";

    private final ListConnection listConnection;
    private final PaymentConnection paymentConnection;
    private PaymentSessionListener listener;
    private WorkerTask<PaymentSession> sessionTask;
    private WorkerTask<OperationResult> operationTask;

    /**
     * Create a new PaymentSessionService, this service is used to load the PaymentSession.
     */
    public PaymentSessionService() {
        this.listConnection = new ListConnection();
        this.paymentConnection = new PaymentConnection();
    }

    /** 
     * Set the session listener which will be informed about the state of a payment session being loaded.
     * 
     * @param listener to be informed about the payment session being loaded
     */
    public void setListener(PaymentSessionListener listener) {
        this.listener = listener;
    }
    
    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {
        if (sessionTask != null) {
            sessionTask.unsubscribe();
            sessionTask = null;
        }
        if (operationTask != null) {
            operationTask.unsubscribe();
            operationTask = null;
        }
    }

    /** 
     * Check if this service is currently active, i.e. is is loading a payment session or posting an operation.
     * 
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return isLoadingPaymentSession() || isPostingOperation();
    }
    
    /** 
     * Check if this service is currently loading a payment session from the Payment API
     * 
     * @return true when loading, false otherwise 
     */
    public boolean isLoadingPaymentSession() {
        return sessionTask != null && sessionTask.isSubscribed();
    }

    /** 
     * Check if this service is currently posting an operation to the Payment API
     * 
     * @return true when posting, false otherwise
     */
    public boolean isPostingOperation() {
        return operationTask != null && operationTask.isSubscribed();
    }
    
    /** 
     * Load the PaymentSession with the given listUrl, this will load the list result, languages and validator.
     * 
     * @param listUrl URL pointing to the list on the Payment API
     * @param context Android context in which this service is used
     */
    public void loadPaymentSession(final String listUrl, final Context context) {

        if (sessionTask != null) {
            throw new IllegalStateException("Already loading payment session, stop first");
        }
        sessionTask = WorkerTask.fromCallable(new Callable<PaymentSession>() {
            @Override
            public PaymentSession call() throws PaymentException {
                return asyncLoadPaymentSession(listUrl, context);
            }
        });
        sessionTask.subscribe(new WorkerSubscriber<PaymentSession>() {
            @Override
            public void onSuccess(PaymentSession paymentSession) {
                sessionTask = null;

                if (listener != null) {
                    listener.onPaymentSessionSuccess(paymentSession);
                }
            }
            @Override
            public void onError(Throwable cause) {
                sessionTask = null;

                if (listener != null) {
                    listener.onPaymentSessionError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(sessionTask);
    }

    /** 
     * Post an operation to the Payment API
     * 
     * @param operation to be posted to the Payment API
     */
    public void postOperation(final Operation operation) {

        if (isPostingOperation()) {
            throw new IllegalStateException("Already posting operation, stop first");
        }
        operationTask = WorkerTask.fromCallable(new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws PaymentException {
                return asyncPostOperation(operation);
            }
        });
        operationTask.subscribe(new WorkerSubscriber<OperationResult>() {
            @Override
            public void onSuccess(OperationResult result) {
                operationTask = null;

                if (listener != null) {
                    listener.onOperationSuccess(result);
                }
            }

            @Override
            public void onError(Throwable cause) {
                operationTask = null;

                if (listener != null) {
                    listener.onOperationError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(operationTask);
    }

    /**
     * Post an Operation to the Payment API
     *
     * @param operation the object containing the operation details
     * @return operation result containing information about the operation request
     */
    private OperationResult asyncPostOperation(Operation operation) throws PaymentException {
        return paymentConnection.postOperation(operation);
    }
    
    private PaymentSession asyncLoadPaymentSession(String listUrl, Context context) throws PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);
        Map<String, PaymentNetwork> networks = loadPaymentNetworks(listResult);
        Map<String, PaymentGroup> groups = loadPaymentGroups(context);
        LanguageFile lang = loadPaymentPageLanguageFile(networks);
        Validator validator = loadValidator(context);
        
        PresetCard presetCard = createPresetCard(listResult, networks);
        List<AccountCard> accountCards = createAccountCards(listResult, networks);
        List<NetworkCard> networkCards = createNetworkCards(networks, groups);
        
        return new PaymentSession(listResult, presetCard, accountCards, networkCards, validator, lang);
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
        URL langUrl = getNetworkLink(network, KEYLANG);

        if (langUrl == null) {
            throw createPaymentException("Missing 'lang' link in ApplicableNetwork", null);
        }
        LanguageFile lang = listConnection.loadLanguageFile(langUrl, true);
        return new PaymentNetwork(network, lang);
    }

    private List<NetworkCard> createNetworkCards(Map<String, PaymentNetwork> networks, Map<String, PaymentGroup> groups) throws PaymentException {
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
        AccountCard card;
        
        for (AccountRegistration account : accounts) {
            PaymentNetwork pn = networks.get(account.getCode());

            if (pn != null) {
                card = new AccountCard(account, pn.getApplicableNetwork(), pn.getLang());
                cards.add(card);
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
        return new PresetCard(account, pn.getApplicableNetwork(), pn.getLang());
    }

    /**
     * This method loads the payment page language file.
     * The URL for the paymentpage language file is constructed from the URL of one of the ApplicableNetwork entries.
     *
     * @param networks contains the list of PaymentNetwork elements
     * @return the properties object containing the language entries
     */
    private LanguageFile loadPaymentPageLanguageFile(Map<String, PaymentNetwork> networks) throws PaymentException {

        if (networks.size() == 0) {
            return new LanguageFile();
        }
        PaymentNetwork network = networks.entrySet().iterator().next().getValue();
        URL langUrl = network.getLink(KEYLANG);

        try {
            String pageUrl = langUrl.toString().replaceAll(network.getCode(), PAYMENTPAGE);
            return listConnection.loadLanguageFile(new URL(pageUrl), true);
        } catch (MalformedURLException e) {
            throw createPaymentException("Malformed language URL", e);
        }
    }

    private Map<String, PaymentGroup> loadPaymentGroups(Context context) throws PaymentException {
        int groupResId = PaymentUI.getInstance().getGroupResId();
        return ResourceLoader.loadPaymentGroups(context.getResources(), groupResId);
    }

    private boolean isSupported(ApplicableNetwork network) {
        String button = network.getButton();
        return (TextUtils.isEmpty(button) || !button.contains("activate")) && !network.getRedirect();
    }

    private PaymentException createPaymentException(String message, Throwable cause) {
        PaymentError error = new PaymentError("Session", PaymentError.INTERNAL_ERROR, message);
        return new PaymentException(error, message, cause);
    }

    private Validator loadValidator(Context context) throws PaymentException {
        int validationResId = PaymentUI.getInstance().getValidationResId();
        return new Validator(ResourceLoader.loadValidations(context.getResources(), validationResId));        
    }

    private URL getNetworkLink(ApplicableNetwork network, String name) {
        Map<String, URL> links = network.getLinks();
        return links != null ? links.get(name) : null;
    }
}
