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
 * The type Input element test.
 */
public class InputElementTest {

    /**
     * Gets checked type invalid value unknown type.
     */
    @Test
    public void getCheckedType_invalidValue_unknownType() {
        InputElement element = new InputElement();
        element.setType("foo");
        assertEquals(element.getCheckedType(), InputElement.TYPE_UNKNOWN);
    }

    /**
     * Gets checked type valid value same type.
     */
    @Test
    public void getCheckedType_validValue_sameType() {
        InputElement element = new InputElement();
        element.setType(InputElement.TYPE_STRING);
        assertEquals(element.getCheckedType(), InputElement.TYPE_STRING);
    }
}
