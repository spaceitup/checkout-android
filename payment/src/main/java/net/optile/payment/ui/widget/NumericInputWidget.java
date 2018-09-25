/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.widget;

import net.optile.payment.R;
import android.view.View;
import android.widget.EditText;
import net.optile.payment.model.InputElement;
import android.support.design.widget.TextInputLayout;
    
/**
 * Class for handling the Numeric input type
 */
public final class NumericInputWidget extends FormWidget {

    private final InputElement element;
    
    private final EditText inputValue;

    private final TextInputLayout inputLayout;
    
    /** 
     * Construct a new NumericInputWidget
     * 
     * @param name     name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public NumericInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;
        inputLayout = rootView.findViewById(R.id.layout_input);
        inputValue = rootView.findViewById(R.id.input_string);

        inputLayout.setHintAnimationEnabled(false);
        inputLayout.setHint(element.getLabel());
        inputLayout.setHintAnimationEnabled(true);
    }
}
