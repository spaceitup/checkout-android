/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.text.TextUtils;
import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.widget.input.ExpiryDateInputMode;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * Widget for handling the date input
 */
public final class DateWidget extends InputLayoutWidget {

    private InputElement monthElement;
    private InputElement yearElement;

    /**
     * Construct a new DateWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public DateWidget(String name, View rootView) {
        super(name, rootView);
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
        operation.putValue(monthElement.getName(), date.month);
        operation.putValue(yearElement.getName(), date.year);
    }

    /**
     * Set the InputElements in this DateWidget
     *
     * @param monthElement expiry month element
     * @param yearElement expiry year element
     */
    public void onBind(String code, InputElement monthElement, InputElement yearElement) { 
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translateAccountPlaceholder(code, name));

        this.monthElement = monthElement;
        this.yearElement = yearElement;
        setTextInputMode(new ExpiryDateInputMode());
        setValidation();
    }

    private ExpiryDate getExpiryDate() {
        String[] split = getValue().split(ExpiryDateInputMode.DIVIDER);
        String month = split.length > 0 ? split[0] : "";
        String year = split.length > 1 ? split[1] : "";

        if (!TextUtils.isEmpty(year)) {
            int expiryYear = PaymentUtils.createExpiryYear(Integer.parseInt(year));
            year = Integer.toString(expiryYear);
        }
        return new ExpiryDate(month, year);
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
