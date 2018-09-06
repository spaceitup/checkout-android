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

public class ApplicableNetworkTest {

    @Test
    public void getCheckedMethod_invalidValue_methodUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setMethod("foo");
        assertEquals(network.getCheckedMethod(), ApplicableNetwork.METHOD_UNKNOWN);
    }

    @Test
    public void getCheckedMethod_validValue_sameMethod() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setMethod(ApplicableNetwork.METHOD_BANK_TRANSFER);
        assertEquals(network.getCheckedMethod(), ApplicableNetwork.METHOD_BANK_TRANSFER);
    }

    @Test
    public void getCheckedRegistration_invalidValue_registrationUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration("foo");
        assertEquals(network.getCheckedRegistration(), ApplicableNetwork.REGTYPE_UNKNOWN);
    }

    @Test
    public void getCheckedRegistration_validValue_sameRegistration() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration(ApplicableNetwork.REGTYPE_OPTIONAL);
        assertEquals(network.getCheckedRegistration(), ApplicableNetwork.REGTYPE_OPTIONAL);
    }

    @Test
    public void getCheckedRecurrence_invalidValue_recurrenceUnknown() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRegistration("foo");
        assertEquals(network.getCheckedRecurrence(), ApplicableNetwork.REGTYPE_UNKNOWN);
    }

    @Test
    public void getCheckedRecurrence_validValue_sameRecurrence() {
        ApplicableNetwork network = new ApplicableNetwork();
        network.setRecurrence(ApplicableNetwork.REGTYPE_FORCED);
        assertEquals(network.getCheckedRecurrence(), ApplicableNetwork.REGTYPE_FORCED);
    }
}
