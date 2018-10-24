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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.validation.ValidationResult;
import android.support.v4.app.DialogFragment;

/**
 * The PaymentListViewHolder holding all Views for easy access
 */
class PaymentListViewHolder extends RecyclerView.ViewHolder {

    final TextView title;

    final ImageView logo;

    final ViewGroup formLayout;

    final PaymentListAdapter adapter;

    final FormWidget.WidgetPresenter presenter;

    final Map<String, FormWidget> widgets;


    PaymentListViewHolder(PaymentListAdapter adapter, View parent) {
        super(parent);
        this.adapter = adapter;
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
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
}
