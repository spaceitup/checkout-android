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

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import net.optile.payment.R;
import net.optile.payment.model.InputElement;

/**
 * Class for handling the Numeric input type
 */
public final class NumericInputWidget extends FormWidget {

    private final InputElement element;

    private final TextInputEditText input;

    private final TextInputLayout layout;

    /**
     * Construct a new NumericInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public NumericInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;
        layout = rootView.findViewById(R.id.layout_value);
        input = rootView.findViewById(R.id.input_value);

        layout.setHintAnimationEnabled(false);
        layout.setHint(element.getLabel());
        layout.setHintAnimationEnabled(true);
    }

    public boolean setLastImeOptionsWidget() {
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }
}
