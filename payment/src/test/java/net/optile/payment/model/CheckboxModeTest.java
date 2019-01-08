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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The CheckboxMode test
 */
public class CheckboxModeTest {

    @Test
    public void isCheckboxMode_invalidValue_false() {
        assertFalse(CheckboxMode.isValid("foo"));
    }

    @Test
    public void isCheckboxMode_validValue_true() {
        assertTrue(CheckboxMode.isValid(CheckboxMode.OPTIONAL));
        assertTrue(CheckboxMode.isValid(CheckboxMode.OPTIONAL_PRESELECTED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.REQUIRED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.REQUIRED_PRESELECTED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.FORCED));
        assertTrue(CheckboxMode.isValid(CheckboxMode.FORCED_DISPLAYED));
    }
}
