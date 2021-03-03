/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.localization;

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
    String translate(String key);
}
