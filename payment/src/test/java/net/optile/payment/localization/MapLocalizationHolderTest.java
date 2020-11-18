/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.localization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
