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
public final class DateWidget extends FormWidget {

    private final TextInputEditText input;

    private final TextInputLayout layout;

    private final String label;

    private InputElement monthElement;

    private InputElement yearElement;
    
    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param label localized label for this date widget
     */
    public DateWidget(String name, View rootView, String label) {
        super(name, rootView);
        this.label = label;
        
        layout = rootView.findViewById(R.id.layout_value);
        input = rootView.findViewById(R.id.input_value);

        layout.setHintAnimationEnabled(false);
        layout.setHint(label);
        layout.setHintAnimationEnabled(true);

        input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOnClick();
                }
            });
    }

    public void setMonthInputElement(InputElement monthElement) {
        this.monthElement = monthElement;
    }

    public void setYearInputElement(InputElement yearElement) {
        this.yearElement = yearElement;
    }
    
    public boolean validate() {
        ValidationResult result = presenter.validate(name, null, null);

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
        dialog.setValues(selMonthIndex, monthLabels, selYearIndex, yearLabels);
        dialog.setButton("Select", null);
        presenter.showDialogFragment(dialog, "date_dialog");
    }
    
    private void setValidation(int state, boolean errorEnabled, String message) {
        setState(state);
        layout.setErrorEnabled(errorEnabled);
        layout.setError(message);
    }
}
