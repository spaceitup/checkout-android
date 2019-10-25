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
import net.optile.payment.ui.widget.GroupingTextWatcher;

/**
 * InputMode for IBAN numbers
 */
public final class IBANInputMode extends TextInputMode {

    /** 
     * Construct an IBANInputMode 
     *
     * @param maxLength 
     * @param groupSize
     */
    public IBANInputMode(int maxLength, int groupSize) {
        super(maxLength, groupSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String normalize(String value) {
        return value != null ? value.replaceAll("[\\s]", "") : null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        int length = getMaxLengthForGrouping();
        InputFilter[] filters = new InputFilter[3];
        filters[0] = new InputFilter.LengthFilter(length);
        filters[1] = new InputFilter.AllCaps();
        filters[2] = new AlphaNumericInputFilter();
        editText.setFilters(filters);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        if (groupSize > 0) {
            editText.addTextChangedListener(new GroupingTextWatcher(groupSize, editText));
        }
    }
}
