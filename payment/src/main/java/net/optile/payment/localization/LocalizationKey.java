/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import net.optile.payment.model.Interaction;

/**
 * Class holding the localization keys and helper methods to create keys.
 */
public final class LocalizationKey {

    // all entries starting with pmlocal originate from the local strings.xml file
    public final static String AUTO_REGISTRATION = "autoRegistrationLabel";
    public final static String ALLOW_RECURRENCE = "allowRecurrenceLabel";
    public final static String NETWORK_LABEL = "network.label";

    public final static String BUTTON_OK = "button.ok.label";    
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

    public final static String REDIRECT_TITLE = "pmlocal.redirect.title";
    public final static String REDIRECT_BUTTON = "pmlocal.redirect.button";    
    
    public final static String ACCOUNTHINT_TITLE = "title";
    public final static String ACCOUNTHINT_TEXT = "text";

    public static String errorKey(String error) {
        return "error." + error;
    }

    public static String accountLabelKey(String account) {
        return "account." + account + ".label";
    }

    public static String accountHintKey(String account, String type) {
        return "account." + account + "." + "hint." + "where." + type;
    }

    public static String interactionKey(Interaction interaction) {
        return "interaction." + interaction.getCode() + "." + interaction.getReason();
    }
}
