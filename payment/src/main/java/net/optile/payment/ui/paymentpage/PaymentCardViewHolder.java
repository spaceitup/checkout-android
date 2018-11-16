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
import net.optile.payment.core.PaymentInputType;

/**
 * The PaymentCardViewHolder holding the widgets for a PaymentCard like Account or Network 
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
        this.widgets = new HashMap<>();

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

    void addWidgets(List<FormWidget> items) {

        for (FormWidget widget : items) {
            widget.setPresenter(presenter);
            widgets.put(widget.getName(), widget);
            formLayout.addView(widget.getRootView());
        }
    }

    FormWidget getFormWidget(String name) {
        return widgets.get(name);
    }

    void bindRegistrationWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) holder.getFormWidget(PaymentInputType.AUTO_REGISTRATION);
        widget.setRegistrationType(item.getRegistration());
    }

    void bindRecurrenceWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) holder.getFormWidget(PaymentInputType.ALLOW_RECURRENCE);
        widget.setRegistrationType(item.getRecurrence());
    }

    void bindButtonWidget(PaymentNetwork network) {
        ButtonWidget widget = (ButtonWidget) holder.getFormWidget(PaymentListAdapter.WIDGET_BUTTON);
        String buttonLabel = adapter.translate(network.getButton());
        widget.setLabel(buttonLabel);
    }
}
