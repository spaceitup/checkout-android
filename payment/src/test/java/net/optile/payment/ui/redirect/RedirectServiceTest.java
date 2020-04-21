package net.optile.payment.ui.redirect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void isSupported() {
        Context context = ApplicationProvider.getApplicationContext();
        Redirect redirect = new Redirect();
        redirect.setMethod(HttpMethod.GET);
        assertTrue(RedirectService.isSupported(context, redirect));

        redirect.setMethod(HttpMethod.POST);
        assertFalse(RedirectService.isSupported(context, redirect));        
    }
}
