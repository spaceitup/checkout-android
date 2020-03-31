/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

/**
 * Interface for localization holders containing translations.
 */
public interface LocalizationHolder {

    /**
     * Translate the given key if available, return null if not found
     *
     * @param key used to identify the translation
     * @return the translated value or null if the key does not exist
     */
    public String translate(String key);
}
