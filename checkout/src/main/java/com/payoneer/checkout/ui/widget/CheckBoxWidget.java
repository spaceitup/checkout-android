/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.payoneer.checkout.R;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.form.Operation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Widget for showing the CheckBox input element
 */
public class CheckBoxWidget extends FormWidget {

    private SwitchMaterial value;
    private TextView label;

    /**
     * Construct a new CheckBoxWidget
     *
     * @param name name identifying this widget
     */
    public CheckBoxWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View inflate(ViewGroup parent) {
        inflateWidgetView(parent, R.layout.widget_checkbox);
        label = widgetView.findViewById(R.id.label_value);
        value = widgetView.findViewById(R.id.checkbox_value);
        return widgetView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        operation.putBooleanValue(name, value.isChecked());
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    boolean isChecked() {
        return value.isChecked();
    }

    void setCheckboxVisible(boolean visible) {
        value.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    void setChecked(boolean checked) {
        value.setChecked(checked);
    }
}
