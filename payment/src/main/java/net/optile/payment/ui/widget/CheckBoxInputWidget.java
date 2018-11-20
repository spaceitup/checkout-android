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
import android.widget.CompoundButton;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;

/**
 * Class for handling the CheckBox input type
 */
public class CheckBoxInputWidget extends FormWidget {

    private final CheckBox value;
    private final TextView labelDisabled;
    private final TextView labelEnabled;

    /**
     * Construct a new CheckBoxInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public CheckBoxInputWidget(String name, View rootView) {
        super(name, rootView);
        labelDisabled = rootView.findViewById(R.id.label_value_disabled);
        labelEnabled = rootView.findViewById(R.id.label_value_enabled);

        value = rootView.findViewById(R.id.checkbox_value);
        value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleOnCheckedChanged(isChecked);
            }
        });
    }

    public void setLabel(String label) {
        this.labelDisabled.setText(label);
        this.labelEnabled.setText(label);
    }

    public void putValue(Charge charge) throws PaymentException {
        charge.putValue(name, value.isChecked());
    }

    public boolean isChecked() {
        return value.isChecked();
    }

    void initCheckBox(boolean clickable, boolean checked) {
        value.setClickable(clickable);
        value.setChecked(checked);
    }

    void handleOnCheckedChanged(boolean isChecked) {
        labelDisabled.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        labelEnabled.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}
