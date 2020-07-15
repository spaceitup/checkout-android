/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.util.ImageHelper;
import net.optile.payment.util.PaymentUtils;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
public abstract class PaymentCardViewHolder extends RecyclerView.ViewHolder {

    final static String BUTTON_WIDGET = "buttonWidget";
    final static String LABEL_WIDGET = "labelWidget";
    final static float ALPHA_SELECTED = 1f;
    final static float ALPHA_DESELECTED = 0.4f;
    final static int ANIM_DURATION = 200;
    final static int COLUMN_SIZE_LANDSCAPE = 3;
    final static int COLUMN_SIZE_PORTRAIT = 2;
    final static int GROUPSIZE = 4;

    final ViewGroup formLayout;
    final ListAdapter adapter;
    final WidgetPresenter presenter;
    final Map<String, FormWidget> widgets;
    final ImageView logoView;

    PaymentCardViewHolder(ListAdapter adapter, View parent) {
        super(parent);
        this.adapter = adapter;
        this.presenter = new CardWidgetPresenter(this, adapter);
        this.formLayout = parent.findViewById(R.id.layout_form);
        this.widgets = new LinkedHashMap<>();
        this.logoView = parent.findViewById(R.id.image_logo);

        View view = parent.findViewById(R.id.layout_header);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onItemClicked(getAdapterPosition());
            }
        });
    }

    /**
     * Get the FormWidget given the name, i.e. cardNumber or holderName.
     *
     * @param name of the widget to be returned
     * @return the widget or null if it could not be found
     */
    public FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    void addButtonWidget(PaymentTheme theme) {
        FormWidget widget = WidgetInflater.inflateButtonWidget(BUTTON_WIDGET, formLayout, theme);
        addWidget(widget);
    }

    void addElementWidgets(PaymentCard card, PaymentTheme theme) {
        String code = card.getCode();
        List<InputElement> elements = card.getInputElements();
        DateWidget dateWidget = null;
        boolean containsExpiryDate = PaymentUtils.containsExpiryDate(elements);

        for (InputElement element : elements) {
            String name = element.getName();

            if (adapter.isHidden(code, name)) {
                continue;
            }
            if (!containsExpiryDate) {
                addWidget(WidgetInflater.inflateElementWidget(element, formLayout, theme));
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = WidgetInflater.inflateDateWidget(PaymentInputType.EXPIRY_DATE, formLayout, theme);
                        addWidget(dateWidget);
                    }
                    break;
                default:
                    addWidget(WidgetInflater.inflateElementWidget(element, formLayout, theme));
            }
        }
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

    void expand(boolean expand) {
        formLayout.setVisibility(expand ? View.VISIBLE : View.GONE);

        for (FormWidget widget : widgets.values()) {
            widget.setValidation();
        }
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
            if (widget instanceof SelectWidget) {
                bindSelectWidget((SelectWidget) widget, element);
            } else if (widget instanceof TextInputWidget) {
                bindTextInputWidget((TextInputWidget) widget, card.getCode(), element);
            }
        }
    }

    void bindAccountMask(TextView title, TextView subtitle, AccountMask mask, String method) {
        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                title.setText(mask.getNumber());
                String date = PaymentUtils.getExpiryDateString(mask);
                if (date != null) {
                    subtitle.setVisibility(View.VISIBLE);
                    subtitle.setText(date);
                }
                break;
            default:
                title.setText(mask.getDisplayLabel());
        }
    }

    void bindSelectWidget(SelectWidget widget, InputElement element) {
        widget.setLabel(element.getLabel());
        widget.setSelectOptions(element.getOptions());
    }

    void bindTextInputWidget(TextInputWidget widget, String code, InputElement element) {
        String name = element.getName();
        boolean visible = Localization.hasAccountHint(code, name);
        int maxLength = adapter.getMaxLength(code, name);

        widget.setHint(visible);
        widget.setLabel(Localization.translateAccountLabel(code, name));
        widget.setInputElement(maxLength, element);
    }

    void bindDateWidget(PaymentCard card) {
        String name = PaymentInputType.EXPIRY_DATE;
        DateWidget widget = (DateWidget) getFormWidget(name);

        if (widget == null) {
            return;
        }
        widget.setLabel(Localization.translateAccountLabel(card.getCode(), name));
        widget.setInputElements(card.getInputElement(PaymentInputType.EXPIRY_MONTH),
            card.getInputElement(PaymentInputType.EXPIRY_YEAR));
    }

    void bindButtonWidget(PaymentCard card) {
        ButtonWidget widget = (ButtonWidget) getFormWidget(BUTTON_WIDGET);

        if (widget == null) {
            return;
        }
        widget.setLabel(Localization.translate(card.getCode(), card.getButton()));
    }

    void bindLogoView(String name, URL url) {
        if (name == null || url == null) {
            return;
        }
        ImageHelper.getInstance().loadImage(logoView, url);
    }

    void setLastImeOptions() {
        List<String> keys = new ArrayList<>(widgets.keySet());
        Collections.reverse(keys);

        for (String key : keys) {
            FormWidget widget = widgets.get(key);
            if (widget != null && widget.setLastImeOptionsWidget()) {
                break;
            }
        }
    }
}
