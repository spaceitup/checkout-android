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
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
abstract class PaymentCardViewHolder extends RecyclerView.ViewHolder {
    final ViewGroup formLayout;
    final ListAdapter adapter;
    final WidgetPresenter presenter;
    final Map<String, FormWidget> widgets;

    PaymentCardViewHolder(ListAdapter adapter, View parent) {
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

        this.presenter = new WidgetPresenter() {
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

    static void addButtonWidget(PaymentCardViewHolder holder, PaymentTheme theme) {
        FormWidget widget = WidgetInflater.inflateButtonWidget(PaymentInputType.ACTION_BUTTON, holder.formLayout, theme);
        holder.addWidget(widget);
    }

    static void addElementWidgets(PaymentCardViewHolder holder, List<InputElement> elements, PaymentTheme theme) {
        DateWidget dateWidget = null;
        boolean containsExpiryDate = PaymentUtils.containsExpiryDate(elements);
        ViewGroup parent = holder.formLayout;
        containsExpiryDate = false;
        
        for (InputElement element : elements) {
            if (!containsExpiryDate) {
                holder.addWidget(WidgetInflater.inflateElementWidget(element, parent, theme));
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                    if (dateWidget == null) {
                        dateWidget = WidgetInflater.inflateDateWidget(PaymentInputType.EXPIRY_DATE, parent, theme);
                        holder.addWidget(dateWidget);
                    }
                    dateWidget.setMonthInputElement(element);
                    break;
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = WidgetInflater.inflateDateWidget(PaymentInputType.EXPIRY_DATE, parent, theme);
                        holder.addWidget(dateWidget);
                    }
                    dateWidget.setYearInputElement(element);
                    break;
                default:
                    holder.addWidget(WidgetInflater.inflateElementWidget(element, parent, theme));
            }
        }
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
        bindElementWidgets(paymentCard);
        bindDateWidget(paymentCard);
        bindButtonWidget(paymentCard);
    }

    void bindElementWidgets(PaymentCard card) {

        FormWidget widget;
        for (InputElement element : card.getInputElements()) {
            widget = getFormWidget(element.getName());
            if (widget == null) {
                continue;
            }
            bindElementWidget(widget, element);
        }
    }

    void bindElementWidget(FormWidget widget, InputElement element) {
        bindIconResource(widget);
        widget.setLabel(element.getLabel());

        if (widget instanceof SelectWidget) {
            ((SelectWidget) widget).setSelectOptions(element.getOptions());
        } else if (widget instanceof TextInputWidget) {
            ((TextInputWidget) widget).setInputType(element.getType());
        }
    }

    void bindIconResource(FormWidget widget) {
        WidgetParameters params = adapter.getPaymentTheme().getWidgetParameters();
        widget.setIconResource(params.getInputTypeIcon(widget.getName()));
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
        List<String> keys = new ArrayList<>(widgets.keySet());
        Collections.reverse(keys);

        for (String key : keys) {
            FormWidget widget = widgets.get(key);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
    }
}
