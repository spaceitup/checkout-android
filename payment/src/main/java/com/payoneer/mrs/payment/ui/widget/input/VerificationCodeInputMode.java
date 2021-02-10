/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget.input;

import android.text.InputFilter;
import android.text.InputType;

import com.google.android.material.textfield.TextInputEditText;

/**
 * InputMode for VerificationCode numbers
 */
public final class VerificationCodeInputMode extends EditTextInputMode {

    /**
     * Construct an VerificationCodeInputMode
     *
     * @param maxLength maximum length of the input field
     */
    public VerificationCodeInputMode(int maxLength) {
        super(maxLength, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        editText.setFilters(new InputFilter[] {
            new InputFilter.LengthFilter(maxLength)
        });
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}
