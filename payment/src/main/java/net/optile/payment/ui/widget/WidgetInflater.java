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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.view.LayoutInflater;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.util.InflaterUtils;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.IconParameters;
import net.optile.payment.ui.theme.ButtonWidgetParameters;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;
import android.view.ContextThemeWrapper;

/**
 * Class with helper methods to inflate the different widgets with the proper theming
 */
public final class WidgetInflater {

    /** 
     * Inflate an InputElement widget, this will either be a select, checkbox or textinput widget.
     * 
     * @param element the InputElement from which the widget will be created
     * @param parent ViewGroup in which the widget will be stored
     * @param theme custom theming that will be applied to the widget
     * @return the themed FormWidget 
     */
    public static FormWidget inflateInputElementWidget(InputElement element, ViewGroup parent, PaymentTheme theme) {
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
     * Inflate a RegisterWidget with the theming included 
     * 
     * @return inflated RegisterWidget
     */
    public static RegisterWidget inflateRegisterWidget() {
        
    }
    
    public static ButtonWidget inflateButtonWidget() {
    }

    public static DateWidget inflateDateWidget() {
    }

    static RegisterWidget inflateRegisterWidget(String name, ListAdapter adapter, ViewGroup parent, String name) {
        CheckBoxWidgetParameters params = adapter.getPaymentTheme().getCheckBoxWidgetParameters();
        View view = inflateWithView(parent, R.layout.widget_checkbox, R.layout.view_checkbox, params.getThemeResId());
        RegisterWidget widget = new RegisterWidget(name, view);
        return widget;
    }

    static TextInputWidget createTextInputWidget(String name, ListAdapter adapter, ViewGroup parent) {
        View view = InflaterUtils.inflate(parent, R.layout.widget_textinput);        
        return new TextInputWidget(name, view);
    }
    
    static CheckBoxWidget createCheckBoxWidget(String name, ListAdapter adapter, ViewGroup parent) {
        View view = InflaterUtils.inflate(parent, R.layout.widget_checkbox);
        return new CheckBoxWidget(name, view);
    }

    static SelectWidget createSelectWidget(String name, ListAdapter adapter, ViewGroup parent) {
        View view = InflaterUtils.inflate(parent, R.layout.widget_select);
        return new SelectWidget(name, view);
    }
    
    static ButtonWidget createButtonWidget(ListAdapter adapter, ViewGroup parent) {
        ButtonWidgetParameters params = adapter.getPaymentTheme().getButtonWidgetParameters();
        View view = inflateWithView(parent, R.layout.widget_button, R.layout.view_button, params.getThemeResId());
        return new ButtonWidget(PaymentInputType.ACTION_BUTTON, view);
    }

    static DateWidget createDateWidget(ListAdapter adapter, ViewGroup parent) {
        View view = inflate(parent, R.layout.widget_date);
        return new DateWidget(PaymentInputType.EXPIRY_DATE, view);
    }


}

