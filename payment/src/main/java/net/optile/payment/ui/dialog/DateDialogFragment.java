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
import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.util.PaymentUtils;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogParameters params = PaymentUI.getInstance().getPaymentTheme().getDialogParameters();
        View v = inflater.inflate(R.layout.dialogfragment_date, container, false);
        initTitle(v, params);
        initNumberPickers(v);
        initButtons(v, params);
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

    private void initTitle(View rootView, DialogParameters params) {
        TextView tv = rootView.findViewById(R.id.text_title);

        if (TextUtils.isEmpty(title)) {
            tv.setVisibility(View.GONE);
            return;
        }
        PaymentUtils.setTextAppearance(tv, params.getDateTitleStyle());
        tv.setVisibility(View.VISIBLE);
        tv.setText(title);
    }
}
