/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.service;

import static com.payoneer.mrs.payment.model.IntegrationType.MOBILE_NATIVE;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.core.WorkerSubscriber;
import com.payoneer.mrs.payment.core.WorkerTask;
import com.payoneer.mrs.payment.core.Workers;
import com.payoneer.mrs.payment.model.AccountRegistration;
import com.payoneer.mrs.payment.model.ApplicableNetwork;
import com.payoneer.mrs.payment.model.ListResult;
import com.payoneer.mrs.payment.model.NetworkOperationType;
import com.payoneer.mrs.payment.model.Networks;
import com.payoneer.mrs.payment.model.PresetAccount;
import com.payoneer.mrs.payment.network.ListConnection;
import com.payoneer.mrs.payment.resource.PaymentGroup;
import com.payoneer.mrs.payment.resource.ResourceLoader;
import com.payoneer.mrs.payment.ui.model.AccountCard;
import com.payoneer.mrs.payment.ui.model.NetworkCard;
import com.payoneer.mrs.payment.ui.model.PaymentNetwork;
import com.payoneer.mrs.payment.ui.model.PaymentSession;
import com.payoneer.mrs.payment.ui.model.PresetCard;
import com.payoneer.mrs.payment.validation.Validator;

import android.content.Context;
import android.text.TextUtils;

/**
 * The PaymentSessionService providing asynchronize loading of the PaymentSession.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class PaymentSessionService {
    private final ListConnection listConnection;
    private PaymentSessionListener listener;
    private WorkerTask<PaymentSession> sessionTask;

    /**
     * Create a new PaymentSessionService, this service is used to load the PaymentSession.
     *
     * @param context context in which this service will run
     */
    public PaymentSessionService(Context context) {
        this.listConnection = new ListConnection(context);
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
    }

    /**
     * Check if this service is currently active, i.e. is is loading a payment session or posting an operation.
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return sessionTask != null && sessionTask.isSubscribed();
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
     * Check if the provided operationType is supported by this PaymentSessionService
     *
     * @param operationType the operation type to check
     * @return true when supported, false otherwise
     */
    public boolean isSupportedNetworkOperationType(String operationType) {
        return NetworkOperationType.CHARGE.equals(operationType) || NetworkOperationType.PRESET.equals(operationType);
    }

    private PaymentSession asyncLoadPaymentSession(String listUrl, Context context) throws PaymentException {
        ListResult listResult = listConnection.getListResult(listUrl);

        String integrationType = listResult.getIntegrationType();
        if (!MOBILE_NATIVE.equals(integrationType)) {
            throw new PaymentException("Integration type is not supported: " + integrationType);
        }

        String operationType = listResult.getOperationType();
        if (!isSupportedNetworkOperationType(operationType)) {
            throw new PaymentException("List operationType is not supported: " + operationType);
        }
        Map<String, PaymentNetwork> networks = loadPaymentNetworks(listResult);
        Map<String, PaymentGroup> groups = loadPaymentGroups(context);

        Validator validator = loadValidator(context);

        PresetCard presetCard = createPresetCard(listResult, networks);
        List<AccountCard> accountCards = createAccountCards(listResult, networks);
        List<NetworkCard> networkCards = createNetworkCards(networks, groups);

        return new PaymentSession(listResult, presetCard, accountCards, networkCards, validator);
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
            String code = network.getCode();
            if (NetworkServiceLookup.supports(code, network.getMethod())) {
                items.put(code, new PaymentNetwork(network));
            }
        }
        return items;
    }

    private List<NetworkCard> createNetworkCards(Map<String, PaymentNetwork> networks, Map<String, PaymentGroup> groups)
        throws PaymentException {
        Map<String, NetworkCard> cards = new LinkedHashMap<>();
        PaymentGroup group;

        for (PaymentNetwork network : networks.values()) {
            group = groups.get(network.getCode());

            if (group == null) {
                addNetwork2SingleCard(cards, network);
            } else {
                addNetwork2GroupCard(cards, network, group);
            }
        }
        return new ArrayList<>(cards.values());
    }

    private void addNetwork2SingleCard(Map<String, NetworkCard> cards, PaymentNetwork network) throws PaymentException {
        NetworkCard card = new NetworkCard();
        card.addPaymentNetwork(network);
        cards.put(network.getCode(), card);
    }

    private void addNetwork2GroupCard(Map<String, NetworkCard> cards, PaymentNetwork network, PaymentGroup group) throws PaymentException {
        String code = network.getCode();
        String groupId = group.getId();
        String regex = group.getSmartSelectionRegex(code);

        if (TextUtils.isEmpty(regex)) {
            throw new PaymentException("Missing regex for network: " + code + " in group: " + groupId);
        }
        NetworkCard card = cards.get(groupId);
        if (card == null) {
            card = new NetworkCard();
            cards.put(groupId, card);
        }
        // a network can always be added to an empty card
        if (!card.addPaymentNetwork(network)) {
            addNetwork2SingleCard(cards, network);
            return;
        }
        card.getSmartSwitch().addSelectionRegex(code, regex);
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
            cards.add(new AccountCard(account));
        }
        return cards;
    }

    private PresetCard createPresetCard(ListResult listResult, Map<String, PaymentNetwork> networks) {
        PresetAccount account = listResult.getPresetAccount();
        if (account == null) {
            return null;
        }
        return new PresetCard(account);
    }

    private Map<String, PaymentGroup> loadPaymentGroups(Context context) throws PaymentException {
        return ResourceLoader.loadPaymentGroups(context.getResources(), R.raw.groups);
    }

    private Validator loadValidator(Context context) throws PaymentException {
        return new Validator(ResourceLoader.loadValidations(context.getResources(), R.raw.validations));
    }
}
