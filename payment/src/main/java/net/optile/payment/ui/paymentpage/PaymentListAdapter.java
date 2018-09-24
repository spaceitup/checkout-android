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

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import net.optile.payment.R;

import com.bumptech.glide.Glide;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<PaymentListViewHolder> {

    private final List<PaymentListItem> items;

    private OnItemListener listener;

    private PaymentPageActivity activity;
    
    /** 
     * Construct a new PaymentListAdapter
     */
    PaymentListAdapter(final PaymentPageActivity activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull PaymentListViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_paymentpage, parent, false);

        PaymentListItem item = getItemWithViewType(viewType);
        PaymentListViewHolder holder = new PaymentListViewHolder(this, view);
        addInputViewsToHolder(holder, inflater, item);
        return holder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull PaymentListViewHolder holder, int position) {
        PaymentItem item = items.get(position).item;
        URL logoUrl = item.getLink("logo");
        holder.title.setText(item.getLabel());

        if (logoUrl != null) {
            Glide.with(activity).asBitmap().load(logoUrl.toString()).into(holder.logo);
        }
    }

    /**
     * Set the item listener in this adapter
     *
     * @param listener the listener interested on events from the item
     */
    public void setListener(final OnItemListener listener) {
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
    public int getItemViewType(final int position) {
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
    public void setItems(final List<PaymentListItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    void handleOnClick(final int position) {
        if (listener != null) {
            PaymentListItem item = items.get(position);
            listener.onItemClicked(item, position);
        }
    }

    /**
     * Get the PaymentListItem at the given index
     *
     * @param index index of the PaymentListItem
     * @return      PaymentListItem given the index or null if not found
     */
    private PaymentListItem getItemFromIndex(final int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the PaymentListItem with its type matching the viewType
     *
     * @param type type of the view
     * @return     PaymentListItem with the same type or null if not found
     */
    private PaymentListItem getItemWithViewType(final int type) {

        for (PaymentListItem item : items) {
            if (item.type == type) {
                return item;
            }
        }
        return null;
    }

    private void addInputElementsToHolder(final PaymentListViewHolder holder, final LayoutInflater inflater, final PaymentListItem item) {
        List<InputElement> elements = item.getSortedInputElements();
        if (elements == null) {
            return;
        }
        String type; 
        for (InputElement element : elements) {
            switch (element.getType()) {
            case InputElementType.STRING:
                addStringElementToHolder(holder, inflater, element);
                break;
            case InputElementType.NUMERIC:
                addNumericElementToHolder(holder, inflater, element);
                break;
            case InputElementType.INTEGER:
                addIntegerElementToHolder(holder, inflater, element);
                break;
            case InputElementType.SELECT:
                addSelectElementToHolder(holder, inflater, element);
            case InputElementType.CHECKBOX:
                addCheckboxlementToHolder(holder, inflater, element);                
            }
        }
    }

    private void addStringElementToHolder(final PaymentListViewHolder holder, final LayoutInflater inflater, final InputElement element) {

        View view = inflater.inflate(R.layout.widget_input_string, parent, false);
        
    }

    /**
     * The item listener
     */
    public interface OnItemListener {
        void onItemClicked(PaymentListItem item, int position);
        void onActionClicked(PaymentListItem item, int position);
    }
}
