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

/**
 * InputMode for IBAN numbers
 */
public final class IBANInputMode extends TextInputMode {

    private GroupingTextWatcher textWatcher;
    
    /**
     * Construct an IBANInputMode
     *
     * @param maxLength maximum length of the input field
     * @param groupSize size of the groups
     */
    public IBANInputMode(int maxLength, int groupSize) {
        super(maxLength, groupSize);
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
        InputFilter[] filters = new InputFilter[3];
        filters[0] = new InputFilter.LengthFilter(getMaxLengthForGrouping());
        filters[1] = new InputFilter.AllCaps();
        filters[2] = new AlphaNumericInputFilter(true);
        editText.setFilters(filters);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        textWatcher = new GroupingTextWatcher(groupSize, editText);
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
