/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import static net.optile.payment.localization.LocalizationKey.BUTTON_OK;
import static net.optile.payment.localization.LocalizationKey.BUTTON_CANCEL;
import static net.optile.payment.localization.LocalizationKey.BUTTON_RETRY;
import static net.optile.payment.localization.LocalizationKey.BUTTON_UPDATE;
import static net.optile.payment.localization.LocalizationKey.CHARGE_INTERRUPTED;
import static net.optile.payment.localization.LocalizationKey.CHARGE_TEXT;
import static net.optile.payment.localization.LocalizationKey.CHARGE_TITLE;
import static net.optile.payment.localization.LocalizationKey.ERROR_CONNECTION;
import static net.optile.payment.localization.LocalizationKey.ERROR_DEFAULT;
import static net.optile.payment.localization.LocalizationKey.LIST_HEADER_NETWORKS;
import static net.optile.payment.localization.LocalizationKey.LIST_TITLE;
import static net.optile.payment.localization.LocalizationKey.REDIRECT_TITLE;
import static net.optile.payment.localization.LocalizationKey.REDIRECT_BUTTON;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import net.optile.payment.R;

/**
 * Class holding the local translations used by the Android SDK.
 */
public final class LocalTranslations {

    private final Map<String, String> localTranslations;

    public LocalTranslations() {
        localTranslations = new HashMap<String, String>();
    }

    /**
     * Load the local translations into this Localization Object.
     * The local translations serve as a fallback when the translation could not be found in any of the files.
     *
     * @param context used to load the local translations
     */
    public void load(Context context) {
        localTranslations.put(BUTTON_OK, context.getString(R.string.pmlocal_button_ok));
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
        localTranslations.put(REDIRECT_TITLE, context.getString(R.string.pmlocal_redirect_title));
        localTranslations.put(REDIRECT_BUTTON, context.getString(R.string.pmlocal_redirect_button));
    }

    public void clear() {
        localTranslations.clear();
    }

    /**
     * Get the local translation, if the translation is not stored in this storage then return the defaultValue.
     *
     * @param key of the translation
     * @param defaultValue returned when the translation could not be found
     * @return the translation or defaultValue if the translation was not found
     */
    public String getTranslation(String key, String defaultValue) {
        String value = localTranslations.get(key);
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }
}
