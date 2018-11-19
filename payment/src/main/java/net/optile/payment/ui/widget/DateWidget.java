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

import java.util.List;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.SelectOption;
import net.optile.payment.ui.dialog.DateDialogFragment;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * Class for handling date input
 */
public final class DateWidget extends InputLayoutWidget implements DateDialogFragment.DateDialogListener {

    private String dialogButtonLabel;
    private InputElement monthElement;
    private InputElement yearElement;
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
    public DateWidget(String name, View rootView) {
        super(name, rootView);
        input.setKeyListener(null);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogFragment();
            }
        });
        setLayoutWidth(WEIGHT_REDUCED);
    }

    public void setDialogButtonLabel(String dialogButtonLabel) {
        this.dialogButtonLabel = dialogButtonLabel;
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
        boolean validated = false;

        if (result.isError()) {
            setValidation(VALIDATION_ERROR, true, result.getMessage());
        } else {
            setValidation(VALIDATION_OK, false, null);
            validated = true;
        }
        if (input.hasFocus()) {
            input.clearFocus();
        }
        return validated;
    }

    public void putValue(Charge charge) throws PaymentException {

        if (!(TextUtils.isEmpty(expiryMonth) || TextUtils.isEmpty(expiryYear))) {
            charge.putValue(monthElement.getName(), expiryMonth);
            charge.putValue(yearElement.getName(), expiryYear);
        }
    }

    void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setValidation(VALIDATION_UNKNOWN, false, null);
            showDateDialogFragment();
        } else if (state == VALIDATION_UNKNOWN) {
            validate();
        }
    }

    private void showDateDialogFragment() {
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
        dialog.setButtonLabel(dialogButtonLabel);
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

        String format = rootView.getContext().getString(R.string.widget_date_format);
        input.setText(String.format(format, monthLabel, yearLabel));

        View nextField = input.focusSearch(View.FOCUS_DOWN);
        if (nextField instanceof TextInputEditText) {
            nextField.requestFocus();
            presenter.showKeyboard();
        } else {
            input.clearFocus();
        }
    }
}
