/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import android.util.Log;
import android.text.TextUtils;
import net.optile.payment.model.Interaction;

/**
 * Class holding the localization entries for the payment page and ApplicableNetworks
 */
public final class Localization {

    // all entries starting with pmlocal. originate from the strings.xml file
    public final static String AUTO_REGISTRATION = "autoRegistrationLabel";
    public final static String ALLOW_RECURRENCE = "allowRecurrenceLabel";
    public final static String NETWORK_LABEL = "network.label";

    public final static String BUTTON_BACK = "button.back.label";
    public final static String BUTTON_CANCEL = "pmlocal.button.cancel";
    public final static String BUTTON_UPDATE = "pmlocal.button.update";
    public final static String BUTTON_RETRY = "pmlocal.button.retry";

    public final static String LIST_TITLE = "pmlocal.list.title";
    public final static String LIST_PRESET_TEXT = "networks.preset.text";
    public final static String LIST_HEADER_PRESET = "networks.preset.title";
    public final static String LIST_HEADER_SAVEDACCOUNTS = "savedAccountsLabel";
    public final static String LIST_HEADER_OTHERACCOUNTS = "addNewAccountLabel"; 
    public final static String LIST_HEADER_NETWORKS = "pmlocal.list.header.networks";

    public final static String CHARGE_TITLE = "pmlocal.charge.title";
    public final static String CHARGE_TEXT = "pmlocal.charge.text";
    public final static String CHARGE_INTERRUPTED = "pmlocal.charge.interupted";

    public final static String ERROR_CONNECTION = "pmlocal.error.connection";
    public final static String ERROR_DEFAULT = "pmlocal.error.default";
    
    public final static String ACCOUNTHINT_TITLE = "title";
    public final static String ACCOUNTHINT_TEXT = "text";

    private final Map<String, Properties> items;
    private Properties fallback;

    /**
     * Get the instance of this Localization
     *
     * @return the instance of this localization
     */
    public static Localization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private Localization() {
        this.items = new HashMap<>();
    }

    public void setFallback(Properties fallback) {
        this.fallback = fallback;
    }
    
    public void putProperties(String propName, Properties properties) {
        items.put(propName, properties);
    }

    public boolean hasFallback() {
        return fallback != null;
    }

    public boolean hasProperties(String propName) {
        return items.containsKey(propName);
    }

    public static boolean hasAccountHint(String propName, String account) {
        String val = translateAccountHint(propName, account, ACCOUNTHINT_TITLE);
        return !TextUtils.isEmpty(val);
    }
    
    public static String translateError(String propName, String error) {
        return translate(propName, "error.".concat(error), null);
    }

    public static String translateInteraction(Interaction interaction) {
        String key = "interaction.".concat(interaction.getCode()).concat(".").concat(interaction.getReason());
        return getInstance().getFallbackProperty(key, null);
    }

    public static String translateAccountLabel(String propName, String account) {
        return translate(propName, "account.".concat(account).concat(".label"), null);
    }

    public static String translateAccountHint(String propName, String account, String type) {
        String key = "account.".concat(account).concat(".").concat("hint.").concat("where.").concat(type);
        return translate(propName, key, null);
    }

    public static String translate(String key) {
        return getInstance().getFallbackProperty(key, null);
    }

    public static String translate(String propName, String key) {
        return translate(propName, key, null);
    }

    public static String translate(String propName, String key, String defaultValue) {
        Localization loc = getInstance();
        String val = null;
        if (propName != null) {
            val = loc.getProperty(loc.items.get(propName), key);
            if (!TextUtils.isEmpty(val)) {
                return val;
            }
        }
        return loc.getFallbackProperty(key, defaultValue);
    }

    private String getFallbackProperty(String key, String defaultValue) {
        String val = getProperty(fallback, key);
        return TextUtils.isEmpty(val) ? defaultValue : val;
    }

    private String getProperty(Properties prop, String key) {
        return prop != null ? prop.getProperty(key) : null;
    }

    private static class InstanceHolder {
        static final Localization INSTANCE = new Localization();
    }
}
