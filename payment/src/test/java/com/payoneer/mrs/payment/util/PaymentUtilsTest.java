/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.core.PaymentInputType;
import com.payoneer.mrs.payment.model.InputElement;

import android.content.res.Resources;
import androidx.test.core.app.ApplicationProvider;

/**
 * Class for testing the PaymentUtils class
 */
@RunWith(RobolectricTestRunner.class)
public class PaymentUtilsTest {

    @Test
    public void isTrue() {
        assertTrue(PaymentUtils.isTrue(Boolean.TRUE));
        assertFalse(PaymentUtils.isTrue(Boolean.FALSE));
        assertFalse(PaymentUtils.isTrue(null));
    }

    @Test
    public void trimToEmpty() {
        String empty = "";
        assertEquals(PaymentUtils.trimToEmpty(null), empty);
        assertEquals(PaymentUtils.trimToEmpty("   "), empty);
    }

    @Test
    public void toInt() {
        assertEquals(PaymentUtils.toInt(null), 0);
        assertEquals(PaymentUtils.toInt(new Integer(100)), 100);
    }

    @Test
    public void containsExpiryDate() {
        List<InputElement> elements = new ArrayList<>();
        assertFalse(PaymentUtils.containsExpiryDate(elements));

        InputElement month = new InputElement();
        month.setName(PaymentInputType.EXPIRY_MONTH);

        InputElement year = new InputElement();
        year.setName(PaymentInputType.EXPIRY_YEAR);
        elements.add(month);
        elements.add(year);
        assertTrue(PaymentUtils.containsExpiryDate(elements));

    }

    @Test(expected = IOException.class)
    public void readRawResource_missing_resource() throws IOException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        PaymentUtils.readRawResource(res, -1);
    }

    @Test
    public void readRawResource_contains_resource() throws IOException {
        Resources res = ApplicationProvider.getApplicationContext().getResources();
        assertNotNull(PaymentUtils.readRawResource(res, R.raw.groups));
    }
}
