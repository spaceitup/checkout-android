/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Class for testing the ImageHelper util class
 */
public class ImageHelperTest {

    @Test
    public void getInstance() {
        assertNotNull(ImageHelper.getInstance());
    }
}
