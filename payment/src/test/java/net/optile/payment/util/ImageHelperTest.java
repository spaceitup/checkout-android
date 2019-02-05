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

package net.optile.payment.util;

import static org.junit.Assert.*;

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
