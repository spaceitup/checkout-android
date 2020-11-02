package net.optile.payment.localization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Copyright(c) 2012-2020 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
@RunWith(RobolectricTestRunner.class)
public class MapLocalizationHolderTest {

    @Test
    public void translate() {
        String defValue = "defValue";
        LocalizationHolder holder = LocalizationTest.createMapLocalizationHolder("key", "value", 5);
        assertEquals("value3", holder.translate("key3"));
        assertEquals(null, holder.translate("foo"));
    }
}
