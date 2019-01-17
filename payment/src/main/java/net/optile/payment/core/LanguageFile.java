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

package net.optile.payment.core;

import java.util.Properties;

import android.text.TextUtils;
import net.optile.payment.model.Interaction;

/**
 * Class holding the language entries for the payment page, ApplicableNetwork or AccountRegistration
 */
public final class LanguageFile {

    public final static String KEY_BUTTON_UPDATE = "button.update.label";
    public final static String KEY_BUTTON_BACK = "button.back.label";
    public final static String KEY_AUTO_REGISTRATION = "autoRegistrationLabel";
    public final static String KEY_ALLOW_RECURRENCE = "allowRecurrenceLabel";

    public final static String TITLE = "title";
    public final static String TEXT = "text";
    private final Properties lang;

    /**
     * Construct an empty LanguageFile
     */
    public LanguageFile() {
        this.lang = new Properties();
    }

    public String translate(String key) {
        return translate(key, null);
    }

    public String translate(String key, String defValue) {
        return key != null ? lang.getProperty(key, defValue) : defValue;
    }

    public String translateError(String error) {
        return translate("error.".concat(error));
    }

    public String translateAccountLabel(String account) {
        return translate("account.".concat(account).concat(".label"));
    }

    public String translateAccountHint(String account, String type) {
        String key = "account.".concat(account).concat(".").concat("hint.").concat("where.").concat(type);
        return translate(key);
    }

    public boolean containsAccountHint(String account) {
        String val = translateAccountHint(account, TITLE);
        return !TextUtils.isEmpty(val);
    }

    public String translateInteraction(Interaction interaction) {
        String key = "interaction.".concat(interaction.getCode()).concat(".").concat(interaction.getReason());
        return translate(key);
    }

    public Properties getProperties() {
        return lang;
    }
}
