/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget;

import com.payoneer.mrs.payment.core.PaymentException;
import com.payoneer.mrs.payment.form.Operation;
import com.payoneer.mrs.payment.util.PaymentUtils;

import android.view.View;

/**
 * Base class for all widgets, i.e. ButtonWidget, CheckBoxWidget, TextInputWidget etc.
 */
public abstract class FormWidget {

    final static int VALIDATION_UNKNOWN = 0x00;
    final static int VALIDATION_ERROR = 0x01;
    final static int VALIDATION_OK = 0x02;

    final View rootView;
    final String name;

    WidgetPresenter presenter;
    int state;

    FormWidget(String name, View rootView) {
        PaymentUtils.setTestId(rootView, "widget", name);
        this.name = name;
        this.rootView = rootView;
    }

    /**
     * Set the presenter in this widget, the presenter may be used by this widget i.e. to inform of events or validate input.
     *
     * @param presenter to be set in this widget
     */
    public final void setPresenter(WidgetPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Get the rootView of this Widget
     *
     * @return the root view of this widget
     */
    public final View getRootView() {
        return rootView;
    }

    /**
     * Get the name of this widget, i.e. "number", "iban" or "bic"
     *
     * @return name of this widget
     */
    public final String getName() {
        return name;
    }

    /**
     * Set this widget to be the last ImeOptions if it supports ImeOptions.
     *
     * @return true when set, false otherwise
     */
    public boolean setLastImeOptionsWidget() {
        return false;
    }

    /**
     * Set the validation in this widget given the current input value.
     */
    public void setValidation() {
    }

    /**
     * Clear the focus of this widget if it supports focus i.e. the TextLayoutWidget.
     */
    public void clearFocus() {
    }

    /**
     * Set this widget to be focussed.
     *
     * @return true when focussed, false otherwise
     */
    public boolean requestFocus() {
        return false;
    }

    /**
     * Request the widget to inject its input value into the operation Object.
     *
     * @param operation in which the input value should be added
     */
    public void putValue(Operation operation) throws PaymentException {
    }

    /**
     * Request the widget to validate itself given the current input value.
     *
     * @return true when validated, false otherwise
     */
    public boolean validate() {
        setValidationState(VALIDATION_OK);
        return true;
    }

    /**
     * Set this widget visible or hide it.
     *
     * @param visible true when visible, false for hiding this widget
     */
    final void setVisible(boolean visible) {
        rootView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Set the validation state of this widget
     *
     * @param state to be set
     */
    final void setValidationState(int state) {
        this.state = state;
    }
}
