/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;

/**
 * Date Dialog Fragment for allowing the user to select month and year
 */
public final class DateDialogFragment extends ThemedDialogFragment {

    private String title;
    private NumberPicker yearPicker;
    private int yearIndex;
    private String[] yearLabels;

    private NumberPicker monthPicker;
    private int monthIndex;
    private String[] monthLabels;

    /**
     * Set the title in this date dialog
     *
     * @param title shown in the top of this date dialog
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setValues(int monthIndex, String[] monthLabels, int yearIndex, String[] yearLabels) {
        this.monthIndex = monthIndex;
        this.monthLabels = monthLabels;
        this.yearIndex = yearIndex;
        this.yearLabels = yearLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTheme() {
        int theme = PaymentUI.getInstance().getPaymentTheme().getDateDialogTheme();
        return theme == 0 ? super.getTheme() : theme;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_date, container, false);
        initTitle(v);
        initNumberPickers(v);
        initButtons(v);
        return v;
    }

    public int getMonthIndex() {
        return monthPicker.getValue();
    }

    public int getYearIndex() {
        return yearPicker.getValue();
    }

    private void initNumberPickers(View rootView) {
        monthPicker = rootView.findViewById(R.id.numberpicker_month);
        monthPicker.setDisplayedValues(monthLabels);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(monthLabels.length - 1);
        monthPicker.setValue(monthIndex);

        yearPicker = rootView.findViewById(R.id.numberpicker_year);
        yearPicker.setDisplayedValues(yearLabels);
        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(yearLabels.length - 1);
        yearPicker.setValue(yearIndex);
    }

    private void initTitle(View rootView) {
        TextView tv = rootView.findViewById(R.id.text_title);

        if (TextUtils.isEmpty(title)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(title);
    }
}
