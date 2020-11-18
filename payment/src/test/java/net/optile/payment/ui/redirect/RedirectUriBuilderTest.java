/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.net.Uri;
import net.optile.payment.model.Parameter;
import net.optile.payment.model.Redirect;
import net.optile.test.util.TestUtils;

@RunWith(RobolectricTestRunner.class)
public class RedirectUriBuilderTest {

    @Test
    public void createUriFromRedirect_noParams() {
        List<Parameter> params = new ArrayList<>();
        params.add(createParameter("param0", "value0"));
        params.add(createParameter("param1", "value1"));

        Redirect redirect = new Redirect();
        redirect.setUrl(TestUtils.createTestURL("http://localhost"));
        redirect.setParameters(params);
        Uri uri = RedirectUriBuilder.fromRedirect(redirect);
        assertEquals("http://localhost?param0=value0&param1=value1", uri.toString());
    }

    @Test
    public void createUriFromRedirect_withParams() {
        List<Parameter> params = new ArrayList<>();
        params.add(createParameter("param0", "value0"));
        params.add(createParameter("param1", "value1"));

        Redirect redirect = new Redirect();
        URL url = TestUtils.createTestURL("http://localhost?foo=bar");
        redirect.setUrl(url);
        redirect.setParameters(params);
        Uri uri = RedirectUriBuilder.fromRedirect(redirect);
        assertEquals("http://localhost?foo=bar&param0=value0&param1=value1", uri.toString());
    }

    @Test
    public void createUriFromURL() {
        URL url = TestUtils.createDefaultURL();
        Uri uri = RedirectUriBuilder.fromURL(url);
        assertEquals(url.toString(), uri.toString());
    }

    private Parameter createParameter(String name, String value) {
        Parameter parameter = new Parameter();
        parameter.setName(name);
        parameter.setValue(value);
        return parameter;
    }
}
