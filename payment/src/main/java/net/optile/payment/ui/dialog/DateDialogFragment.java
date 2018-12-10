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

import java.util.Objects;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * Date Dialog Fragment for allowing the user to select month and year
 */
public final class DateDialogFragment extends DialogFragment {

    private String title;
    private String buttonLabel;

    private NumberPicker yearPicker;
    private int yearIndex;
    private String[] yearLabels;

    private NumberPicker monthPicker;
    private int monthIndex;
    private String[] monthLabels;

    private DateDialogListener listener;
    private PaymentTheme theme;

    /**
     * Set the title in this date dialog
     *
     * @param title shown in the top of this date dialog
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the button label and action
     *
     * @param buttonLabel the Label of the button
     */
    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    /**
     * Set the listener to this DateDialogFragment
     *
     * @param listener to inform of an action button click
     */
    public void setListener(DateDialogListener listener) {
        this.listener = listener;
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
        initButton(v, params);
        return v;
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

    private void initButton(View rootView, DialogParameters params) {
        View layout = rootView.findViewById(R.id.layout_button);
        layout.setVisibility(View.VISIBLE);
        TextView tv = rootView.findViewById(R.id.text_button);
        tv.setText(buttonLabel);
        PaymentUtils.setTextAppearance(tv, params.getButtonLabelStyle());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void handleButtonClick() {

        if (this.listener != null) {
            this.monthIndex = monthPicker.getValue();
            this.yearIndex = yearPicker.getValue();
            listener.onDateChanged(monthIndex, monthLabels[monthIndex],
                yearIndex, yearLabels[yearIndex]);
        }
        dismiss();
    }

    public interface DateDialogListener {
        void onDateChanged(int monthIndex, String monthLabel, int yearIndex, String yearLabel);
    }
}
