package net.optile.payment.localization;

import static org.junit.Assert.*;

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
        LocalizationHolder visa = LocalizationTest.createPropLocalizationHolder("visa", "visa", 5);
        LocalizationHolder mastercard = LocalizationTest.createPropLocalizationHolder("mastercard", "mastercard", 5);
        LocalizationHolder jcb = LocalizationTest.createPropLocalizationHolder("jcb", "jcb", 5);
        LocalizationHolder multi = new MultiLocalizationHolder(visa, mastercard, jcb);

        assertEquals("visa2", multi.translate("visa2", null));
        assertEquals("mastercard3", multi.translate("mastercard3", null));
        assertEquals("jcb1", multi.translate("jcb1", null));
        assertEquals("defValue", multi.translate("foo", "defValue"));        
    }
}
