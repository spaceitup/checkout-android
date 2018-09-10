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

package net.optile.payment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The CheckboxMode test
 */
public class HttpMethodTest {

    @Test
    public void isHttpMethod_invalidValue_false() {
        assertFalse(HttpMethod.isValid("foo"));
    }

    @Test
    public void isHttpMethod_validValue_true() {
        assertTrue(HttpMethod.isValid(HttpMethod.GET));
        assertTrue(HttpMethod.isValid(HttpMethod.POST));
    }
}
