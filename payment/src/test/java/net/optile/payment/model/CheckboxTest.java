/**
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

import static org.junit.Assert.*;

public class CheckboxTest {

    @Test
    public void getCheckedMode_invalidValue_modeUnknown() {
        Checkbox checkbox = new Checkbox();
        checkbox.setMode("foo");
        assertEquals(checkbox.getCheckedMode(), Checkbox.MODE_UNKNOWN);
    }

    @Test
    public void getCheckedMode_validValue_sameMode() {
        Checkbox checkbox = new Checkbox();
        checkbox.setMode(Checkbox.MODE_REQUIRED);
        assertEquals(checkbox.getCheckedMode(), Checkbox.MODE_REQUIRED);
    }
}
