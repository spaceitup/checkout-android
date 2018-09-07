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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The type Interaction test.
 */
public class InteractionTest {

    /**
     * Gets checked code invalid value code unknown.
     */
    @Test
    public void getCheckedCode_invalidValue_codeUnknown() {
        Interaction interaction = new Interaction();
        interaction.setCode("foo");
        assertEquals(interaction.getCheckedCode(), Interaction.CODE_UNKNOWN);
    }

    /**
     * Gets checked code valid value same code.
     */
    @Test
    public void getCheckedCode_validValue_sameCode() {
        Interaction interaction = new Interaction();
        interaction.setCode(Interaction.CODE_PROCEED);
        assertEquals(interaction.getCheckedCode(), Interaction.CODE_PROCEED);
    }

    /**
     * Gets checked reason invalid value reason unknown.
     */
    @Test
    public void getCheckedReason_invalidValue_reasonUnknown() {
        Interaction interaction = new Interaction();
        interaction.setReason("foo");
        assertEquals(interaction.getCheckedReason(), Interaction.REASON_UNKNOWN);
    }

    /**
     * Gets checked reason valid value same reason.
     */
    @Test
    public void getCheckedReason_validValue_sameReason() {
        Interaction interaction = new Interaction();
        interaction.setReason(Interaction.REASON_BLOCKED);
        assertEquals(interaction.getCheckedReason(), Interaction.REASON_BLOCKED);
    }
}
