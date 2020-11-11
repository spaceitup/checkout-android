/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The OperationType test
 */
public class OperationTypeTest {

    @Test
    public void isOperationType_invalidValue_false() {
        assertFalse(OperationType.isValid("foo"));
    }

    @Test
    public void isOperationType_validValue_true() {
        assertTrue(OperationType.isValid(OperationType.CHARGE));
        assertTrue(OperationType.isValid(OperationType.PRESET));
        assertTrue(OperationType.isValid(OperationType.PAYOUT));
        assertTrue(OperationType.isValid(OperationType.UPDATE));
        assertTrue(OperationType.isValid(OperationType.ACTIVATION));
    }
}
