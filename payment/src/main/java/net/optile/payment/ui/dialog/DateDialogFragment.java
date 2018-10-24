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

import java.util.List;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.NumberPicker;
import net.optile.payment.R;

/**
 * Date Dialog Fragment for allowing the user to select month and year
 */
public final class DateDialogFragment extends DialogFragment {

    private String buttonLabel;

    private String buttonAction;

    private int selYearIndex;

    private String[] yearLabels;

    private NumberPicker yearPicker;
    
    private int selMonthIndex;

    private String[] monthLabels;
    
    private DateDialogListener listener;

    private NumberPicker monthPicker;
    
    /**
     * Set the button label and action
     *
     * @param label the Label of the button
     * @param action the action of the button
     */
    public void setButton(String label, String action) {
        this.buttonLabel = label;
        this.buttonAction = action;
    }

    /**
     * Set the listener to this DateDialogFragment
     *
     * @param listener to inform of an action button click
     */
    public void setListener(DateDialogListener listener) {
        this.listener = listener;
    }

    public void setValues(int selMonthIndex, String[] monthLabels, int selYearIndex, String[] yearLabels) {
        this.selMonthIndex = selMonthIndex;
        this.monthLabels = monthLabels;
        this.selYearIndex = selYearIndex;
        this.yearLabels = yearLabels;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_date, container, false);
        initNumberPickers(v);
        initButton(v);
        return v;
    }

    private void initNumberPickers(View rootView) {
        
        monthPicker = rootView.findViewById(R.id.numberpicker_month);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(monthLabels.length - 1);
        monthPicker.setDisplayedValues(monthLabels);

        yearPicker = rootView.findViewById(R.id.numberpicker_year);
        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(yearLabels.length - 1);
        yearPicker.setDisplayedValues(yearLabels);
    }
    
    private void initButton(View rootView) {
        View layout = rootView.findViewById(R.id.layout_button);
        layout.setVisibility(View.VISIBLE);
        TextView tv = rootView.findViewById(R.id.text_button);
        tv.setText(buttonLabel);

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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void handleButtonClick() {
        if (this.listener != null) {
            listener.onDateChanged(null, null);
        }
        dismiss();
    }

    public interface DateDialogListener {
        void onDateChanged(String month, String year);
    }
}
