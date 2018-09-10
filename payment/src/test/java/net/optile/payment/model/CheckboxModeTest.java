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
import static net.optile.payment.model.CheckboxMode.isCheckboxMode;

/**
 * The CheckboxMode test
 */
public class CheckboxModeTest {

    @Test
    public void isCheckboxMode_invalidValue_false() {
        assertFalse(CheckboxMode.isCheckboxMode("foo"));
    }

    @Test
    public void isCheckboxMode_validValue_true() {
        assertTrue(isCheckboxMode(CheckboxMode.OPTIONAL));
        assertTrue(isCheckboxMode(CheckboxMode.OPTIONAL_PRESELECTED));
        assertTrue(isCheckboxMode(CheckboxMode.REQUIRED));
        assertTrue(isCheckboxMode(CheckboxMode.REQUIRED_PRESELECTED));
        assertTrue(isCheckboxMode(CheckboxMode.FORCED));
        assertTrue(isCheckboxMode(CheckboxMode.FORCED_DISPLAYED));
    }
}
