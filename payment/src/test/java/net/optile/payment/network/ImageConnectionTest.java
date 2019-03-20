/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.graphics.Bitmap;
import net.optile.payment.core.PaymentException;

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
