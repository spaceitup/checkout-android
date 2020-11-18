/*
 * Copyright (c) 2020 optile GmbH
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
     */
    public RegisterWidget(String name, View rootView) {
        super(name, rootView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
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
