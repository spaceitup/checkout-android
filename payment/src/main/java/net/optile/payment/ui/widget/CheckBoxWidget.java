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
import net.optile.payment.ui.theme.CheckBoxParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

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
     * @param theme PaymentTheme to apply
     */
    public CheckBoxWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        labelUnchecked = rootView.findViewById(R.id.label_value_unchecked);
        labelChecked = rootView.findViewById(R.id.label_value_checked);

        CheckBoxParameters params = theme.getCheckBoxParameters();
        PaymentUtils.setTextAppearance(labelUnchecked, params.getUncheckedTextAppearance());
        PaymentUtils.setTextAppearance(labelChecked, params.getCheckedTextAppearance());

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

    public void putValue(Charge charge) throws PaymentException {
        charge.putValue(name, value.isChecked());
    }

    boolean isChecked() {
        return value.isChecked();
    }

    void initCheckBox(boolean clickable, boolean checked) {
        value.setClickable(clickable);
        value.setChecked(checked);
    }

    private void handleOnCheckedChanged(boolean isChecked) {
        labelUnchecked.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        labelChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}
