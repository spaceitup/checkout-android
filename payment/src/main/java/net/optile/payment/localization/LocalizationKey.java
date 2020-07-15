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

    public final static String AUTO_REGISTRATION_OPTIONAL = "networks.registration.optional.label";
    public final static String AUTO_REGISTRATION_FORCED = "networks.registration.forced.label";
    public final static String ALLOW_RECURRENCE_OPTIONAL = "networks.recurrence.optional.label";
    public final static String ALLOW_RECURRENCE_FORCED = "networks.recurrence.forced.label";

    public final static String BUTTON_OK = "button.ok.label";
    public final static String BUTTON_BACK = "button.back.label";
    public final static String BUTTON_CANCEL = "button.cancel.label";
    public final static String BUTTON_RETRY = "button.retry.label";

    public final static String LIST_TITLE = "paymentpage.title";
    public final static String LIST_PRESET_TEXT = "networks.preset.text";
    public final static String LIST_HEADER_PRESET = "networks.preset.title";
    public final static String LIST_HEADER_ACCOUNTS = "accounts.title";
    public final static String LIST_HEADER_NETWORKS = "networks.title";
    public final static String LIST_HEADER_NETWORKS_OTHER = "networks.other.title";
    public final static String LIST_GROUPEDCARDS_TITLE = "groups.cards.title";

    public final static String CHARGE_TITLE = "messages.processing.title";
    public final static String CHARGE_TEXT = "messages.processing.text";
    public final static String CHARGE_INTERRUPTED = "messages.processing.interrupted.text";

    public final static String ERROR_CONNECTION_TITLE = "messages.error.internet.title";
    public final static String ERROR_CONNECTION_TEXT = "messages.error.internet.text";
    public final static String ERROR_DEFAULT_TITLE = "messages.error.default.title";
    public final static String ERROR_DEFAULT_TEXT = "messages.error.default.text";

    public final static String LABEL_TITLE = "title";
    public final static String LABEL_TEXT = "text";

    public static String errorKey(String error) {
        return "error." + error;
    }

    public static String accountLabelKey(String account) {
        return "account." + account + ".label";
    }

    public static String accountHintKey(String account, String labelType) {
        return "account." + account + "." + "hint." + "where." + labelType;
    }

    public static String operationButtonKey(String operationType) {
        return "button.operation." + operationType + ".label";
    }

    public static String interactionKey(Interaction interaction, String labelType) {
        return "interaction." + interaction.getCode() + "." + interaction.getReason() + "." + labelType;
    }
}
