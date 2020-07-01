/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import com.google.android.material.switchmaterial.SwitchMaterial;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.PaymentTheme;

/**
 * Widget for showing the CheckBox input element
 */
public class CheckBoxWidget extends FormWidget {

    private final SwitchMaterial value;
    private final TextView labelUnchecked;
    private final TextView labelChecked;

    /**
     * Construct a new CheckBoxWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to apply
     */
    public CheckBoxWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        labelUnchecked = rootView.findViewById(R.id.label_value_unchecked);
        labelChecked = rootView.findViewById(R.id.label_value_checked);

        value = rootView.findViewById(R.id.checkbox_value);
        value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleOnCheckedChanged(isChecked);
            }
        });
    }

    public void setLabel(String label) {
        this.labelUnchecked.setText(label);
        this.labelChecked.setText(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        operation.putValue(name, value.isChecked());
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

    private void handleOnCheckedChanged(boolean isChecked) {
        labelUnchecked.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        labelChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}
