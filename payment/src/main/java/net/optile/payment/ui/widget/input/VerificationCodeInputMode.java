/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;
import android.text.InputType;

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
