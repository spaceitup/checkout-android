/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import static net.optile.payment.localization.LocalizationKey.ACCOUNTHINT_TITLE;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import android.text.TextUtils;
import net.optile.payment.model.Interaction;

/**
 * Class holding the localization files and local translations..
 *
 * This class implements a fallback mechanism with the following logic:
 * 1. If the translation is not available in a localization file with the given name, it will try to obtain the translation from the shared file.
 * 2. If the shared file is not set or does not contain the translation, then it will try to obtain the translation from the local translations.
 * 3. If the local translations have not been set or does not contain the translation then the default value will be returned.
 */
public final class Localization {
    private final Map<String, Properties> files;
    private LocalTranslations localTranslations;
    private Properties sharedFile;
    private String localizationId;
    
    private Localization() {
        this.files = new HashMap<>();
    }

    /**
     * Get the instance of this Localization
     *
     * @return the instance of this localization
     */
    public static Localization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void setLocalizationId(String localizationId) {
        this.localizationId = localizationId;
    }

    public String getLocalizationId() {
        return localizationId;
    }
        
    public void clearAll() {
        clearFiles();
        localTranslations = null;
    }

    public void clearFiles() {
        files.clear();
        sharedFile = null;
    }

    public boolean hasLocalTranslations() {
        return localTranslations != null;
    }
    
    public void setLocalTranslations(LocalTranslations localTranslations) {
        this.localTranslations = localTranslations;
    }

    public boolean hasSharedFile() {
        return sharedFile != null;
    }

    public void setSharedFile(Properties sharedFile) {
        this.sharedFile = sharedFile;
    }

    public boolean hasFile(String fileName) {
        return files.containsKey(fileName);
    }
    
    public void putFile(String fileName, Properties properties) {
        files.put(fileName, properties);
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
        return translate(fileName, LocalizationKey.errorKey(error), null);
    }

    /**
     * Helper method to obtain the translation for the interaction.
     *
     * @param interaction to be translated
     * @return translation or null if not found
     */
    public static String translateInteraction(Interaction interaction) {
        return getInstance().getTranslation(LocalizationKey.interactionKey(interaction), null);
    }

    /**
     * Helper method to obtain the translation for the account label.
     *
     * @param fileName name of the localization file
     * @param account name of the account
     * @return translation or null if not found
     */
    public static String translateAccountLabel(String fileName, String account) {
        return translate(fileName, LocalizationKey.accountLabelKey(account), null);
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
        return translate(fileName, LocalizationKey.accountHintKey(account, type), null);
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
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        if (localTranslations == null) {
            return defaultValue;
        }
        return localTranslations.getTranslation(key, defaultValue);
    }

    private String getValue(Properties prop, String key) {
        return prop != null ? prop.getProperty(key) : null;
    }

    private static class InstanceHolder {
        static final Localization INSTANCE = new Localization();
    }
}
