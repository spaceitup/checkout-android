/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import java.util.Properties;
import java.net.URL;
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
    private final String name;
    private final URL url;

    /**
     * Construct an empty LanguageFile
     *
     * @param name the unique name of this language file 
     * @param url pointing to the location of this language file
     */
    public LanguageFile(String name, URL url) {
        this.name = name;
        this.url = url;
        this.lang = new Properties();
    }

    public String getName() {
        return name;
    }

    public URL getURL() {
        return url;
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
