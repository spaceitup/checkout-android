/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import java.util.Map;

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
