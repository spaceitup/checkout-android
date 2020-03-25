/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import java.util.Properties;

import android.text.TextUtils;

/**
 * Class storing localizations in a Properties object
 */
public final class PropLocalizationHolder implements LocalizationHolder {

    private final Properties properties;

    /**
     * Construct a new localization holder
     *
     * @param properties containing the localizations
     */
    public PropLocalizationHolder(Properties properties) {
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String translate(String key, String defValue) {
        String value = properties.getProperty(key);
        return TextUtils.isEmpty(value) ? defValue : value;
    }
}
