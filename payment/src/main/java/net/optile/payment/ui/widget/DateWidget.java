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

import android.util.Log;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.validation.ValidationResult;
import net.optile.payment.ui.dialog.DateDialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.SelectOption;
import net.optile.payment.util.PaymentUtils;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for handling date input
 */
public final class DateWidget extends InputLayoutWidget implements DateDialogFragment.DateDialogListener {

    private InputElement monthElement;

    private InputElement yearElement;

    private final String button;

    private String expiryMonth;

    private String expiryYear;

    private DateDialogFragment dateDialog;
    
    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param label localized label for this date widget
     * @param button localized button label
     */
    public DateWidget(String name, View rootView, String label, String button) {
        super(name, rootView, label);
        this.button = button;

        input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOnClick();
                }
            });
        setLayoutWidth(WEIGHT_REDUCED);
    }

    public void setMonthInputElement(InputElement monthElement) {
        this.monthElement = monthElement;
    }

    public void setYearInputElement(InputElement yearElement) {
        this.yearElement = yearElement;
    }
    
    public boolean validate() {
        ValidationResult result = presenter.validate(name, expiryMonth, expiryYear);

        if (result == null) {
            return false;
        }
        if (result.isError()) {
            setValidation(VALIDATION_ERROR, true, result.getMessage());
            return false;
        }
        setValidation(VALIDATION_OK, false, null);
        return true;
    }

    public void putValue(Charge charge) throws PaymentException {

        if (!(TextUtils.isEmpty(expiryMonth) || TextUtils.isEmpty(expiryYear))) {
            charge.putValue(monthElement.getName(), expiryMonth);
            charge.putValue(yearElement.getName(), expiryYear);                
        }
    }

    private void handleOnClick() {
        presenter.hideKeyboard();

        if (monthElement == null || yearElement == null) {
            return;
        }
        List<SelectOption> monthOptions = monthElement.getOptions();
        List<SelectOption> yearOptions = yearElement.getOptions();

        if (monthOptions == null || monthOptions.size() == 0 || yearOptions == null || yearOptions.size() == 0) {
            return;
        }
        if (this.dateDialog == null) {
            dateDialog = createDateDialogFragment(monthOptions, yearOptions);
        }
        presenter.showDialogFragment(dateDialog, "date_dialog");
    }

    private DateDialogFragment createDateDialogFragment(List<SelectOption> monthOptions, List<SelectOption> yearOptions) {
        int selMonthIndex = 0;
        String[] monthLabels = new String[monthOptions.size()];
        int selYearIndex = 0;
        String[] yearLabels = new String[yearOptions.size()];

        SelectOption option;
        for (int i = 0, e = monthOptions.size(); i < e; i++) {
            option = monthOptions.get(i);
            monthLabels[i] = option.getLabel();
            
            if (PaymentUtils.isTrue(option.getSelected())) {
                selMonthIndex = i;
            }
        }
        for (int i = 0, e = yearOptions.size(); i < e; i++) {
            option = yearOptions.get(i);
            yearLabels[i] = option.getLabel();
            
            if (PaymentUtils.isTrue(option.getSelected())) {
                selYearIndex = i;
            }
        }
        DateDialogFragment dialog = new DateDialogFragment();
        dialog.setTitle(label);
        dialog.setValues(selMonthIndex, monthLabels, selYearIndex, yearLabels);
        dialog.setButton(button, null);
        dialog.setListener(this);
        return dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDateChanged(int monthIndex, String monthLabel, int yearIndex, String yearLabel) {
        this.expiryMonth = monthElement.getOptions().get(monthIndex).getValue();
        this.expiryYear = yearElement.getOptions().get(yearIndex).getValue();         
        input.setText(String.format(getString(R.string.widget_date_format), monthLabel, yearLabel));
        validate();
    }
}
