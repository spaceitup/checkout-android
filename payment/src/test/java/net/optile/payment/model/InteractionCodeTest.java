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
import static net.optile.payment.model.InteractionCode.isInteractionCode;

/**
 * The InteractionCode test
 */
public class InteractionCodeTest {

    @Test
    public void isInteractionCode_invalidValue_false() {
        assertFalse(InteractionCode.isInteractionCode("foo"));
    }

    @Test
    public void isInteractionCode_validValue_true() {
        assertTrue(isInteractionCode(InteractionCode.PROCEED));
        assertTrue(isInteractionCode(InteractionCode.ABORT));
        assertTrue(isInteractionCode(InteractionCode.TRY_OTHER_NETWORK));
        assertTrue(isInteractionCode(InteractionCode.TRY_OTHER_ACCOUNT));
        assertTrue(isInteractionCode(InteractionCode.RETRY));
        assertTrue(isInteractionCode(InteractionCode.RELOAD));
    }
}
