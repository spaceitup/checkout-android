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

package net.optile.payment.ui.paymentpage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.optile.payment.model.AccountRegistration;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.core.LanguageFile;

/**
 * Class for holding the ApplicableNetwork
 */
final class AccountItem extends PaymentItem {

    final int type;    
    private final AccountRegistration account;
    private final ApplicableNetwork network;
    
    /** 
     * Construct a new AccountItem 
     * 
     * @param type 
     * @param account 
     * @param network 
     */
    AccountItem(int type, AccountRegistration account, ApplicableNetwork network) {
        this.type = type;
        this.account = account;
        this.network = network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    URL getLink(String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String getPaymentMethod() {
        return network.getMethod();
    }
    
    boolean isSelected() {
        return PaymentUtils.isTrue(account.getSelected());
    }

    List<InputElement> getInputElements() {
        List<InputElement> elements = account.getLocalizedInputElements();
        return elements == null ? new ArrayList<>() : elements;
    }
}
