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

import android.content.Context;
import android.text.TextUtils;
import net.optile.payment.R;
import net.optile.payment.model.Interaction;

/**
 * Class holding the localization files and local translations.
 * If the translation is not available in a localization file with the given name, it will try to obtain the translation from the shared file.
 * If the shared file is not set or does not contain the translation, then it will try to obtain the translation from the local translations map.
 */
public final class Localization {

    // all entries starting with pmlocal originate from the local strings.xml file
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

    private final Map<String, Properties> files;
    private final Map<String, String> localTranslations;
    private Properties sharedFile;

    private Localization() {
        this.files = new HashMap<>();
        this.localTranslations = new HashMap<>();
    }

    /**
     * Get the instance of this Localization
     *
     * @return the instance of this localization
     */
    public static Localization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Helper method to check if a translation exist for the account hint.
     *
     * @param fileName the name of the Properties file
     * @param account name of the account
     * @return true if it has a translation for the account hint, false otherwise
     */
    public static boolean hasAccountHint(String fileName, String account) {
        String val = translateAccountHint(fileName, account, ACCOUNTHINT_TITLE);
        return !TextUtils.isEmpty(val);
    }

    /**
     * Helper method to obtain the translation for the error from the specified file.
     *
     * @param fileName name of the localization file
     * @param error the error which should be translated
     * @return the translation or null if not found
     */
    public static String translateError(String fileName, String error) {
        return translate(fileName, "error." + error, null);
    }

    /**
     * Helper method to obtain the translation for the interaction.
     *
     * @param interaction to be translated
     * @return translation or null if not found
     */
    public static String translateInteraction(Interaction interaction) {
        String key = "interaction." + interaction.getCode() + "." + interaction.getReason();
        return getInstance().getTranslation(key, null);
    }

    /**
     * Helper method to obtain the translation for the account label.
     *
     * @param fileName name of the localization file
     * @param account name of the account
     * @return translation or null if not found
     */
    public static String translateAccountLabel(String fileName, String account) {
        return translate(fileName, "account." + account + ".label", null);
    }

    /**
     * Helper method to obtain the translation of the account hint with the given type.
     *
     * @param fileName name of the localization file
     * @param account name of the account
     * @param type of the account hint, either ACCOUNTHINT_TEXT or ACCOUNTHINT_TITLE
     * @return the translation or null if not found
     */
    public static String translateAccountHint(String fileName, String account, String type) {
        String key = "account." + account + "." + "hint." + "where." + type;
        return translate(fileName, key, null);
    }

    /**
     * Helper method to obtain the translation for the given key from the shared file or local translations map.
     *
     * @param key of the translation
     * @return the translation or null if not found
     */
    public static String translate(String key) {
        return getInstance().getTranslation(key, null);
    }

    /**
     * Helper method to obtain the translation for the given key from the localization file.
     *
     * @param fileName name of the localization file
     * @param key of the translation
     * @return the translation or null if not found
     */
    public static String translate(String fileName, String key) {
        return translate(fileName, key, null);
    }

    /**
     * Helper method to obtain the translation for the given key from the localization file.
     * If the translation could not be found then return the defaultValue.
     *
     * @param fileName name of the localization file
     * @param key of the translation
     * @param defaultValue returned when the translation could not be found
     * @return the translation or the defaultValue if the translation was not found
     */
    public static String translate(String fileName, String key, String defaultValue) {
        return getInstance().getTranslation(fileName, key, defaultValue);
    }

    /**
     * Get the translation from the localization file with the given name.
     * If the file does not contain the translation then try to obtain it from the shared file.
     * If the shared file does not exist or does not contain the translation then try to obtain the translation from the local translations map.
     *
     * @param fileName name of the localization file
     * @param key of the translation
     * @param defaultValue returned when the translation could not be found
     * @return the translation or defaultValue if the translation was not found
     */
    public String getTranslation(String fileName, String key, String defaultValue) {
        if (!TextUtils.isEmpty(fileName)) {
            String value = getValue(files.get(fileName), key);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return getTranslation(key, defaultValue);
    }

    /**
     * Get the translation from the shared localization file.
     * If the shared file does not exist or does not contain the translation then try to obtain the translation from the local translations map.
     *
     * @param key of the translation
     * @param defaultValue returned when the translation could not be found
     * @return the translation or defaultValue if the translation was not found
     */
    public String getTranslation(String key, String defaultValue) {
        String value = getValue(sharedFile, key);
        if (TextUtils.isEmpty(value)) {
            value = localTranslations.get(key);
        }
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    /**
     * Load the local translations into this Localization Object.
     * The local translations serve as a fallback when the translation could not be found in any of the files.
     *
     * @param context used to load the local translations
     */
    public void loadLocalTranslations(Context context) {
        if (localTranslations.size() != 0) {
            return;
        }
        localTranslations.put(BUTTON_CANCEL, context.getString(R.string.pmlocal_button_cancel));
        localTranslations.put(BUTTON_RETRY, context.getString(R.string.pmlocal_button_retry));
        localTranslations.put(BUTTON_UPDATE, context.getString(R.string.pmlocal_button_update));
        localTranslations.put(LIST_TITLE, context.getString(R.string.pmlocal_list_title));
        localTranslations.put(LIST_HEADER_NETWORKS, context.getString(R.string.pmlocal_list_header_networks));
        localTranslations.put(CHARGE_TITLE, context.getString(R.string.pmlocal_charge_title));
        localTranslations.put(CHARGE_TEXT, context.getString(R.string.pmlocal_charge_text));
        localTranslations.put(CHARGE_INTERRUPTED, context.getString(R.string.pmlocal_charge_interrupted));
        localTranslations.put(ERROR_CONNECTION, context.getString(R.string.pmlocal_error_connection));
        localTranslations.put(ERROR_DEFAULT, context.getString(R.string.pmlocal_error_default));
    }

    public void clear() {
        files.clear();
        localTranslations.clear();
        sharedFile = null;
    }

    public void setSharedFile(Properties sharedFile) {
        this.sharedFile = sharedFile;
    }

    public boolean hasSharedFile() {
        return sharedFile != null;
    }

    public void putFile(String fileName, Properties properties) {
        files.put(fileName, properties);
    }

    public boolean hasFile(String fileName) {
        return files.containsKey(fileName);
    }

    private String getValue(Properties prop, String key) {
        return prop != null ? prop.getProperty(key) : null;
    }

    private static class InstanceHolder {
        static final Localization INSTANCE = new Localization();
    }
}
