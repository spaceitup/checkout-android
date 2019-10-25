/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.mode;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;
import android.text.InputType;

/**
 * InputMode for VerificationCode numbers
 */
public final class VerificationCodeInputMode extends TextInputMode {

    /** 
     * Construct an VerificationCodeInputMode 
     *
     * @param maxLength 
     * @param groupSize
     */
    public VerificationCodeInputMode(int maxLength, int groupSize) {
        super(maxLength, 0);
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
    }
}
