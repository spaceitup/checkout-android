/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.ui.widget.FormWidget;
import com.payoneer.checkout.ui.widget.WidgetPresenter;
import com.payoneer.checkout.validation.ValidationResult;

import android.view.View;

/**
 * Class acting as a presenter for the widgets hosted in the List adapter
 */
class CardWidgetPresenter implements WidgetPresenter {

    private final PaymentCardViewHolder holder;
    private final ListAdapter adapter;

    CardWidgetPresenter(PaymentCardViewHolder holder, ListAdapter adapter) {
        this.holder = holder;
        this.adapter = adapter;
    }

    @Override
    public void onActionClicked() {
        adapter.onActionClicked(holder.getAdapterPosition());
    }

    @Override
    public void onHintClicked(String type) {
        adapter.onHintClicked(holder.getAdapterPosition(), type);
    }

    @Override
    public void hideKeyboard() {
        adapter.hideKeyboard(holder.getAdapterPosition());
    }

    @Override
    public void showKeyboard(View view) {
        adapter.showKeyboard(holder.getAdapterPosition(), view);
    }

    @Override
    public boolean requestFocusNextWidget(FormWidget currentWidget) {
        return holder.requestFocusNextWidget(currentWidget);
    }

    @Override
    public int getMaxLength(String code, String type) {
        return adapter.getMaxLength(holder.getAdapterPosition(), code, type);
    }

    @Override
    public ValidationResult validate(String type, String value1, String value2) {
        return adapter.validate(holder.getAdapterPosition(), type, value1, value2);
    }

    @Override
    public void onTextInputChanged(String type, String text) {
        adapter.onTextInputChanged(holder.getAdapterPosition(), type, text);
    }
}
