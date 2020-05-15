/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import java.util.Calendar;

import android.text.TextUtils;
import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.widget.input.ExpiryDateInputMode;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling the date input
 */
public final class DateWidget extends TextInputWidget {

    private InputElement monthElement;
    private InputElement yearElement;

    /**
     * Construct a new DateWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme the PaymentTheme to apply
     */
    public DateWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        ExpiryDate date = getExpiryDate();
        ValidationResult result = presenter.validate(name, date.month, date.year);
        return setValidationResult(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        ExpiryDate date = getExpiryDate();
        if (!(TextUtils.isEmpty(date.month) || TextUtils.isEmpty(date.year))) {
            operation.putValue(monthElement.getName(), date.month);
            operation.putValue(yearElement.getName(), date.year);
        }
    }

    public void setInputElements(InputElement monthElement, InputElement yearElement) {
        this.monthElement = monthElement;
        this.yearElement = yearElement;
    }

    private ExpiryDate getExpiryDate() {
        String[] split = getValue().split(ExpiryDateInputMode.DIVIDER);
        String month = split.length > 0 ? split[0] : "";
        String year = split.length > 1 ? split[1] : "";

        int century = (Calendar.getInstance().get(Calendar.YEAR) / 100);
        return new ExpiryDate(month, century + year);
    }

    private class ExpiryDate {
        final String month;
        final String year;

        ExpiryDate(String month, String year) {
            this.month = month;
            this.year = year;
        }
    }
}
