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
 * Class for representing an AccountCard in the PaymentPage list
 */
final class AccountCard implements PaymentCard {

    private final AccountRegistration account;
    private final ApplicableNetwork network;

    private boolean hasExpiryDate;
    private LanguageFile lang;
    
    AccountCard(AccountRegistration account, ApplicableNetwork network) {
        this.account = account;
        this.network = network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getOperationLink() {
        return getLink("operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return network.getMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LanguageFile getLang() {
        return lang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasExpiryDate() {
        return hasExpiryDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPreselected() {
        return PaymentUtils.isTrue(account.getSelected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        List<InputElement> elements = account.getLocalizedInputElements();
        return elements == null ? new ArrayList<>() : elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return network.getButton();
    }
    
    URL getLink(String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    void setExpiryDate(boolean hasExpiryDate) {
        this.hasExpiryDate = hasExpiryDate;
    }

    void setLang(LanguageFile lang) {
        this.lang = lang;
    }
}
