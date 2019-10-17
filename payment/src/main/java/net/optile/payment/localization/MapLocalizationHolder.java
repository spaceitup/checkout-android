/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import java.util.Map;

import android.text.TextUtils;

/**
 * Class storing localizations in a map
 */
public final class MapLocalizationHolder implements LocalizationHolder {

    private final Map<String, String> map;

    /**
     * Construct a new localization holder 
     *
     * @param map containing the localizations
     */
    public MapLocalizationHolder(Map<String, String> map) {
        this.map = map;
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
