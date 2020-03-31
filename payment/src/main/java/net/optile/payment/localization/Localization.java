/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import static net.optile.payment.localization.LocalizationKey.ACCOUNTHINT_TITLE;

import java.util.Map;

import android.text.TextUtils;
import net.optile.payment.model.Interaction;

/**
 * Class holding individual localizations to provide easy access to all translations.
 */
public final class Localization {
    private final Map<String, LocalizationHolder> networks;
    private final LocalizationHolder shared;
    private static Localization instance;

    /**
     * Get the currently set Localization instance
     *
     * @return the current instance or null if not previously set 
     */
    public static Localization getInstance() {
        return instance;
    }

    /**
     * Set the current Localization instance
     *
     * @param newInstance to be set as the current instance
     */
    public static void setInstance(Localization newInstance) {
        instance = newInstance;
    }

    /**
     * Construct a new Localization with a shared localization and a list of network localizations
     *
     * @param shared the shared localization
     * @param networks the list of network localizations
     */
    public Localization(LocalizationHolder shared, Map<String, LocalizationHolder> networks) {
        this.shared = shared;
        this.networks = networks;
    }

    /**
     * Helper method to obtain the translation for the given key from the shared holder
     *
     * @param key of the translation
     * @return the translation or null if not found
     */
    public static String translate(String key) {
        Localization loc = getInstance();
        return loc != null ? loc.getSharedTranslation(key) : null;
    }

    /**
     * Helper method to obtain the translation for the given key from the localization file.
     *
     * @param networkCode name of the localization file
     * @param key of the translation
     * @return the translation or null if not found
     */
    public static String translate(String networkCode, String key) {
        Localization loc = getInstance();
        return loc != null ? loc.getNetworkTranslation(networkCode, key) : null;
    }

    /**
     * Helper method to obtain the translation for the interaction.
     *
     * @param interaction to be translated
     * @return translation of the interaction or null if not found
     */
    public static String translateInteraction(Interaction interaction) {
        Localization loc = getInstance();
        return loc != null ? loc.getSharedTranslation(LocalizationKey.interactionKey(interaction)) : null;
    }

    /**
     * Helper method to check if a translation exist for the account hint.
     *
     * @param networkCode the name of the localization holder
     * @param account name of the account
     * @return true if it has a translation for the account hint, false otherwise
     */
    public static boolean hasAccountHint(String networkCode, String account) {
        String val = translateAccountHint(networkCode, account, ACCOUNTHINT_TITLE);
        return !TextUtils.isEmpty(val);
    }

    /**
     * Helper method to obtain the translation for the error from the specified file.
     *
     * @param networkCode name of the localization file
     * @param error the error which should be translated
     * @return the translation or null if not found
     */
    public static String translateError(String networkCode, String error) {
        return translate(networkCode, LocalizationKey.errorKey(error));
    }

    /**
     * Helper method to obtain the translation for the account label.
     *
     * @param networkCode name of the localization file
     * @param account name of the account
     * @return translation or null if not found
     */
    public static String translateAccountLabel(String networkCode, String account) {
        return translate(networkCode, LocalizationKey.accountLabelKey(account));
    }

    /**
     * Helper method to obtain the translation of the account hint with the given type.
     *
     * @param networkCode name of the localization file
     * @param account name of the account
     * @param type of the account hint, either ACCOUNTHINT_TEXT or ACCOUNTHINT_TITLE
     * @return the translation or null if not found
     */
    public static String translateAccountHint(String networkCode, String account, String type) {
        return translate(networkCode, LocalizationKey.accountHintKey(account, type));
    }

    /**
     * Get the translation from the network localization the given network code.
     * If the localization does not exist or does not contain the translation then return null
     *
     * @param networkCode name of the network localization
     * @param key of the translation
     * @return the translation or null if the translation was not found
     */
    public String getNetworkTranslation(String networkCode, String key) {
        LocalizationHolder holder = networks != null ? networks.get(networkCode) : null;
        return holder != null ? holder.translate(key) : null;
    }

    /**
     * Get the translation from the shared localization holder.
     * If the shared holder does not exist or does not contain the translation then return null.
     *
     * @param key of the translation
     * @return the translation or null if the translation was not found
     */
    public String getSharedTranslation(String key) {
        return shared != null ? shared.translate(key) : null;
    }
}
