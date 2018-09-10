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
import static net.optile.payment.model.InteractionReason.isInteractionReason;

/**
 * The InteractionReason test
 */
public class InteractionReasonTest {

    @Test
    public void isInteractionReason_invalidValue_false() {
        assertFalse(InteractionReason.isInteractionReason("foo"));
    }

    @Test
    public void isInteractionReason_validValue_true() {
        assertTrue(isInteractionReason(InteractionReason.OK));
        assertTrue(isInteractionReason(InteractionReason.PENDING));
        assertTrue(isInteractionReason(InteractionReason.STRONG_AUTHENTICATION));
        assertTrue(isInteractionReason(InteractionReason.DECLINED));
        assertTrue(isInteractionReason(InteractionReason.EXCEEDS_LIMIT));
        assertTrue(isInteractionReason(InteractionReason.TEMPORARY_FAILURE));
        assertTrue(isInteractionReason(InteractionReason.NETWORK_FAILURE));
        assertTrue(isInteractionReason(InteractionReason.BLACKLISTED));
        assertTrue(isInteractionReason(InteractionReason.BLOCKED));
        assertTrue(isInteractionReason(InteractionReason.SYSTEM_FAILURE));
        assertTrue(isInteractionReason(InteractionReason.INVALID_ACCOUNT));
        assertTrue(isInteractionReason(InteractionReason.FRAUD));
        assertTrue(isInteractionReason(InteractionReason.ADDITIONAL_NETWORKS));
        assertTrue(isInteractionReason(InteractionReason.INVALID_REQUEST));
        assertTrue(isInteractionReason(InteractionReason.SCHEDULED));
        assertTrue(isInteractionReason(InteractionReason.NO_NETWORKS));
        assertTrue(isInteractionReason(InteractionReason.DUPLICATE_OPERATION));
        assertTrue(isInteractionReason(InteractionReason.CHARGEBACK));
        assertTrue(isInteractionReason(InteractionReason.RISK_DETECTED));
        assertTrue(isInteractionReason(InteractionReason.CUSTOMER_ABORT));
        assertTrue(isInteractionReason(InteractionReason.EXPIRED_SESSION));
        assertTrue(isInteractionReason(InteractionReason.EXPIRED_ACCOUNT));
        assertTrue(isInteractionReason(InteractionReason.ACCOUNT_NOT_ACTIVATED));  
        assertTrue(isInteractionReason(InteractionReason.TRUSTED_CUSTOMER));
        assertTrue(isInteractionReason(InteractionReason.UNKNOWN_CUSTOMER));
        assertTrue(isInteractionReason(InteractionReason.ACTIVATED));
        assertTrue(isInteractionReason(InteractionReason.UPDATED));
        assertTrue(isInteractionReason(InteractionReason.TAKE_ACTION));
    }
}
