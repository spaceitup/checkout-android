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

    public final static String KEY_BUTTON_DATE = "button.update.label";
    public final static String KEY_AUTO_REGISTRATION = "autoRegistrationLabel";
    public final static String KEY_ALLOW_RECURRENCE = "allowRecurrenceLabel";

    public final static String HINT_TITLE = "title";
    public final static String HINT_TEXT = "text";
    public final static String HINT_WHERE = "where";
    public final static String HINT_WHAT = "what";
    public final static String HINT_WHY = "why";

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

    public String translateInteraction(Interaction interaction) {
        String key = "interaction.".concat(interaction.getCode()).concat(".").concat(interaction.getReason());
        return translate(key);
    }

    public String translateHint(String name, String subject, String label) {
        String key = "account.".concat(name).concat(".").concat("hint.").concat(subject).concat(".").concat(label);
        return translate(key);
    }

    public boolean containsHint(String name) {
        String val = translateHint(name, HINT_WHERE, HINT_TITLE);
        return !TextUtils.isEmpty(val);
    }

    public Properties getProperties() {
        return lang;
    }
}
