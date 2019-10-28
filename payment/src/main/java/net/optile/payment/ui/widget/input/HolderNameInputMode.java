/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import static android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
import static android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS;
import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;

/**
 * InputMode for holder names
 */
public final class HolderNameInputMode extends TextInputMode {

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
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(filters);
        editText.setInputType(TYPE_TEXT_VARIATION_PERSON_NAME | TYPE_TEXT_FLAG_CAP_WORDS);
    }
}
