/*
 * Copyright (c) 2020 optile GmbH
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
import static net.optile.payment.localization.LocalizationKey.ERROR_CONNECTION_TITLE;
import static net.optile.payment.localization.LocalizationKey.ERROR_DEFAULT_TEXT;
import static net.optile.payment.localization.LocalizationKey.ERROR_DEFAULT_TITLE;

import java.util.HashMap;

import android.content.Context;
import net.optile.payment.R;

/**
 * Class storing local localizations
 */
public final class LocalLocalizationHolder extends MapLocalizationHolder {

    /**
     * Construct a new local localization holder
     *
     * @param context containing the local localizations
     */
    public LocalLocalizationHolder(Context context) {
        super(new HashMap<String, String>());
        map.put(BUTTON_CANCEL, context.getString(R.string.button_cancel_label));
        map.put(BUTTON_RETRY, context.getString(R.string.button_retry_label));
        map.put(BUTTON_OK, context.getString(R.string.button_ok_label));

        map.put(ERROR_CONNECTION_TITLE, context.getString(R.string.error_connection_title));
        map.put(ERROR_CONNECTION_TEXT, context.getString(R.string.error_connection_text));
        map.put(ERROR_DEFAULT_TITLE, context.getString(R.string.error_default_title));
        map.put(ERROR_DEFAULT_TEXT, context.getString(R.string.error_default_text));
    }
}
