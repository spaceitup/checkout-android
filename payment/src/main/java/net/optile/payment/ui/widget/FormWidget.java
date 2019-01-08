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

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
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

    public void setLabel(String label) {
    }

    public boolean isValid() {
        return this.state == VALIDATION_OK;
    }

    public void putValue(Charge charge) throws PaymentException {
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
