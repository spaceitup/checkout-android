/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * Class for storing and selecting payment networks based on smart switch logic
 */
public final class SmartSwitch {

    private final Map<String, Pattern> smartMapping;
    private final List<PaymentNetwork> networks;
    private final List<PaymentNetwork> smartSelected;
    private final List<PaymentNetwork> smartBuffer;

    public SmartSwitch(List<PaymentNetwork> networks) {
        this.networks = networks;
        this.smartMapping = new HashMap<>();
        this.smartSelected = new ArrayList<>();
        this.smartBuffer = new ArrayList<>();
    }

    public void addSelectionRegex(String code, String regex) {
        if (!TextUtils.isEmpty(regex)) {
            smartMapping.put(code, Pattern.compile(regex));
        }
    }

    /**
     * Get the list of smart selected PaymentNetworks.
     *
     * @return the list of smart selected PaymentNetworks.
     */
    public List<PaymentNetwork> getAllSelected() {
        return smartSelected;
    }

    /**
     * Get the first smart selected PaymentNetwork, may return null if none are selected.
     *
     * @return the first smart selected PaymentNetwork.
     */
    public PaymentNetwork getFirstSelected() {
        return smartSelected.isEmpty() ? null : smartSelected.get(0);
    }

    /**
     * Are any PaymentNetworks selected based on smart switch.
     *
     * @return true when there are selected payment networks, false otherwise
     */
    public boolean hasSelected() {
        return smartSelected.size() > 0;
    }

    /**
     * Check if the PaymentNetwork is smart selected, it is smart selected when the provided number input matches
     * the regex of this PaymentMethod in the groups settings file. A PaymentMethod is always smart selected when
     * there is only one PaymentMethod in the NetworkCard.
     *
     * @param network to check if smart selection
     * @return true when smart selected, false otherwise
     */
    public boolean isSelected(PaymentNetwork network) {
        if (networks.size() == 1 && networks.get(0) == network) {
            return true;
        }
        return smartSelected.contains(network);
    }

    /**
     * Validate the PaymentMethods for smart selection given the new text
     *
     * @param text to validate for smart selection
     * @return true when the smart selection of payment methods has changed, false otherwise
     */
    public boolean validate(String text) {
        smartBuffer.clear();

        if (text != null) {
            for (PaymentNetwork network : networks) {
                Pattern pattern = smartMapping.get(network.getCode());

                if (pattern != null && pattern.matcher(text).matches()) {
                    smartBuffer.add(network);
                }
            }
        }
        if (!smartSelected.equals(smartBuffer)) {
            smartSelected.clear();
            smartSelected.addAll(smartBuffer);
            return true;
        }
        return false;
    }
}

