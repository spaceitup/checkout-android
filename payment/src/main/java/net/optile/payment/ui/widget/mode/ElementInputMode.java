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
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.widget.GroupingTextWatcher;

/**
 * InputMode for Element numbers
 */
public final class ElementInputMode extends TextInputMode {

    private final InputElement element;
    
    /** 
     * Construct an ElementInputMode 
     *
     * @param maxLength 
     * @param groupSize
     */
    public ElementInputMode(int maxLength, int groupSize, InputElement element) {
        super(maxLength, 0);
        this.element = element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(filters);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
    }
}
