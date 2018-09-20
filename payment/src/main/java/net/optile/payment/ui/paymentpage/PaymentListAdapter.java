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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.ArrayList;
import net.optile.payment.R;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<PaymentListViewHolder> {

    private final List<PaymentListItem> items;

    private OnItemListener listener;

    /** 
     * Construct a new PaymentListAdapter
     */
    PaymentListAdapter() {
        this.items = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_paymentpage, parent, false);
        return new PaymentListViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull PaymentListViewHolder holder, int position) {
        PaymentListItem item = items.get(position);
        holder.title.setText(item.getLabel());
    }

    /**
     * Set the item listener in this adapter
     *
     * @param listener the listener interested on events from the item
     */
    public void setListener(OnItemListener listener) {
        this.listener = listener;
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
        return items.get(position).type;
    }
    
    /**
     * Clear all items from this adapter
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * Set new items in this adapter and notify any
     * listeners.
     *
     * @param newItems list of PaymentListItems that should be set
     */
    public void setItems(List<PaymentListItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
    
    /**
     * Get the PaymentListItem at the given index
     *
     * @param index index of the PaymentListItem
     * @return      PaymentListItem given the index or null if not found
     */
    private PaymentListItem getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the PaymentListItem with its type matching the viewType
     *
     * @param type type of the view
     * @return     PaymentListItem with the same type or null if not found
     */
    private PaymentListItem getItemWithViewType(int type) {

        for (PaymentListItem item : items) {
            if (item.type == type) {
                return item;
            }
        }
        return null;
    }

    private void handleOnClick(int position) {

        if (listener != null) {
            PaymentListItem item = items.get(position);
            listener.onItemClicked(item, position);
        }
    }

    /**
     * The item listener
     */
    public interface OnItemListener {
        void onItemClicked(PaymentListItem item, int position);
        void onActionClicked(PaymentListItem item, int position);
    }
}
