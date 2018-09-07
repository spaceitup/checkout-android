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
 * The type Applicable network test.
 */
public class ApplicableNetworkTest {

    /**
     * Gets checked method invalid value method unknown.
     */
    @Test
    public void getCheckedMethod_invalidValue_methodUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setMethod("foo");
        assertEquals(network.getCheckedMethod(), ApplicableNetwork.METHOD_UNKNOWN);
    }

    /**
     * Gets checked method valid value same method.
     */
    @Test
    public void getCheckedMethod_validValue_sameMethod() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setMethod(ApplicableNetwork.METHOD_BANK_TRANSFER);
        assertEquals(network.getCheckedMethod(), ApplicableNetwork.METHOD_BANK_TRANSFER);
    }

    /**
     * Gets checked registration invalid value registration unknown.
     */
    @Test
    public void getCheckedRegistration_invalidValue_registrationUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration("foo");
        assertEquals(network.getCheckedRegistration(), ApplicableNetwork.REGTYPE_UNKNOWN);
    }

    /**
     * Gets checked registration valid value same registration.
     */
    @Test
    public void getCheckedRegistration_validValue_sameRegistration() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration(ApplicableNetwork.REGTYPE_OPTIONAL);
        assertEquals(network.getCheckedRegistration(), ApplicableNetwork.REGTYPE_OPTIONAL);
    }

    /**
     * Gets checked recurrence invalid value recurrence unknown.
     */
    @Test
    public void getCheckedRecurrence_invalidValue_recurrenceUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration("foo");
        assertEquals(network.getCheckedRecurrence(), ApplicableNetwork.REGTYPE_UNKNOWN);
    }

    /**
     * Gets checked recurrence valid value same recurrence.
     */
    @Test
    public void getCheckedRecurrence_validValue_sameRecurrence() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRecurrence(ApplicableNetwork.REGTYPE_FORCED);
        assertEquals(network.getCheckedRecurrence(), ApplicableNetwork.REGTYPE_FORCED);
    }
}
