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
import net.optile.payment.validation.ValidationResult;

/**
 * The base InputWidget
 */
public abstract class FormWidget {

    public final static int VALIDATION_UNKNOWN = 0x00;
    public final static int VALIDATION_ERROR = 0x01;
    public final static int VALIDATION_OK = 0x02;

    final View rootView;

    final String name;

    final ImageView icon;

    WidgetPresenter presenter;

    int state;

    String error;

    FormWidget(String name, View rootView) {
        this.name = name;
        this.rootView = rootView;
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

    public boolean isValid() {
        return this.state == VALIDATION_OK;
    }

    public void setVisible(boolean visible) {
        rootView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public boolean setLastImeOptionsWidget() {
        return false;
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
        int colorResId = R.color.validation_ok;
        switch (state) {
            case VALIDATION_OK:
                colorResId = R.color.validation_ok;
                break;
            case VALIDATION_ERROR:
                colorResId = R.color.validation_error;
                break;
            default:
                colorResId = R.color.validation_unknown;
        }
        icon.setColorFilter(ContextCompat.getColor(rootView.getContext(), colorResId));
    }

    /**
     * The widget presenter
     */
    public interface WidgetPresenter {

        void onActionClicked();

        void onKeyboardDone();

        ValidationResult validate(String type, String value1, String value2);
    }
}
