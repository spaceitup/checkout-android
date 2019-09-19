/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.core;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import java.util.Properties;
import android.content.Context;
import android.content.res.Resources;
import androidx.test.core.app.ApplicationProvider;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.resource.ResourceLoader;

@RunWith(RobolectricTestRunner.class)
public class LocalizationTest {

    @Before
    public void before() {
        Localization loc = Localization.getInstance();
        loc.clear();
    }
    
    @Test
    public void getInstance() {
        Localization loc = Localization.getInstance();
        assertNotNull(loc);
    }

    @Test
    public void loadLocalTranslations() {
        Context context = ApplicationProvider.getApplicationContext();        
        Localization loc = Localization.getInstance();
        loc.loadLocalTranslations(context);
        assertNotNull(loc.translate(Localization.BUTTON_CANCEL));
        assertNotNull(loc.translate(Localization.BUTTON_RETRY));
        assertNotNull(loc.translate(Localization.BUTTON_UPDATE));
        assertNotNull(loc.translate(Localization.LIST_TITLE));
        assertNotNull(loc.translate(Localization.LIST_HEADER_NETWORKS));
        assertNotNull(loc.translate(Localization.CHARGE_TITLE));
        assertNotNull(loc.translate(Localization.CHARGE_TEXT));
        assertNotNull(loc.translate(Localization.CHARGE_INTERRUPTED));
        assertNotNull(loc.translate(Localization.ERROR_CONNECTION));
        assertNotNull(loc.translate(Localization.ERROR_DEFAULT));
    }

    @Test
    public void setSharedFile() {
        String testKey = "testKey";
        String testValue = "testValue";

        Localization loc = Localization.getInstance();
        Properties shared = new Properties();
        shared.put(testKey, testValue);
        loc.setSharedFile(shared);
        assertEquals(Localization.translate(testKey), testValue);
    }

    @Test
    public void hasSharedFile() {
        Localization loc = Localization.getInstance();
        loc.setSharedFile(new Properties());
        assertTrue(loc.hasSharedFile());
    }

    @Test
    public void putFile() {
        String fileName = "fileName";
        String testKey = "testKey";

        Localization loc = Localization.getInstance();
        Properties file = new Properties();
        file.put(testKey, "testValue");
        loc.putFile(fileName, file);
        assertEquals(Localization.translate(fileName, testKey, null), "testValue");
    }

    @Test
    public void hasFile() {
        Localization loc = Localization.getInstance();
        loc.putFile("testFile", new Properties());
        assertTrue(loc.hasFile("testFile"));
    }

    @Test
    public void translateTestMissingFileDefaultValue() {
        String defaultValue = "defaultValue";
        Localization loc = Localization.getInstance();
        assertEquals(Localization.translate("fileName", "testKey", defaultValue), defaultValue);
    }

    @Test
    public void translateTestMissingFileKeyDefaultValue() {
        String fileName = "fileName";
        String defaultValue = "defaultValue";

        Localization loc = Localization.getInstance();
        Properties file = new Properties();
        loc.putFile(fileName, file);
        assertEquals(Localization.translate(fileName, "testKey", defaultValue), defaultValue);
    }
    
    @Test
    public void translateTestFallbackToShared() {
        String fileName = "fileName";
        String testKey = "testKey";

        Localization loc = Localization.getInstance();
        Properties file = new Properties();
        loc.putFile(fileName, file);

        Properties shared = new Properties();
        shared.put("testKey", "testValue");
        loc.setSharedFile(shared);
        assertEquals(Localization.translate(fileName, "testKey", null), "testValue");
    }

    @Test
    public void translateTestFallbackToLocalTranslations() {
        String fileName = "fileName";
        String testKey = "testKey";
        
        Context context = ApplicationProvider.getApplicationContext();        
        String title = context.getString(R.string.pmlocal_list_title);

        Localization loc = Localization.getInstance();
        loc.loadLocalTranslations(context);

        Properties file = new Properties();
        file.put(testKey, "fileValue");
        loc.putFile(fileName, file);

        Properties shared = new Properties();
        shared.put(testKey, "sharedValue");        
        loc.setSharedFile(shared);
        assertEquals(Localization.translate(fileName, Localization.LIST_TITLE, null), title);
    }   
}
