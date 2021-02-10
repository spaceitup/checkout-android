/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.localization;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class LocalLocalizationHolderTest {

    @Test
    public void loadLocalLocalization() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalLocalizationHolder holder = new LocalLocalizationHolder(context);
        assertNotNull(holder.translate(LocalizationKey.BUTTON_OK));
        assertNotNull(holder.translate(LocalizationKey.BUTTON_CANCEL));
        assertNotNull(holder.translate(LocalizationKey.BUTTON_RETRY));

        assertNotNull(holder.translate(LocalizationKey.ERROR_CONNECTION_TEXT));
        assertNotNull(holder.translate(LocalizationKey.ERROR_DEFAULT_TEXT));
    }
}
