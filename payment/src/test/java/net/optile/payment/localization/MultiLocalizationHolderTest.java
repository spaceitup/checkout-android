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
public class MultiLocalizationHolderTest {

    @Test
    public void translate() {
        LocalizationHolder visa = LocalizationTest.createMapLocalizationHolder("visa", "visa", 5);
        LocalizationHolder mastercard = LocalizationTest.createMapLocalizationHolder("mastercard", "mastercard", 5);
        LocalizationHolder jcb = LocalizationTest.createMapLocalizationHolder("jcb", "jcb", 5);
        LocalizationHolder multi = new MultiLocalizationHolder(visa, mastercard, jcb);

        assertEquals("visa2", multi.translate("visa2"));
        assertEquals("mastercard3", multi.translate("mastercard3"));
        assertEquals("jcb1", multi.translate("jcb1"));
        assertEquals(null, multi.translate("foo"));
    }
}
