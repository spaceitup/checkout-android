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
 * The RegistrationType test
 */
public class RegistrationTypeTest {

    @Test
    public void isRegistrationType_invalidValue_false() {
        assertFalse(RegistrationType.isValid("foo"));
    }

    @Test
    public void isRegistrationType_validValue_true() {
        assertTrue(RegistrationType.isValid(RegistrationType.NONE));
        assertTrue(RegistrationType.isValid(RegistrationType.OPTIONAL));
        assertTrue(RegistrationType.isValid(RegistrationType.FORCED));
        assertTrue(RegistrationType.isValid(RegistrationType.OPTIONAL_PRESELECTED));
        assertTrue(RegistrationType.isValid(RegistrationType.FORCED_DISPLAYED));
    }
}
