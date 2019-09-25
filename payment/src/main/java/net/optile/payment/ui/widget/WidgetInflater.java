/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * Class with helper methods to inflate widgets with proper theming
 */
public final class WidgetInflater {

    /**
     * Inflate an InputElement widget, this will either be a select, checkbox or textinput widget.
     *
     * @param element the InputElement from which the widget will be created
     * @param parent the parent ViewGroup in which this CheckBoxWidget will be placed
     * @param theme used to style the CheckBox widget
     * @return the inflated themed FormWidget
     */
    public static FormWidget inflateElementWidget(InputElement element, ViewGroup parent, PaymentTheme theme) {
        String name = element.getName();
        switch (element.getType()) {
            case InputElementType.SELECT:
                return inflateSelectWidget(name, parent, theme);
            case InputElementType.CHECKBOX:
                return inflateCheckBoxWidget(name, parent, theme);
            default:
                return inflateTextInputWidget(name, parent, theme);
        }
    }

    /**
     * Inflate a CheckBoxWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this CheckBoxWidget will be placed
     * @param theme used to style the CheckBox widget
     * @return inflated and themed CheckBoxWidget
     */
    public static CheckBoxWidget inflateCheckBoxWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View layout = inflateWithThemedChild(parent, R.layout.widget_checkbox, R.layout.view_checkbox);
        return new CheckBoxWidget(name, layout, theme);
    }

    /**
     * Inflate a RegisterWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this RegisterWidget will be placed
     * @param theme used to style the CheckBox widget
     * @return inflated and themed RegisterWidget
     */
    public static RegisterWidget inflateRegisterWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View layout = inflateWithThemedChild(parent, R.layout.widget_checkbox, R.layout.view_checkbox);
        return new RegisterWidget(name, layout, theme);
    }

    /**
     * Inflate a ButtonWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this ButtonWidget will be placed
     * @param theme used to style the ButtonWidget
     * @return inflated and themed ButtonWidget
     */
    public static ButtonWidget inflateButtonWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View layout = inflateWithThemedChild(parent, R.layout.widget_button, R.layout.view_button);
        return new ButtonWidget(name, layout, theme);
    }

    /**
     * Inflate a DateWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this DateWidget will be placed
     * @param theme used to style the DateWidget
     * @return inflated and themed DateWidget
     */
    public static DateWidget inflateDateWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View layout = inflateTextInputView(parent);
        return new DateWidget(name, layout, theme);
    }

    /**
     * Inflate a TextInputWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this TextInputWidget will be placed
     * @param theme used to style the TextInputWidget
     * @return inflated and themed TextInputWidget
     */
    public static TextInputWidget inflateTextInputWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View layout = inflateTextInputView(parent);
        return new TextInputWidget(name, layout, theme);
    }

    /**
     * Inflate a SelectWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this SelectWidget will be placed
     * @param theme used to style the SelectWidget
     * @return inflated and themed SelectWidget
     */
    public static SelectWidget inflateSelectWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View view = inflate(parent, R.layout.widget_select);
        return new SelectWidget(name, view, theme);
    }

    /**
     * Inflate a LabelWidget with the proper theming
     *
     * @param name unique name of the widget
     * @param parent the parent ViewGroup in which this SelectWidget will be placed
     * @param theme used to style the LabelWidget
     * @return inflated and themed LabelWidget
     */
    public static LabelWidget inflateLabelWidget(String name, ViewGroup parent, PaymentTheme theme) {
        View view = inflate(parent, R.layout.widget_label);
        return new LabelWidget(name, view, theme);
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

    /**
     * Inflate the input layout and add the hint element to it
     *
     * @param parent ViewGroup in which this inflated view will be added
     * @return the inflated view
     */
    private static View inflateTextInputView(ViewGroup parent) {
        View view = inflateWithThemedChild(parent, R.layout.widget_textinput, R.layout.view_textinput);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup group = view.findViewById(R.id.layout_viewholder);
        inflater.inflate(R.layout.view_hint, group, true);
        return view;
    }

    /**
     * Inflate the layout and attach the themed internal view
     *
     * @param parent ViewGroup in which this inflated view will be added
     * @param layoutResId layout resource id of the view that should be inflated
     * @param childResId internal view resource id that should be inflated with the theme
     * @return the inflated view
     */
    private static View inflateWithThemedChild(ViewGroup parent, int layoutResId, int childResId) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResId, parent, false);
        ViewGroup group = view.findViewById(R.id.layout_viewholder);
        if (group == null) {
            return view;
        }
        inflater.inflate(childResId, group, true);
        return view;
    }
}

