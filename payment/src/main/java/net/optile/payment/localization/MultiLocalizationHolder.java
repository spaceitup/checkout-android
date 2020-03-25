/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import android.text.TextUtils;

/**
 * LocalizationHolder containing multiple child localization holders
 */
public final class MultiLocalizationHolder implements LocalizationHolder {

    private final LocalizationHolder[] holders;

    /**
     * Construct a new multi localization holder 
     *
     * @param holders array containing the chained localization holders
     */
    public MultiLocalizationHolder(LocalizationHolder... holders) {
        this.holders = holders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String translate(String key, String defValue) {
        String value;
        for (LocalizationHolder holder : holders) {
            value = holder.translate(key, null);

            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return defValue;
    }
}
