/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.localization;

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
    public String translate(String key) {
        for (LocalizationHolder holder : holders) {
            String value = holder.translate(key);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return null;
    }
}
