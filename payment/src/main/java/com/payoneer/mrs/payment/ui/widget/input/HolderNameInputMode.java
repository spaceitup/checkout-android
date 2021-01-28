/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget.input;

import static android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS;
import static android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;

/**
 * InputMode for holder names
 */
public final class HolderNameInputMode extends EditTextInputMode {

    /**
     * Construct an HolderNameInputMode
     *
     * @param maxLength the maximum length of the holder name
     */
    public HolderNameInputMode(int maxLength) {
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
        editText.setInputType(TYPE_TEXT_VARIATION_PERSON_NAME | TYPE_TEXT_FLAG_CAP_WORDS);
    }
}
