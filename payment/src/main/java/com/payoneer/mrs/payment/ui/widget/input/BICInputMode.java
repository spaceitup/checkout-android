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
 * InputMode for BIC numbers
 */
public final class BICInputMode extends EditTextInputMode {

    /**
     * Construct an BICInputMode
     *
     * @param maxLength maximum length of the input field
     */
    public BICInputMode(int maxLength) {
        super(maxLength, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String normalize(String value) {
        return value != null ? value.replaceAll("\\s", "") : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        editText.setFilters(new InputFilter[] {
            new InputFilter.LengthFilter(maxLength),
            new InputFilter.AllCaps(),
            new AlphaNumericInputFilter(false)
        });
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }
}
