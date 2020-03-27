/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.localization;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class LocalLocalizationHolderTest {

    @Test
    public void loadLocalLocalization() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalLocalizationHolder holder = new LocalLocalizationHolder(context);
        assertNotNull(holder.translate(LocalizationKey.BUTTON_OK, null));
        assertNotNull(holder.translate(LocalizationKey.BUTTON_CANCEL, null));
        assertNotNull(holder.translate(LocalizationKey.BUTTON_RETRY, null));
        assertNotNull(holder.translate(LocalizationKey.BUTTON_UPDATE, null));
        assertNotNull(holder.translate(LocalizationKey.LIST_TITLE, null));
        assertNotNull(holder.translate(LocalizationKey.LIST_HEADER_NETWORKS, null));
        assertNotNull(holder.translate(LocalizationKey.CHARGE_TITLE, null));
        assertNotNull(holder.translate(LocalizationKey.CHARGE_TEXT, null));
        assertNotNull(holder.translate(LocalizationKey.CHARGE_INTERRUPTED, null));
        assertNotNull(holder.translate(LocalizationKey.ERROR_CONNECTION, null));
        assertNotNull(holder.translate(LocalizationKey.ERROR_DEFAULT, null));
    }
}
