/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget.input;

import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.InputElement;

/**
 * Class for creating TextInputModes for the InputElement
 */
public final class EditTextInputModeFactory {

    /**
     * Create a TextInputMode for the given input element
     *
     * @param maxLength the maximum length of the input field
     * @param element contains the name and type of the input element
     * @return the TextInputMode for the given InputElement
     */
    public static EditTextInputMode createMode(int maxLength, InputElement element) {
        switch (element.getName()) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return new AccountNumberInputMode(maxLength);
            case PaymentInputType.BIC:
                return new BICInputMode(maxLength);
            case PaymentInputType.HOLDER_NAME:
                return new HolderNameInputMode(maxLength);
            case PaymentInputType.VERIFICATION_CODE:
                return new VerificationCodeInputMode(maxLength);
            case PaymentInputType.IBAN:
                return new IBANInputMode(maxLength);
            default:
                return new ElementInputMode(maxLength, element);
        }
    }
}

