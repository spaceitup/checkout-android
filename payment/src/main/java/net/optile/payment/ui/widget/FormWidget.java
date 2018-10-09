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

/**
 * The base InputWidget
 */
public abstract class FormWidget {

    final View rootView;

    final String name;

    OnWidgetListener listener;

    FormWidget(String name, View rootView) {
        this.name = name;
        this.rootView = rootView;
    }

    public void setListener(OnWidgetListener listener) {
        this.listener = listener;
    }

    public View getRootView() {
        return rootView;
    }

    public String getName() {
        return name;
    }

    public void setVisible(boolean visible) {
        rootView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public boolean setLastImeOptionsWidget() {
        return false;
    }

    public void putValue(Charge charge) throws PaymentException {
    }

    /**
     * The widget listener
     */
    public interface OnWidgetListener {
        void onActionClicked(FormWidget widget);
    }
}
