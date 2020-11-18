/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.model;

import static net.optile.payment.localization.LocalizationKey.NETWORK_LABEL;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.optile.payment.localization.Localization;
import net.optile.payment.localization.LocalizationKey;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.OperationType;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for holding the data of a PresetCard in the list
 */
public final class PresetCard implements PaymentCard {
    private final PresetAccount account;

    public PresetCard(PresetAccount account) {
        this.account = account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsLink(String name, URL url) {
        return PaymentUtils.equalsAsString(getLink(name), url);
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
    public String getOperationType() {
        return account.getOperationType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaymentMethod() {
        return account.getMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return account.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel() {
        return Localization.translate(account.getCode(), NETWORK_LABEL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InputElement> getInputElements() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputElement getInputElement(String name) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getButton() {
        return LocalizationKey.operationButtonKey(OperationType.PRESET);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTextInputChanged(String type, String text) {
        return false;
    }

    public PresetAccount getPresetAccount() {
        return account;
    }

    public URL getLink(String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    public AccountMask getMaskedAccount() {
        return account.getMaskedAccount();
    }
}
