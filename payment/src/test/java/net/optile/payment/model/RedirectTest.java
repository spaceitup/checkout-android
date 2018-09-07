/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Redirect test.
 */
public class RedirectTest {

    /**
     * Gets checked method invalid value method unknown.
     */
    @Test
    public void getCheckedMethod_invalidValue_methodUnknown() {
        Redirect redirect = new Redirect();
        redirect.setMethod("foo");
        assertEquals(redirect.getCheckedMethod(), Redirect.METHOD_UNKNOWN);
    }

    /**
     * Gets checked method valid value same method.
     */
    @Test
    public void getCheckedMethod_validValue_sameMethod() {
        Redirect redirect = new Redirect();
        redirect.setMethod(Redirect.METHOD_GET);
        assertEquals(redirect.getCheckedMethod(), Redirect.METHOD_GET);
    }
}
