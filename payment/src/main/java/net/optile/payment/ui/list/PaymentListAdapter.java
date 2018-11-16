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

import java.util.List;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> items;
    private final PaymentList list;

    PaymentListAdapter(PaymentList list, List<ListItem> items) {
        this.list = list;
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItem item = getItemWithViewType(viewType);
        ViewHolder holder = null;

        if (item instanceof NetworkCardItem) {
            holder = NetworkCardViewHolder.createInstance(this, (NetworkCardItem) item, inflater, parent);
        } else if (item instanceof AccountCardItem) {
            holder = AccountCardViewHolder.createInstance(this, (AccountCardItem) item, inflater, parent);
        } else if (item instanceof HeaderItem) {
            holder = HeaderViewHolder.createInstance(inflater, parent);
        }
        return holder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).onBind((HeaderItem) item);
        } else if (holder instanceof AccountCardViewHolder) {
            AccountCardViewHolder ah = (AccountCardViewHolder) holder;
            ah.onBind((AccountCardItem) item);
            ah.expand(list.getSelected() == position);
        } else if (holder instanceof NetworkCardViewHolder) {
            NetworkCardViewHolder nh = (NetworkCardViewHolder) holder;
            nh.onBind((NetworkCardItem) item);
            nh.expand(list.getSelected() == position);
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
        if (!isValidPosition(position)) {
            return;
        }
        list.onItemClicked(items.get(position), position);
    }

    void hideKeyboard(int position) {
        if (!isValidPosition(position)) {
            return;
        }
        list.hideKeyboard();
    }

    void showKeyboard(int position) {
        if (!isValidPosition(position)) {
            return;
        }
        list.showKeyboard();
    }

    void showDialogFragment(int position, DialogFragment dialog, String tag) {
        if (!isValidPosition(position)) {
            return;
        }
        list.showDialogFragment(dialog, tag);
    }

    void onActionClicked(int position) {
        if (!isValidPosition(position)) {
            return;
        }
        ListItem item = items.get(position);
        //list.onActionClicked(, position);
    }

    ValidationResult validate(int position, String type, String value1, String value2) {
        if (!isValidPosition(position)) {
            return null;
        }
        //NetworkCard item = items.get(position);
        //return list.validate(item.getActiveNetworkItem(), type, value1, value2);
        return null;
    }

    PaymentTheme getPaymentTheme() {
        return PaymentUI.getInstance().getPaymentTheme();
    }

    LanguageFile getLanguageFile() {
        return list.getPaymentSession().getLang();
    }

    /**
     * Get the ListItem at the given index
     *
     * @param index index of the NetworkCard
     * @return ListItem given the index or null if not found
     */
    ListItem getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the list item with its type matching the viewType
     *
     * @param type type of the view
     * @return ListItem with the same type or null if not found
     */
    private ListItem getItemWithViewType(int viewType) {

        for (ListItem item : items) {
            if (item.viewType == viewType) {
                return item;
            }
        }
        return null;
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position < items.size();
    }
}
