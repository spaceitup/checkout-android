/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.PaymentException;
import net.optile.payment.localization.LocalizationHolder;

/**
 * The type Localization connection test.
 */
@RunWith(RobolectricTestRunner.class)
public class LocalizationConnectionTest {

    /**
     * Gets LocalizationHolder with invalid URL
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadLocalizationHolder_invalidURL_IllegalArgumentException() throws PaymentException {
        LocalizationConnection conn = new LocalizationConnection();
        LocalizationHolder holder = conn.loadLocalization(null);
    }
}
