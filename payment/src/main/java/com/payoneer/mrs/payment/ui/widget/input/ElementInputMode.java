/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget.input;

import com.google.android.material.textfield.TextInputEditText;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.model.InputElementType;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;

/**
 * InputMode for InputElements received in the ListResult
 */
public final class ElementInputMode extends EditTextInputMode {

    private final static String NUMERIC_DIGITS = "0123456789- ";
    private final InputElement element;

    /**
     * Construct an ElementInputMode
     *
     * @param maxLength maximum length of the input
     * @param element containing the type of the input
     */
    public ElementInputMode(int maxLength, InputElement element) {
        super(maxLength, 0);
        this.element = element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String normalize(String value) {
        if (value != null && element.getType().equals(InputElementType.NUMERIC)) {
            return value.replaceAll("[\\s|-]", "");
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        editText.setFilters(new InputFilter[] {
            new InputFilter.LengthFilter(maxLength)
        });

        switch (element.getType()) {
            case InputElementType.NUMERIC:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setKeyListener(DigitsKeyListener.getInstance(NUMERIC_DIGITS));
                break;
            case InputElementType.INTEGER:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }
}
