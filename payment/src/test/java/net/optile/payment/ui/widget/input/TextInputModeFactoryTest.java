/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;

public class TextInputModeFactoryTest {

    @Test
    public void createMode() {
        validateTextInputMode(PaymentInputType.ACCOUNT_NUMBER, AccountNumberInputMode.class);
        validateTextInputMode(PaymentInputType.HOLDER_NAME, HolderNameInputMode.class);
        validateTextInputMode(PaymentInputType.VERIFICATION_CODE, VerificationCodeInputMode.class);
        validateTextInputMode(PaymentInputType.IBAN, IBANInputMode.class);
        validateTextInputMode(PaymentInputType.BIC, BICInputMode.class);
        validateTextInputMode(PaymentInputType.BANK_CODE, ElementInputMode.class);
        validateTextInputMode("FOO", ElementInputMode.class);
    }

    private static void validateTextInputMode(String name, Class clazz) {
        InputElement element = new InputElement();
        element.setName(name);
        element.setType(InputElementType.STRING);
        
        TextInputMode mode = TextInputModeFactory.createMode(10, 4, element); 
        assertTrue(clazz.isInstance(mode));
    }
}
