/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import static net.optile.payment.localization.LocalizationKey.ALLOW_RECURRENCE_FORCED;
import static net.optile.payment.localization.LocalizationKey.ALLOW_RECURRENCE_OPTIONAL;
import static net.optile.payment.localization.LocalizationKey.AUTO_REGISTRATION_FORCED;
import static net.optile.payment.localization.LocalizationKey.AUTO_REGISTRATION_OPTIONAL;

import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.RegistrationType;
import net.optile.payment.ui.PaymentTheme;

/**
 * Widget for handling the Register input
 */
public final class RegisterWidget extends CheckBoxWidget {

    private String type;

    /**
     * Construct a new RegisterWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to be applied
     */
    public RegisterWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {

        if (!RegistrationType.isValid(type)) {
            return;
        }
        switch (type) {
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                operation.putValue(name, true);
                break;
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
                operation.putValue(name, isChecked());
        }
    }

    public void setRegistrationType(String type) {
        this.type = type;
        if (!RegistrationType.isValid(type)) {
            setVisible(false);
            return;
        }
        switch (type) {
            case RegistrationType.OPTIONAL:
                setVisible(true);
                setRegistrationLabel(false);
                initCheckBox(true, true, false);
                break;
            case RegistrationType.OPTIONAL_PRESELECTED:
                setVisible(true);
                setRegistrationLabel(false);
                initCheckBox(true, true, true);
                break;
            case RegistrationType.FORCED_DISPLAYED:
                setVisible(true);
                setRegistrationLabel(true);
                initCheckBox(false, false, true);
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
