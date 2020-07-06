/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import static net.optile.payment.localization.LocalizationKey.BUTTON_CANCEL;
import static net.optile.payment.localization.LocalizationKey.BUTTON_OK;
import static net.optile.payment.localization.LocalizationKey.BUTTON_RETRY;
import static net.optile.payment.localization.LocalizationKey.ERROR_CONNECTION_TEXT;
import static net.optile.payment.localization.LocalizationKey.ERROR_DEFAULT_TEXT;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import net.optile.payment.R;

/**
 * Class storing local localizations
 */
public final class LocalLocalizationHolder implements LocalizationHolder {

    private final Map<String, String> map;

    /**
     * Construct a new local localization holder
     *
     * @param context containing the local localizations
     */
    public LocalLocalizationHolder(Context context) {
        map = new HashMap<>();
        map.put(BUTTON_CANCEL, context.getString(R.string.button_cancel_label));
        map.put(BUTTON_RETRY, context.getString(R.string.button_retry_label));
        map.put(BUTTON_OK, context.getString(R.string.button_ok_label));

        map.put(ERROR_CONNECTION_TEXT, context.getString(R.string.error_connection_text));
        map.put(ERROR_DEFAULT_TEXT, context.getString(R.string.error_default_text));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String translate(String key) {
        return map.get(key);
    }
}
