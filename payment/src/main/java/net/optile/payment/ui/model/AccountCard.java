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

package net.optile.payment.ui.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.AccountRegistration;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.InputElement;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for representing an AccountCard in the PaymentPage list
 */
public final class AccountCard implements PaymentCard {

    public final AccountRegistration account;
    public final ApplicableNetwork network;
    private LanguageFile lang;

    public AccountCard(AccountRegistration account, ApplicableNetwork network) {
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

    public void setLang(LanguageFile lang) {
        this.lang = lang;
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
    public boolean isPreselected() {
        return PaymentUtils.isTrue(account.getSelected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return network.getButton();
    }

    public URL getLink(String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    public AccountMask getMaskedAccount() {
        return account.getMaskedAccount();
    }
}
