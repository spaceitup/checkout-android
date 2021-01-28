/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.localization.LocalizationHolder;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

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
        Context context = ApplicationProvider.getApplicationContext();
        LocalizationConnection conn = new LocalizationConnection(context);
        LocalizationHolder holder = conn.loadLocalization(null);
    }
}
