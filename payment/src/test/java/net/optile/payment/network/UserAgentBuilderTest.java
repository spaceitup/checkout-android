/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.core.PaymentException;

@RunWith(RobolectricTestRunner.class)
public class UserAgentBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void createFromContext_IllegalArgumentException() throws PaymentException {
        UserAgentBuilder.createFromContext(null);
    }

    @Test
    public void getValue_succes() throws PaymentException {
        Context context = ApplicationProvider.getApplicationContext();
        String value = UserAgentBuilder.createFromContext(context);
        assertTrue(value.startsWith("android-sdk/"));
    }
}
