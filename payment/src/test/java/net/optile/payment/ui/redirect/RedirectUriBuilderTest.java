package net.optile.payment.ui.redirect;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.net.Uri;
import net.optile.payment.model.Parameter;
import net.optile.payment.model.Redirect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class RedirectUriBuilderTest {

    @Test
    public void createUriFromRedirect_noParams() throws MalformedURLException {
        List<Parameter> params = new ArrayList<>();
        params.add(createParameter("param0", "value0"));
        params.add(createParameter("param1", "value1"));
        
        Redirect redirect = new Redirect();
        redirect.setUrl(new URL("http://example.com"));
        redirect.setParameters(params);
        Uri uri = RedirectUriBuilder.createUri(redirect);
        assertEquals("http://example.com?param0=value0&param1=value1", uri.toString());
    }

    @Test
    public void createUriFromRedirect_withParams() throws MalformedURLException {
        List<Parameter> params = new ArrayList<>();
        params.add(createParameter("param0", "value0"));
        params.add(createParameter("param1", "value1"));
        
        Redirect redirect = new Redirect();
        redirect.setUrl(new URL("http://example.com?foo=bar"));
        redirect.setParameters(params);
        Uri uri = RedirectUriBuilder.createUri(redirect);
        assertEquals("http://example.com?foo=bar&param0=value0&param1=value1", uri.toString());
    }

    
    private Parameter createParameter(String name, String value) {
        Parameter parameter = new Parameter();
        parameter.setName(name);
        parameter.setValue(value);
        return parameter;
    }
}
