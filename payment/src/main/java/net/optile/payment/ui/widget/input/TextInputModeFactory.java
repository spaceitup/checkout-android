/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;

/**
 * Class for creating TextInputModes for the InputElement
 */
public final class TextInputModeFactory {

    /**
     * Create a TextInputMode for the given input element
     *
     * @param maxLength the maximum length of the input field
     * @param groupSize the size of character grouping if the mode supports grouping
     * @param element contains the name and type of the input element
     * @return the TextInputMode for the given InputElement
     */
    public static TextInputMode createMode(int maxLength, int groupSize, InputElement element) {
        switch (element.getName()) {
            case PaymentInputType.ACCOUNT_NUMBER:
                return new AccountNumberInputMode(maxLength, groupSize);
            case PaymentInputType.BIC:
                return new BICInputMode(maxLength);
            case PaymentInputType.HOLDER_NAME:
                return new HolderNameInputMode(maxLength);
            case PaymentInputType.VERIFICATION_CODE:
                return new VerificationCodeInputMode(maxLength);
            case PaymentInputType.IBAN:
                return new IBANInputMode(maxLength, groupSize);
            default:
                return new ElementInputMode(maxLength, element);
        }
    }

    /** 
     * Create a new ExpiryDateInputMode 
     * 
     * @return the newly created expiry date mode 
     */
    public static TextInputMode createExpiryDateInputMode() {
        return new ExpiryDateInputMode();
    }
}

