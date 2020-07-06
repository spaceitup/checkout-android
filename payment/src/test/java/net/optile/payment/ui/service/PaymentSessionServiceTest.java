package net.optile.payment.ui.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.model.OperationType;

@RunWith(RobolectricTestRunner.class)
public class PaymentSessionServiceTest {

    @Test
    public void isSupportedOperationType() {
        PaymentSessionService service = new PaymentSessionService();
        assertTrue(service.isSupportedOperationType(OperationType.CHARGE));
        assertTrue(service.isSupportedOperationType(OperationType.PRESET));
        assertFalse(service.isSupportedOperationType(OperationType.UPDATE));
        assertFalse(service.isSupportedOperationType(OperationType.ACTIVATION));
        assertFalse(service.isSupportedOperationType(OperationType.PAYOUT));
        assertFalse(service.isSupportedOperationType(null));
    }
}
