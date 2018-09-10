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
 * The InputElementType test
 */
public class InputElementTypeTest {

    @Test
    public void isInputElementType_invalidValue_false() {
        assertFalse(InputElementType.isValid("foo"));
    }

    @Test
    public void isInputElementType_validValue_true() {
        assertTrue(InputElementType.isValid(InputElementType.STRING));
        assertTrue(InputElementType.isValid(InputElementType.NUMERIC));
        assertTrue(InputElementType.isValid(InputElementType.INTEGER));
        assertTrue(InputElementType.isValid(InputElementType.SELECT));
        assertTrue(InputElementType.isValid(InputElementType.CHECKBOX));
    }
}
