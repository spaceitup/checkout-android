/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.model.NetworkOperationType;

@RunWith(RobolectricTestRunner.class)
public class PaymentSessionServiceTest {

    @Test
    public void isSupportedOperationType() {
        PaymentSessionService service = new PaymentSessionService();
        assertTrue(service.isSupportedNetworkOperationType(NetworkOperationType.CHARGE));
        assertTrue(service.isSupportedNetworkOperationType(NetworkOperationType.PRESET));
        assertFalse(service.isSupportedNetworkOperationType(NetworkOperationType.UPDATE));
        assertFalse(service.isSupportedNetworkOperationType(NetworkOperationType.ACTIVATION));
        assertFalse(service.isSupportedNetworkOperationType(NetworkOperationType.PAYOUT));
        assertFalse(service.isSupportedNetworkOperationType(null));
    }
}
