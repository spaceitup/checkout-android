/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.redirect;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.payoneer.mrs.payment.model.HttpMethod;
import com.payoneer.mrs.payment.model.Redirect;
import com.payoneer.mrs.test.util.TestUtils;

@RunWith(RobolectricTestRunner.class)
public class RedirectServiceTest {

    @Test
    public void supports() {
        Context context = ApplicationProvider.getApplicationContext();
        Redirect redirect = new Redirect();
        redirect.setMethod(HttpMethod.GET);

        URL link = TestUtils.createDefaultURL();
        RedirectRequest request = new RedirectRequest(redirect, link);
        assertTrue(RedirectService.supports(context, request));

        redirect.setMethod(HttpMethod.POST);
        assertTrue(RedirectService.supports(context, request));
    }
}
