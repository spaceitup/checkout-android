/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.util.PaymentUtils;

/**
 * Base class for all widgets, i.e. ButtonWidget, CheckBoxWidget, TextInputWidget etc.
 */
public abstract class FormWidget {

    final static int VALIDATION_UNKNOWN = 0x00;
    final static int VALIDATION_ERROR = 0x01;
    final static int VALIDATION_OK = 0x02;

    final View rootView;
    final String name;
    final ImageView icon;
    final PaymentTheme theme;

    WidgetPresenter presenter;
    int state;

    FormWidget(String name, View rootView, PaymentTheme theme) {
        PaymentUtils.setTestTag(rootView, "widget", name);
        this.name = name;
        this.rootView = rootView;
        this.theme = theme;
        this.icon = rootView.findViewById(R.id.image_icon);
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
     * Set the resource ID of the validation icon in front of this widget
     *
     * @param resId resource id of the icon
     */
    public final void setIconResource(@DrawableRes int resId) {

        if (icon != null) {
            icon.setImageResource(resId);
            setIconColor(this.state);
        }
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
        setIconColor(state);
    }

    private void setIconColor(int state) {

        if (icon == null) {
            return;
        }
        WidgetParameters params = theme.getWidgetParameters();
        int colorResId = params.getValidationColorUnknown();
        switch (state) {
            case VALIDATION_OK:
                colorResId = params.getValidationColorOk();
                break;
            case VALIDATION_ERROR:
                colorResId = params.getValidationColorError();
        }
        if (colorResId != 0) {
            icon.setColorFilter(ContextCompat.getColor(rootView.getContext(), colorResId));
        }
    }
}
