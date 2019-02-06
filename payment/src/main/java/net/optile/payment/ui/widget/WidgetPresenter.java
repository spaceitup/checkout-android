/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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
     * Inform the presenter that the hint icon has been clicked for the input field with the given type
     *
     * @param type the type of the widget
     */
    void onHintClicked(String type);

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
     * @return ValidationResult holding the result of the validation
     */
    ValidationResult validate(String type, String value1, String value2);

    /**
     * Notify that text has changed in this widget.
     *
     * @param type the type of the TextInput widget
     * @param text new text of the textInput
     */
    void onTextInputChanged(String type, String text);
}
