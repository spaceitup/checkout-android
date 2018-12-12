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

import android.support.v4.app.DialogFragment;
import net.optile.payment.validation.ValidationResult;

/**
 * The presenter which is controlling each widget
 */
public interface WidgetPresenter {

    /**
     * Inform the presenter that the Pay Button has been clicked
     */
    void onActionClicked();

    /**
     * Ask the presenter to hide the keyboard
     */
    void hideKeyboard();

    /**
     * Ask the presenter to show the keyboard
     */
    void showKeyboard();

    /**
     * Ask the presenter to show the DialogFragment
     *
     * @param dialog to be shown to the user
     * @param tag to identify the DialogFragment
     */
    void showDialogFragment(DialogFragment dialog, String tag);

    /**
     * Widgets call this method to validate their input values. The first value is mandatory, the second is optional.
     * I.e. A Date widget may use both values to validate the month and year values at the same time.
     *
     * @param type type of the value to be validated
     * @param value1 mandatory first value to validate
     * @param value2 optional second value to validate
     * @return ValidationResult holding the result of the validate
     */
    ValidationResult validate(String type, String value1, String value2);
}
