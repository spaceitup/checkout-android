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

import android.view.View;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.RegistrationType;

/**
 * Class for handling the Register input type
 */
public final class RegisterWidget extends CheckBoxInputWidget {

    private String type;

    /**
     * Construct a new RegisterWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public RegisterWidget(String name, View rootView) {
        super(name, rootView);
    }

    public void putValue(Charge charge) throws PaymentException {

        if (!RegistrationType.isValid(type)) {
            return;
        }
        switch (type) {
            case RegistrationType.FORCED:
            case RegistrationType.FORCED_DISPLAYED:
                charge.putRegister(name, true);
                break;
            case RegistrationType.OPTIONAL:
            case RegistrationType.OPTIONAL_PRESELECTED:
                charge.putRegister(name, isChecked());
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
                break;
            case RegistrationType.OPTIONAL_PRESELECTED:
                setVisible(true);
                initCheckBox(true, true);
                break;
            case RegistrationType.FORCED_DISPLAYED:
                setVisible(true);
                initCheckBox(false, true);
                break;
            default:
                setVisible(false);
        }
    }
}
