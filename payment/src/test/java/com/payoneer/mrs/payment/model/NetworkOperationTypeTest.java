/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NetworkOperationTypeTest {

    @Test
    public void isNetworkOperationType_invalidValue_false() {
        assertFalse(NetworkOperationType.isValid("foo"));
    }

    @Test
    public void isNetworkOperationType_validValue_true() {
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.CHARGE));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.PRESET));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.PAYOUT));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.UPDATE));
        assertTrue(NetworkOperationType.isValid(NetworkOperationType.ACTIVATION));
    }
}
