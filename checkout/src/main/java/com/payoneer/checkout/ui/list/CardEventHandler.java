/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import com.payoneer.checkout.localization.Localization;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.widget.FormWidget;
import com.payoneer.checkout.ui.widget.WidgetPresenter;
import com.payoneer.checkout.validation.ValidationResult;
import com.payoneer.checkout.validation.Validator;

import android.view.View;

/**
 * Class handling events originating from payment cards and widgets
 */
class CardEventHandler implements WidgetPresenter {

    private final PaymentCardViewHolder holder;
    private final ListAdapter adapter;
    private final PaymentCardListener listener;
    
    CardEventHandler(PaymentCardViewHolder holder, ListAdapter adapter, PaymentCardListener listener) {
        this.holder = holder;
        this.adapter = adapter;
        this.listener = listener;
    }

    @Override
    public boolean requestFocusNextWidget(FormWidget currentWidget) {
        if (holder.hasValidPosition()) {
            return holder.requestFocusNextWidget(currentWidget);
        }
        return false;
    }
    
    @Override
    public void onActionClicked() {
        if (holder.hasValidPosition()) {
            boolean error = false;
            for (FormWidget widget : holder.getFormWidgets().values()) {
                if (!widget.validate()) {
                    error = true;
                }
                widget.clearFocus();
            }
            if (!error) {
                listener.onActionClicked(holder.getPaymentCard(), holder.getFormWidgets());
            }
        }
    }

    @Override
    public void onHintClicked(String type) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();
            listener.onHintClicked(card.getCode(), type);
        }
    }

    @Override
    public void hideKeyboard() {
        if (holder.hasValidPosition()) {
            listener.hideKeyboard();
        }
    }

    @Override
    public void showKeyboard(View view) {
        if (holder.hasValidPosition()) {
            listener.showKeyboard(view);
        }
    }

    @Override
    public int getMaxInputLength(String code, String type) {
        if (holder.hasValidPosition()) {
            Validator validator = Validator.getInstance();
            return validator.getMaxInputLength(code, type);
        }
        return -1;
    }

    @Override
    public ValidationResult validate(String type, String value1, String value2) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();
            Validator validator = Validator.getInstance();
            ValidationResult result = validator.validate(card.getPaymentMethod(), card.getCode(), type, value1, value2);

            if (!result.isError()) {
                return result;
            }
            result.setMessage(Localization.translateError(card.getCode(), result.getError()));
            return result;
        }
        return null;
    }

    @Override
    public void onTextInputChanged(String type, String text) {
        if (holder.hasValidPosition()) {
            PaymentCard card = holder.getPaymentCard();

            if (card.onTextInputChanged(type, text)) {
                adapter.notifyItemChanged(holder.getAdapterPosition());
            }
        }
    }

    void onDeleteClicked() {
        if (holder.hasValidPosition()) {
            listener.onDeleteClicked(holder.getPaymentCard());
        }
    }
    
    void onCardClicked() {
        if (holder.hasValidPosition()) {
            listener.onCardClicked(holder.getAdapterPosition());
        }
    }
    
    boolean isInputTypeHidden(String code, String type) {
        if (holder.hasValidPosition()) {
            Validator validator = Validator.getInstance();
            return validator.isHidden(code, type);
        }
        return false;
    }
}
