/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import java.util.List;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PresetCard;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.validation.ValidationResult;
import net.optile.payment.validation.Validator;

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

    void showKeyboard(int position) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.showKeyboard();
    }

    void showDialogFragment(int position, DialogFragment dialog, String tag) {
        if (isInvalidPosition(position)) {
            return;
        }
        list.showDialogFragment(dialog, tag);
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

    int getMaxLength(String code, String type) {
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
        result.setMessage(card.getLang().translateError(result.getError()));
        return result;
    }

    PaymentTheme getPaymentTheme() {
        return PaymentUI.getInstance().getPaymentTheme();
    }

    LanguageFile getPageLanguageFile() {
        return list.getPaymentSession().getLang();
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
