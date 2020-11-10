package net.optile.payment.ui.redirect;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.model.HttpMethod;
import net.optile.payment.model.Redirect;

@RunWith(RobolectricTestRunner.class)
public class RedirectServiceTest {

    @Test
    public void supports() throws MalformedURLException {
        Context context = ApplicationProvider.getApplicationContext();
        Redirect redirect = new Redirect();
        redirect.setMethod(HttpMethod.GET);

        URL link = new URL("http://example.com/");
        RedirectRequest request = new RedirectRequest(redirect, link);
        assertTrue(RedirectService.supports(context, request));

        redirect.setMethod(HttpMethod.POST);
        assertTrue(RedirectService.supports(context, request));
    }
}
