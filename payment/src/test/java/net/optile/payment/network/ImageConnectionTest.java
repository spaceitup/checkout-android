/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.network;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import android.graphics.Bitmap;

/**
 * Containing ImageConnection tests
 */
@RunWith(RobolectricTestRunner.class)
public class ImageConnectionTest {

    /**
     * Create ImageConnection and loadBitmap with invalid url
     *
     * @throws PaymentException the network exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadBitmap_invalidUrl_IllegalArgumentException() throws PaymentException {
        ImageConnection conn = new ImageConnection();
        Bitmap bitmap = conn.loadBitmap(null);
    }
}
