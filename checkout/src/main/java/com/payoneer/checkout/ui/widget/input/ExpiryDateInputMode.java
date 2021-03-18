/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget.input;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;

/**
 * InputMode for IBAN numbers
 */
public final class ExpiryDateInputMode extends EditTextInputMode {

    public final static int MAXLENGTH = 7;
    public final static String DIVIDER = " / ";
    public final static String DATE_DIGITS = "0123456789/ ";

    /**
     * Construct an IBANInputMode
     */
    public ExpiryDateInputMode() {
        super(MAXLENGTH, 0);
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
        editText.setKeyListener(DigitsKeyListener.getInstance(DATE_DIGITS));
        textWatcher = new ExpiryDateTextWatcher(editText);
    }
}
