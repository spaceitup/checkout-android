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

package net.optile.payment.ui.paymentpage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.validation.ValidationResult;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxInputWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.SelectInputWidget;
import net.optile.payment.ui.widget.TextInputWidget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import com.bumptech.glide.Glide;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxInputWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.SelectInputWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.validation.ValidationResult;


/**
 * The PaymentCardViewHolder holding the header and input widgets 
 */
class PaymentCardViewHolder extends RecyclerView.ViewHolder {

    final static String ACTION_BUTTON = "actionbutton";

    final ViewGroup formLayout;
    final PaymentListAdapter adapter;
    final FormWidget.WidgetPresenter presenter;
    final Map<String, FormWidget> widgets;

    PaymentCardViewHolder(PaymentListAdapter adapter, View parent) {
        super(parent);

        this.adapter = adapter;
        this.formLayout = parent.findViewById(R.id.layout_form);
        this.widgets = new LinkedHashMap<>();

        View view = parent.findViewById(R.id.layout_header);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onItemClicked(getAdapterPosition());
            }
        });

        this.presenter = new FormWidget.WidgetPresenter() {
            @Override
            public void onActionClicked() {
                adapter.onActionClicked(getAdapterPosition());
            }
            @Override
            public void hideKeyboard() {
                adapter.hideKeyboard(getAdapterPosition());
            }
            @Override
            public void showKeyboard() {
                adapter.showKeyboard(getAdapterPosition());
            }
            @Override
            public void showDialogFragment(DialogFragment dialog, String tag) {
                adapter.showDialogFragment(getAdapterPosition(), dialog, tag);
            }
            @Override
            public ValidationResult validate(String type, String value1, String value2) {
                return adapter.validate(getAdapterPosition(), type, value1, value2);
            }
        };
    }

    void expand(boolean expand) {
        formLayout.setVisibility(expand ? View.VISIBLE : View.GONE);
    }

    void addWidget(FormWidget widget) {
        widget.setPresenter(presenter);
        widgets.put(widget.getName(), widget);
        formLayout.addView(widget.getRootView());
    }

    FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    //widget.setIconResource(theme.getWidgetIconRes(name));
    //widget.setLabel(element.getLabel());

    void bindDateWidget(PaymentCard card) {
        //String label = card.getLang().translateAccountLabel(PaymentInputType.EXPIRY_DATE);
        //widget.setLabel(label);
        //widget.setButton(translate(PAGEKEY_BUTTON_DATE));
        //widget.setIconResource(theme.getWidgetIconRes(PaymentInputType.EXPIRY_DATE));
    }
    
    void bindButtonWidget(PaymentCard card) {
        ButtonWidget widget = (ButtonWidget) getFormWidget(ACTION_BUTTON);

        if (widget == null) {
            return;
        }
        //String buttonLabel = adapter.translate(card.getButton());
        //widget.setLabel(buttonLabel);
    }

    void setLastImeOptions() {
        //List<FormWidget> widgets = createWidgets(card, inflater, parent);
        //FormWidget widget;

        //for (int i = widgets.size(); i > 0; ) {
        //    widget = widgets.get(--i);
        //    if (widget.setLastImeOptionsWidget()) {
        //        break;
        //    }
        // }
        //holder.addWidgets(widgets);        
    }

    static void addInputWidgets(PaymentCardViewHolder holder, LayoutInflater inflater, ViewGroup parent, PaymentCard card) {
        DateWidget dateWidget = null;

        for (InputElement element : card.getInputElements()) {
            if (!card.hasExpiryDate()) {
                addInputWidget(holder, element, inflater, parent);
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                    if (dateWidget == null) {
                        dateWidget = addDateWidget(holder, inflater, parent);
                    }
                    dateWidget.setMonthInputElement(element);
                    break;
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = addDateWidget(holder, inflater, parent);
                    }
                    dateWidget.setYearInputElement(element);
                    break;
                default:
                    addInputWidget(holder, element, inflater, parent);
            }
        }
    }

    static ButtonWidget addButtonWidget(PaymentCardViewHolder holder, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_button, parent, false);
        ButtonWidget widget = new ButtonWidget(ACTION_BUTTON, view);        
        holder.addWidget(widget);
        return widget;
    }
            
    static DateWidget addDateWidget(PaymentCardViewHolder holder, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_input_date, parent, false);
        DateWidget widget = new DateWidget(PaymentInputType.EXPIRY_DATE, view);
        holder.addWidget(widget);
        return widget;
    }

    static FormWidget addInputWidget(PaymentCardViewHolder holder, InputElement element, LayoutInflater inflater, ViewGroup parent) {
        FormWidget widget;
        String name = element.getName();

        switch (element.getType()) {
            case InputElementType.SELECT:
                View view = inflater.inflate(R.layout.widget_input_select, parent, false);
                widget = new SelectInputWidget(name, view, element);
                break;
            case InputElementType.CHECKBOX:
                view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
                widget = new CheckBoxInputWidget(name, view);
                break;
            default:
                view = inflater.inflate(R.layout.widget_input_text, parent, false);
                widget = new TextInputWidget(name, view, element);
        }
        holder.addWidget(widget);
        return widget;
    }




}
