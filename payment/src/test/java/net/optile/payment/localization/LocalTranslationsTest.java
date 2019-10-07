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
public class LocalTranslationsTest {

    @Test
    public void loadLocalTranslations() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalTranslations trans = new LocalTranslations();
        trans.load(context);
        assertNotNull(trans.getTranslation(LocalizationKey.BUTTON_CANCEL, null));
        assertNotNull(trans.getTranslation(LocalizationKey.BUTTON_RETRY, null));
        assertNotNull(trans.getTranslation(LocalizationKey.BUTTON_UPDATE, null));
        assertNotNull(trans.getTranslation(LocalizationKey.LIST_TITLE, null));
        assertNotNull(trans.getTranslation(LocalizationKey.LIST_HEADER_NETWORKS, null));
        assertNotNull(trans.getTranslation(LocalizationKey.CHARGE_TITLE, null));
        assertNotNull(trans.getTranslation(LocalizationKey.CHARGE_TEXT, null));
        assertNotNull(trans.getTranslation(LocalizationKey.CHARGE_INTERRUPTED, null));
        assertNotNull(trans.getTranslation(LocalizationKey.ERROR_CONNECTION, null));
        assertNotNull(trans.getTranslation(LocalizationKey.ERROR_DEFAULT, null));
    }
}
