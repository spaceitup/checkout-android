/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.list;

import static com.payoneer.mrs.payment.core.PaymentInputType.EXPIRY_DATE;
import static com.payoneer.mrs.payment.core.PaymentInputType.EXPIRY_MONTH;
import static com.payoneer.mrs.payment.core.PaymentInputType.EXPIRY_YEAR;
import static com.payoneer.mrs.payment.core.PaymentInputType.VERIFICATION_CODE;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.model.AccountMask;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.model.PaymentMethod;
import com.payoneer.mrs.payment.ui.model.PaymentCard;
import com.payoneer.mrs.payment.ui.widget.ButtonWidget;
import com.payoneer.mrs.payment.ui.widget.DateWidget;
import com.payoneer.mrs.payment.ui.widget.FormWidget;
import com.payoneer.mrs.payment.ui.widget.SelectWidget;
import com.payoneer.mrs.payment.ui.widget.TextInputWidget;
import com.payoneer.mrs.payment.ui.widget.VerificationCodeWidget;
import com.payoneer.mrs.payment.ui.widget.WidgetInflater;
import com.payoneer.mrs.payment.ui.widget.WidgetPresenter;
import com.payoneer.mrs.payment.util.NetworkLogoLoader;
import com.payoneer.mrs.payment.util.PaymentUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The PaymentCardViewHolder holding the header and input widgets
 */
public abstract class PaymentCardViewHolder extends RecyclerView.ViewHolder {

    final static String BUTTON_WIDGET = "buttonWidget";
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
    final ImageView cardLogoView;

    PaymentCardViewHolder(ListAdapter adapter, View parent) {
        super(parent);
        this.adapter = adapter;
        this.presenter = new CardWidgetPresenter(this, adapter);
        this.formLayout = parent.findViewById(R.id.layout_form);
        this.widgets = new LinkedHashMap<>();
        this.cardLogoView = parent.findViewById(R.id.image_logo);

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

    void addButtonWidget() {
        FormWidget widget = WidgetInflater.inflateButtonWidget(BUTTON_WIDGET, formLayout);
        addWidget(widget);
    }

    void addElementWidgets(PaymentCard card) {
        String code = card.getCode();
        List<InputElement> elements = card.getInputElements();
        boolean elementsContainExpiryDate = PaymentUtils.containsExpiryDate(elements);

        for (InputElement element : elements) {
            String name = element.getName();
            if (adapter.isHidden(code, name)) {
                continue;
            }
            switch (element.getName()) {
                case VERIFICATION_CODE:
                    addWidget(WidgetInflater.inflateVerificationCodeWidget(VERIFICATION_CODE, formLayout));
                    break;
                case EXPIRY_MONTH:
                case EXPIRY_YEAR:
                    if (!elementsContainExpiryDate) {
                        addWidget(WidgetInflater.inflateElementWidget(element, formLayout));
                    } else if (!widgets.containsKey(EXPIRY_DATE)) {
                        addWidget(WidgetInflater.inflateDateWidget(EXPIRY_DATE, formLayout));
                    }
                    break;
                default:
                    addWidget(WidgetInflater.inflateElementWidget(element, formLayout));
            }
        }
    }

    boolean requestFocusNextWidget(FormWidget currentWidget) {
        boolean requestFocus = false;
        for (Map.Entry<String, FormWidget> entry : widgets.entrySet()) {
            FormWidget widget = entry.getValue();
            if (requestFocus) {
                if (widget.requestFocus()) {
                    return true;
                }
            } else {
                requestFocus = (widget == currentWidget);
            }
        }
        return false;
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
        boolean focusRequested = false;

        for (FormWidget widget : widgets.values()) {
            widget.setValidation();
            if (expand && !focusRequested) {
                focusRequested = widget.requestFocus();
            }
        }
    }

    void onBind(PaymentCard paymentCard) {
        for (Map.Entry<String, FormWidget> entry : widgets.entrySet()) {
            FormWidget widget = entry.getValue();

            switch (entry.getKey()) {
                case BUTTON_WIDGET:
                    bindButtonWidget((ButtonWidget) widget, paymentCard);
                    break;
                case VERIFICATION_CODE:
                    bindVerificationCodeWidget((VerificationCodeWidget) widget, paymentCard);
                    break;
                case EXPIRY_DATE:
                    bindDateWidget((DateWidget) widget, paymentCard);
                    break;
                default:
                    bindElementWidget(widget, paymentCard);
            }
        }
    }

    void bindButtonWidget(ButtonWidget widget, PaymentCard card) {
        widget.onBind(card.getCode(), card.getButton());
    }

    void bindVerificationCodeWidget(VerificationCodeWidget widget, PaymentCard card) {
        InputElement element = card.getInputElement(VERIFICATION_CODE);
        widget.onBind(card.getCode(), element);
    }

    void bindDateWidget(DateWidget widget, PaymentCard card) {
        InputElement month = card.getInputElement(EXPIRY_MONTH);
        InputElement year = card.getInputElement(EXPIRY_YEAR);
        widget.onBind(card.getCode(), month, year);
    }

    void bindElementWidget(FormWidget widget, PaymentCard card) {
        InputElement element = card.getInputElement(widget.getName());
        String code = card.getCode();

        if (widget instanceof SelectWidget) {
            ((SelectWidget) widget).onBind(code, element);
        } else if (widget instanceof TextInputWidget) {
            ((TextInputWidget) widget).onBind(code, element);
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

    void bindCardLogo(int logoResId) {
        cardLogoView.setImageResource(logoResId);
    }

    void bindCardLogo(String networkCode, URL url) {
        if (networkCode == null || url == null) {
            return;
        }
        NetworkLogoLoader.loadNetworkLogo(cardLogoView, networkCode, url);
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
