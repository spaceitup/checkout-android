package net.optile.payment.ui.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import net.optile.payment.form.Operation;

@RunWith(RobolectricTestRunner.class)
public class PaymentSessionServiceTest {

    @Test
    public void isSupportedOperationType() {
        PaymentSessionService service = new PaymentSessionService();
        assertTrue(service.isSupportedOperationType(Operation.CHARGE));
        assertTrue(service.isSupportedOperationType(Operation.PRESET));
        assertFalse(service.isSupportedOperationType(Operation.UPDATE));
        assertFalse(service.isSupportedOperationType(null));
    }
}
