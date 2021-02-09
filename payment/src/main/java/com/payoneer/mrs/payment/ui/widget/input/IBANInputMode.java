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
 * InputMode for IBAN numbers
 */
public final class IBANInputMode extends EditTextInputMode {

    /**
     * Construct an IBANInputMode
     *
     * @param maxLength maximum length of the input field
     */
    public IBANInputMode(int maxLength) {
        super(maxLength, 4);
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
            new InputFilter.LengthFilter(getMaxLengthForGrouping()),
            new InputFilter.AllCaps(),
            new AlphaNumericInputFilter(true)
        });
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        textWatcher = new GroupingTextWatcher(groupSize, editText);
    }
}
