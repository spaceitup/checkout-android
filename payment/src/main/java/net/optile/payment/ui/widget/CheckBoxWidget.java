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

import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.ui.theme.CheckBoxParameters;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * Widget for showing the CheckBox input element
 */
public class CheckBoxWidget extends FormWidget {

    private final CheckBox value;
    private final TextView labelUnchecked;
    private final TextView labelChecked;

    /**
     * Construct a new CheckBoxWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public CheckBoxWidget(String name, View rootView) {
        super(name, rootView);
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

    public void applyTheme(PaymentTheme theme) {
        super.applyTheme(theme);
        CheckBoxParameters params = theme.getCheckBoxParameters();
        TextViewCompat.setTextAppearance(labelUnchecked, params.getUncheckedTextAppearance());
        TextViewCompat.setTextAppearance(labelChecked, params.getCheckedTextAppearance());
    }

    public void setLabel(String label) {
        this.labelUnchecked.setText(label);
        this.labelChecked.setText(label);
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
        labelUnchecked.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        labelChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}
