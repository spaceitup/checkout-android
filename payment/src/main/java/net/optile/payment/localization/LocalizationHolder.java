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
     * Translate the given key and if the key was not found return the default value 
     * 
     * @param key used to identify the translation
     * @param defValue the default value to be returned if this holder does not contain the translation
     */
    public String translate(String key, String defValue);
}
