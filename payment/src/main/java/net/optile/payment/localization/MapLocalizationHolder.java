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
 * Class storing localizations in a map
 */
public class MapLocalizationHolder implements LocalizationHolder {

    final Map<String, String> map;

    /**
     * Construct a  map localization holder
     *
     * @param map the map containing the localizations
     */
    public MapLocalizationHolder(Map<String, String> map) {
        this.map = map;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String translate(String key) {
        return map.get(key);
    }
}
