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

import android.text.TextUtils;

/**
 * Class for storing and selecting payment networks based on smart switch logic
 */
public final class SmartSwitch {

    private final HashMap<String, String> smartMapping;
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
        smartMapping.put(code, regex);
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
        return smartSelected.size() > 0 ? smartSelected.get(0) : null;
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
        String regex;

        if (text != null) {
            for (PaymentNetwork network : networks) {
                regex = smartMapping.get(network.getCode());

                if (!TextUtils.isEmpty(regex) && text.matches(regex)) {
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

