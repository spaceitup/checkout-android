/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.model.BrowserData;

@RunWith(RobolectricTestRunner.class)
public class BrowserDataBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void createFromContext_IllegalArgumentException() {
        BrowserDataBuilder.createFromContext(null);
    }

    @Test
    public void createFromContext_success() {
        Context context = ApplicationProvider.getApplicationContext();
        BrowserData browserData = BrowserDataBuilder.createFromContext(context);
        assertNotNull(browserData);
    }

    @Test
    public void build_success() {
        BrowserDataBuilder builder = new BrowserDataBuilder();
        Boolean javaEnabled = false;
        String language = "en-GB";
        String timeZone = "Berlin/Europe";
        Integer colorDepth = 24;
        Integer browserScreenWidth = 480;
        Integer browserScreenHeight = 720;

        BrowserData browserData = builder.setJavaEnabled(javaEnabled).
            setLanguage(language).
            setTimeZone(timeZone).
            setColorDepth(colorDepth).
            setBrowserScreenWidth(browserScreenWidth).
            setBrowserScreenHeight(browserScreenHeight).build();

        assertEquals(javaEnabled, browserData.getJavaEnabled());
        assertEquals(language, browserData.getLanguage());
        assertEquals(timeZone, browserData.getTimezone());
        assertEquals(colorDepth, browserData.getColorDepth());
        assertEquals(browserScreenWidth, browserData.getBrowserScreenWidth());
        assertEquals(browserScreenHeight, browserData.getBrowserScreenHeight());
    }
}
