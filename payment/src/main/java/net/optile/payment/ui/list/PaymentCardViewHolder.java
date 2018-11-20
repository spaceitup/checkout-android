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

package net.optile.payment.ui.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxInputWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectInputWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
class PaymentCardViewHolder extends RecyclerView.ViewHolder {

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

    static void addInputWidgets(PaymentCardViewHolder holder, LayoutInflater inflater, ViewGroup parent, PaymentCard card) {
        DateWidget dateWidget = null;

        for (InputElement element : card.getInputElements()) {
            if (!card.hasExpiryDate()) {
                holder.addWidget(createInputWidget(element, inflater, parent));
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                    if (dateWidget == null) {
                        dateWidget = createDateWidget(inflater, parent);
                        holder.addWidget(dateWidget);
                    }
                    dateWidget.setMonthInputElement(element);
                    break;
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = createDateWidget(inflater, parent);
                        holder.addWidget(dateWidget);
                    }
                    dateWidget.setYearInputElement(element);
                    break;
                default:
                    holder.addWidget(createInputWidget(element, inflater, parent));
            }
        }
    }

    static FormWidget createInputWidget(InputElement element, LayoutInflater inflater, ViewGroup parent) {
        FormWidget widget;
        String name = element.getName();

        switch (element.getType()) {
            case InputElementType.SELECT:
                View view = inflater.inflate(R.layout.widget_input_select, parent, false);
                return new SelectInputWidget(name, view);
            case InputElementType.CHECKBOX:
                view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
                return new CheckBoxInputWidget(name, view);
            default:
                view = inflater.inflate(R.layout.widget_input_text, parent, false);
                return new TextInputWidget(name, view);
        }
    }

    static ButtonWidget createButtonWidget(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_button, parent, false);
        return new ButtonWidget(PaymentInputType.ACTION_BUTTON, view);
    }

    static DateWidget createDateWidget(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_input_date, parent, false);
        return new DateWidget(PaymentInputType.EXPIRY_DATE, view);
    }

    void expand(boolean expand) {
        formLayout.setVisibility(expand ? View.VISIBLE : View.GONE);
    }

    void addWidget(FormWidget widget) {
        String name = widget.getName();

        if (widgets.containsKey(name)) {
            return;
        }
        widget.setPresenter(presenter);
        widgets.put(name, widget);
        formLayout.addView(widget.getRootView());
    }

    FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    void onBind(PaymentCard paymentCard) {
        bindInputWidgets(paymentCard);
        bindDateWidget(paymentCard);
        bindButtonWidget(paymentCard);
    }

    void bindInputWidgets(PaymentCard card) {

        FormWidget widget;
        for (InputElement element : card.getInputElements()) {
            widget = getFormWidget(element.getName());
            if (widget == null) {
                continue;
            }
            bindInputWidget(widget, element);
        }
    }

    private void bindInputWidget(FormWidget widget, InputElement element) {
        bindIconResource(widget);
        widget.setLabel(element.getLabel());

        if (widget instanceof SelectInputWidget) {
            ((SelectInputWidget) widget).setSelectOptions(element.getOptions());
        } else if (widget instanceof TextInputWidget) {
            ((TextInputWidget) widget).setInputType(element.getType());
        }
    }

    void bindIconResource(FormWidget widget) {
        PaymentTheme theme = adapter.getPaymentTheme();
        widget.setIconResource(theme.getWidgetIconRes(widget.getName()));
    }

    void bindDateWidget(PaymentCard card) {
        String name = PaymentInputType.EXPIRY_DATE;
        DateWidget widget = (DateWidget) getFormWidget(name);

        if (widget == null) {
            return;
        }
        LanguageFile pageLang = adapter.getPageLanguageFile();
        bindIconResource(widget);
        widget.setLabel(card.getLang().translateAccount(name));
        widget.setDialogButtonLabel(pageLang.translate(LanguageFile.KEY_BUTTON_DATE));
    }

    void bindButtonWidget(PaymentCard card) {
        String name = PaymentInputType.ACTION_BUTTON;
        ButtonWidget widget = (ButtonWidget) getFormWidget(name);

        if (widget == null) {
            return;
        }
        LanguageFile pageLang = adapter.getPageLanguageFile();
        widget.setButtonLabel(pageLang.translate(card.getButton()));
    }

    void setLastImeOptions() {
        List<String> keys = new ArrayList<String>(widgets.keySet());
        Collections.reverse(keys);

        for (String key : keys) {
            FormWidget widget = widgets.get(key);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
    }
}
