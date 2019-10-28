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
 * InputMode for BIC numbers
 */
public final class BICInputMode extends TextInputMode {

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
        InputFilter[] filters = new InputFilter[3];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        filters[1] = new InputFilter.AllCaps();
        filters[2] = new AlphaNumericInputFilter(false);
        editText.setFilters(filters);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
    }
}
