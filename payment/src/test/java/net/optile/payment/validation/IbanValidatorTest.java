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

package net.optile.payment.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IbanValidatorTest {

    @Test
    public void isValidIban() {
        assertTrue(IbanValidator.isValidIban("DE27100777770209299700"));
        assertTrue(IbanValidator.isValidIban("AT022050302101023600"));

        assertFalse(IbanValidator.isValidIban(null));
        assertFalse(IbanValidator.isValidIban(""));
        assertFalse(IbanValidator.isValidIban("AT1234567890123456"));
    }
}
