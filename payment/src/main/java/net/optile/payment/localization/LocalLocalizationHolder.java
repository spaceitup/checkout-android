/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

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

import java.util.Map;
import android.content.Context;
import android.text.TextUtils;

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
        this.map = new HashMap<>();
        map.put(BUTTON_CANCEL, context.getString(R.string.pmlocal_button_cancel));
        map.put(BUTTON_RETRY, context.getString(R.string.pmlocal_button_retry));
        map.put(BUTTON_UPDATE, context.getString(R.string.pmlocal_button_update));
        map.put(LIST_TITLE, context.getString(R.string.pmlocal_list_title));
        map.put(LIST_HEADER_NETWORKS, context.getString(R.string.pmlocal_list_header_networks));
        map.put(CHARGE_TITLE, context.getString(R.string.pmlocal_charge_title));
        map.put(CHARGE_TEXT, context.getString(R.string.pmlocal_charge_text));
        map.put(CHARGE_INTERRUPTED, context.getString(R.string.pmlocal_charge_interrupted));
        map.put(ERROR_CONNECTION, context.getString(R.string.pmlocal_error_connection));
        map.put(ERROR_DEFAULT, context.getString(R.string.pmlocal_error_default));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String translate(String key, String defValue) {
        String value = map.get(key);
        return TextUtils.isEmpty(value) ? defValue : value;
    }
}
