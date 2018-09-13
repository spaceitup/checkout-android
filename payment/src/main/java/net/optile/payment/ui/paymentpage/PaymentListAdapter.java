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

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import net.optile.payment.R;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<PaymentListViewHolder> {

    private final static String TAG = "payment_PaymentListAdapter";

    /**
     * The list of items in this adapter
     */
    private List<PaymentListItem> items;

    /**
     * The PaymentPageActivity
     */
    private PaymentPageActivity activity;

    /**
     * The item listener
     */
    private OnItemListener listener;

    /** 
     * Construct a new PaymentListAdapter
     * 
     * @param activity 
     * @param items 
     * 
     * @return 
     */
    PaymentListAdapter(PaymentPageActivity activity, List<PaymentListItem> items) {
        this.activity = activity;
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_paymentpage, parent, false);
        return new PaymentListViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(PaymentListViewHolder holder, int position) {
        PaymentListItem item = items.get(position);
    }

    /**
     * Set the item listener in this adapter
     *
     * @param listener
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
     * Clear
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * Get the PaymentListItem at the given index
     *
     * @param index the index of the PaymentListItem
     * @return the PaymentListItem given the index or null if not found
     */
    private PaymentListItem getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the PaymentListItem with its type matching the viewType
     *
     * @param type the type of the view
     * @return the PaymentListItem with the same type or null if not found
     */
    private PaymentListItem getItemWithViewType(int type) {

        for (PaymentListItem item : items) {
            if (item.type == type) {
                return item;
            }
        }
        return null;
    }

    /**
     * Set new items in this adapter and notify any
     * listeners.
     *
     * @param newItems
     */
    public void setItems(List<PaymentListItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    /**
     * handleOnClick
     *
     * @param position
     */
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
