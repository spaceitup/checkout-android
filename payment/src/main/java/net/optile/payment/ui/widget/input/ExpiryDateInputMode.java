/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;

/**
 * InputMode for IBAN numbers
 */
public final class ExpiryDateInputMode extends TextInputMode {

    public final static int MAXLENGTH = 7;
    public final static String DIVIDER = " / ";
    public final static String DATE_DIGITS = "0123456789/ ";

    private ExpiryDateTextWatcher textWatcher;

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
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(filters);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setKeyListener(DigitsKeyListener.getInstance(DATE_DIGITS));
        textWatcher = new ExpiryDateTextWatcher(editText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        if (textWatcher != null) {
            textWatcher.reset();
        }
    }
}
