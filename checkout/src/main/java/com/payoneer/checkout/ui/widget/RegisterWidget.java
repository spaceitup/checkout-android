/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.widget;

import static com.payoneer.checkout.localization.LocalizationKey.ALLOW_RECURRENCE_FORCED;
import static com.payoneer.checkout.localization.LocalizationKey.ALLOW_RECURRENCE_OPTIONAL;
import static com.payoneer.checkout.localization.LocalizationKey.AUTO_REGISTRATION_FORCED;
import static com.payoneer.checkout.localization.LocalizationKey.AUTO_REGISTRATION_OPTIONAL;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.model.RegistrationType;

/**
 * Widget for handling the Register input
 */
public final class RegisterWidget extends CheckBoxWidget {

    private String type;

    /**
     * Construct a new RegisterWidget
     *
     * @param name name identifying this widget
     */
    public RegisterWidget(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        switch (type) {
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                operation.putBooleanValue(name, true);
                break;
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
                operation.putBooleanValue(name, isChecked());
        }
    }

    public void onBind(String type) {
        this.type = type;
        switch (type) {
            case RegistrationType.OPTIONAL:
                setVisible(true);
                setCheckboxVisible(true);
                setChecked(false);
                setRegistrationLabel(false);
                break;
            case RegistrationType.OPTIONAL_PRESELECTED:
                setVisible(true);
                setCheckboxVisible(true);
                setChecked(true);
                setRegistrationLabel(false);
                break;
            case RegistrationType.FORCED_DISPLAYED:
                setVisible(true);
                setCheckboxVisible(false);
                setRegistrationLabel(true);
                break;
            default:
                setVisible(false);
        }
    }

    private void setRegistrationLabel(boolean forced) {
        String key;
        if (PaymentInputType.ALLOW_RECURRENCE.equals(name)) {
            key = forced ? ALLOW_RECURRENCE_FORCED : ALLOW_RECURRENCE_OPTIONAL;
        } else {
            key = forced ? AUTO_REGISTRATION_FORCED : AUTO_REGISTRATION_OPTIONAL;
        }
        setLabel(Localization.translate(key));
    }
}
