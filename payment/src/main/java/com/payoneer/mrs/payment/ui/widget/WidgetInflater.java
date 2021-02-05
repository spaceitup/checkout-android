/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.model.InputElementType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class with helper methods to inflate widgets
 */
public final class WidgetInflater {

    /**
     * Inflate an InputElement widget, this will either be a select, checkbox or textinput widget.
     *
     * @param element the InputElement from which the widget will be created
     * @param parent the parent ViewGroup in which this CheckBoxWidget will be placed
     * @return the inflated FormWidget
     */
    public static FormWidget inflateElementWidget(InputElement element, ViewGroup parent) {
        String name = element.getName();
        switch (element.getType()) {
            case InputElementType.SELECT:
                return inflateSelectWidget(name, parent);
            case InputElementType.CHECKBOX:
                return inflateCheckBoxWidget(name, parent);
            default:
                return inflateTextInputWidget(name, parent);
        }
    }

    /**
     * Inflate a VerificationCodeWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this VerificationCodeWidget will be placed
     * @return inflated VerificationCodeWidget
     */
    public static VerificationCodeWidget inflateVerificationCodeWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_textinput);
        return new VerificationCodeWidget(name, view);
    }

    /**
     * Inflate a CheckBoxWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this CheckBoxWidget will be placed
     * @return inflated CheckBoxWidget
     */
    public static CheckBoxWidget inflateCheckBoxWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_checkbox);
        return new CheckBoxWidget(name, view);
    }

    /**
     * Inflate a RegisterWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this RegisterWidget will be placed
     * @return inflated RegisterWidget
     */
    public static RegisterWidget inflateRegisterWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_checkbox);
        return new RegisterWidget(name, view);
    }

    /**
     * Inflate a ButtonWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this ButtonWidget will be placed
     * @return inflated ButtonWidget
     */
    public static ButtonWidget inflateButtonWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_button);
        return new ButtonWidget(name, view);
    }

    /**
     * Inflate a DateWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this DateWidget will be placed
     * @return inflated DateWidget
     */
    public static DateWidget inflateDateWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_textinput);
        return new DateWidget(name, view);
    }

    /**
     * Inflate a TextInputWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this TextInputWidget will be placed
     * @return inflated TextInputWidget
     */
    public static TextInputWidget inflateTextInputWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_textinput);
        return new TextInputWidget(name, view);
    }

    /**
     * Inflate a SelectWidget
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this SelectWidget will be placed
     * @return inflated SelectWidget
     */
    public static SelectWidget inflateSelectWidget(String name, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_select);
        return new SelectWidget(name, view);
    }

    /**
     * Inflate the layout given the parent ViewGroup
     *
     * @param parent ViewGroup in which this inflated view will be added
     * @param layoutResId layout resource id of the view that should be inflated
     * @return the inflated view
     */
    private static View inflate(ViewGroup parent, int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(layoutResId, parent, false);
    }
}

