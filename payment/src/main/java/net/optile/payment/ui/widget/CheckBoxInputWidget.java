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

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;

/**
 * Class for handling the CheckBox input type
 */
public final class CheckBoxInputWidget extends FormWidget {

    private final InputElement element;

    private final CheckBox value;

    private final TextView label;

    /**
     * Construct a new CheckBoxInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public CheckBoxInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;
        label = rootView.findViewById(R.id.label_value);
        value = rootView.findViewById(R.id.checkbox_value);
    }

    public void putValue(Charge charge) throws PaymentException {
        charge.putValue(element.getName(), value.isChecked());
    }
}
