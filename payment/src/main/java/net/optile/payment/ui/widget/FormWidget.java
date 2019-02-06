/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;

/**
 * The base class for all widgets, i.e. Button, CheckBox, TextInput etc.
 */
public abstract class FormWidget {

    final static int VALIDATION_UNKNOWN = 0x00;
    final static int VALIDATION_ERROR = 0x01;
    final static int VALIDATION_OK = 0x02;

    final View rootView;
    final String name;
    final ImageView icon;

    WidgetPresenter presenter;
    int state;
    String error;
    PaymentTheme theme;

    FormWidget(String name, View rootView, PaymentTheme theme) {
        this.name = name;
        this.rootView = rootView;
        this.theme = theme;
        this.icon = rootView.findViewById(R.id.image_icon);
    }

    public void setPresenter(WidgetPresenter presenter) {
        this.presenter = presenter;
    }

    public View getRootView() {
        return rootView;
    }

    public String getName() {
        return name;
    }

    public void setIconResource(int resId) {

        if (icon != null) {
            icon.setImageResource(resId);
            setIconColor(this.state);
        }
    }

    void setVisible(boolean visible) {
        rootView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public boolean setLastImeOptionsWidget() {
        return false;
    }

    public void clearInputErrors() {
    }

    public void setLabel(String label) {
    }

    public boolean isValid() {
        return this.state == VALIDATION_OK;
    }

    public void putValue(Operation operation) throws PaymentException {
    }

    public boolean validate() {
        setState(VALIDATION_OK);
        return true;
    }

    void setState(int state) {
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
