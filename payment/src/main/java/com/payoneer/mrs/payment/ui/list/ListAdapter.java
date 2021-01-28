/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.list;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.ui.model.AccountCard;
import com.payoneer.mrs.payment.ui.model.NetworkCard;
import com.payoneer.mrs.payment.ui.model.PaymentCard;
import com.payoneer.mrs.payment.ui.model.PresetCard;
import com.payoneer.mrs.payment.validation.ValidationResult;
import com.payoneer.mrs.payment.validation.Validator;

/**
 * The ListAdapter handling the items in this RecyclerView list
 */
final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> items;
    private final PaymentList list;

    ListAdapter(PaymentList list, List<ListItem> items) {
        this.list = list;
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItem item = getItemWithViewType(viewType);
        PaymentCard card = item != null ? item.getPaymentCard() : null;

        if (card instanceof NetworkCard) {
            return NetworkCardViewHolder.createInstance(this, (NetworkCard) card, parent);
        } else if (card instanceof AccountCard) {
            return AccountCardViewHolder.createInstance(this, (AccountCard) card, parent);
        } else if (card instanceof PresetCard) {
            return PresetCardViewHolder.createInstance(this, (PresetCard) card, parent);
        } else {
            return HeaderViewHolder.createInstance(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);

        if (item.hasPaymentCard()) {
            PaymentCardViewHolder ph = (PaymentCardViewHolder) holder;
            ph.onBind(item.getPaymentCard());
            ph.expand(list.getSelected() == position);
        } else {
            ((HeaderViewHolder) holder).onBind((HeaderItem) item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemViewType(int position) {
        return items.get(position).viewType;
    }

    void onItemClicked(int position) {

        if (isInvalidPosition(position)) {
            return;
        }
        list.onItemClicked(position);
    }

    void hideKeyboard(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.hideKeyboard();
    }

    void showKeyboard(int position, View view) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.showKeyboard(view);
    }

    void onActionClicked(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.onActionClicked(position);
    }

    void onHintClicked(int position, String type) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.onHintClicked(position, type);
    }

    Context getContext() {
        return list.getContext();
    }

    void onTextInputChanged(int position, String type, String text) {
        if (isInvalidPosition(position)) {
            return;
        }
        ListItem item = items.get(position);

        if (item.hasPaymentCard()) {
            PaymentCard card = item.getPaymentCard();

            if (card.onTextInputChanged(type, text)) {
                notifyItemChanged(position);
            }
        }
    }

    boolean isHidden(String code, String type) {
        Validator validator = list.getPaymentSession().getValidator();
        return validator.isHidden(code, type);
    }

    int getMaxLength(int position, String code, String type) {
        if (isInvalidPosition(position)) {
            return -1;
        }
        Validator validator = list.getPaymentSession().getValidator();
        return validator.getMaxLength(code, type);
    }

    ValidationResult validate(int position, String type, String value1, String value2) {
        if (isInvalidPosition(position)) {
            return null;
        }
        ListItem item = items.get(position);

        if (!item.hasPaymentCard()) {
            return null;
        }
        PaymentCard card = item.getPaymentCard();
        Validator validator = list.getPaymentSession().getValidator();
        ValidationResult result = validator.validate(card.getPaymentMethod(), card.getCode(), type, value1, value2);

        if (!result.isError()) {
            return result;
        }
        result.setMessage(Localization.translateError(card.getCode(), result.getError()));
        return result;
    }

    private ListItem getItemWithViewType(int viewType) {

        for (ListItem item : items) {
            if (item.viewType == viewType) {
                return item;
            }
        }
        return null;
    }

    private boolean isInvalidPosition(int position) {
        return (position < 0) || (position >= items.size());
    }
}
