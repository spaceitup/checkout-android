/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import java.util.List;

import com.google.android.material.textfield.TextInputEditText;

import android.text.TextUtils;
import android.view.View;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.SelectOption;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.dialog.DateDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling the date input
 */
public final class DateWidget extends InputLayoutWidget implements ThemedDialogListener {

    private String dialogButtonLabel;
    private InputElement monthElement;
    private InputElement yearElement;
    private String expiryMonth;
    private String expiryYear;
    private int selMonthIndex;
    private int selYearIndex;

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme the PaymentTheme to apply
     */
    public DateWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);

        textInput.setKeyListener(null);
        textInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogFragment();
            }
        });
        setReducedView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        ValidationResult result = presenter.validate(name, expiryMonth, expiryYear);
        return setValidationResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {

        if (!(TextUtils.isEmpty(expiryMonth) || TextUtils.isEmpty(expiryYear))) {
            operation.putValue(monthElement.getName(), expiryMonth);
            operation.putValue(yearElement.getName(), expiryYear);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDismissed(ThemedDialogFragment dialog) {
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

    void handleOnFocusChange(boolean hasFocus) {
        if (hasFocus) {
            setInputLayoutState(VALIDATION_UNKNOWN, false, null);
            showDateDialogFragment();
        } else if (state == VALIDATION_UNKNOWN && !(TextUtils.isEmpty(expiryMonth) || TextUtils.isEmpty(expiryYear))) {
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
        DateDialogFragment dateDialog = createDateDialogFragment(monthOptions, yearOptions);
        presenter.showDialogFragment(dateDialog, "date_dialog");
    }

    private DateDialogFragment createDateDialogFragment(List<SelectOption> monthOptions, List<SelectOption> yearOptions) {
        int selMonthIndex = this.selMonthIndex;
        String[] monthLabels = new String[monthOptions.size()];
        int selYearIndex = this.selYearIndex;
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
        dialog.setNeutralButton(dialogButtonLabel);
        dialog.setListener(this);
        return dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onButtonClicked(ThemedDialogFragment dialog, int which) {
        DateDialogFragment dateDialog = (DateDialogFragment) dialog;

        this.selMonthIndex = dateDialog.getMonthIndex();
        this.selYearIndex = dateDialog.getYearIndex();

        SelectOption monthOption = monthElement.getOptions().get(selMonthIndex);
        SelectOption yearOption = yearElement.getOptions().get(selYearIndex);

        this.expiryMonth = monthOption.getValue();
        this.expiryYear = yearOption.getValue();

        String format = rootView.getContext().getString(R.string.pmlist_widget_date);
        textInput.setText(String.format(format, monthOption.getLabel(), yearOption.getLabel()));
        View nextField = textInput.focusSearch(View.FOCUS_DOWN);

        if (nextField instanceof TextInputEditText) {
            nextField.requestFocus();
            presenter.showKeyboard();
        } else {
            textInput.clearFocus();
        }
    }
}
