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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.validate.Validator;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxInputWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.IntegerInputWidget;
import net.optile.payment.ui.widget.NumericInputWidget;
import net.optile.payment.ui.widget.SelectInputWidget;
import net.optile.payment.ui.widget.StringInputWidget;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<PaymentListViewHolder> {

    private final static String TAG = "pay_PaymentListAdapter";
    private final static String BUTTON_WIDGET = "ButtonWidget";

    private final List<PaymentGroup> items;

    private final PaymentList list;

    private OnItemListener listener;

    PaymentListAdapter(PaymentList list) {
        this.list = list;
        this.items = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull
    PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_paymentpage, parent, false);

        PaymentListViewHolder holder = new PaymentListViewHolder(this, view);
        PaymentGroup group = getGroupWithViewType(viewType);

        if (group != null) {
            addWidgetsToHolder(holder, group, inflater, parent);
        }
        return holder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull PaymentListViewHolder holder, int position) {
        PaymentGroup group = items.get(position);
        URL logoUrl = group.getLink("logo");
        holder.title.setText(group.getLabel());

        if (logoUrl != null) {
            Glide.with(list.getContext()).asBitmap().load(logoUrl.toString()).into(holder.logo);
        }

        String buttonLabel = list.getPaymentSession().translate(group.getButton(), null);
        ButtonWidget widget = (ButtonWidget) holder.getFormWidget(BUTTON_WIDGET);
        if (widget != null) {
            if (TextUtils.isEmpty(buttonLabel)) {
                widget.setVisible(false);
            } else {
                widget.setLabel(buttonLabel);
                widget.setVisible(true);
            }
        }
        holder.expand(position == list.getSelected());
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
     * Set the payment groups in this adapter
     *
     * @param groups the list of PaymentGroup objects
     */
    public void setPaymentGroups(List<PaymentGroup> groups) {
        items.clear();
        items.addAll(groups);
        notifyDataSetChanged();
    }

    void onItemClicked(int position) {
        if (listener != null) {
            PaymentGroup item = items.get(position);
            listener.onItemClicked(item, position);
        }
    }

    void onActionClicked(int position) {
        if (listener != null) {
            PaymentGroup item = items.get(position);
            listener.onActionClicked(item, position);
        }
    }

    String translateValidateError(int position, String error) {
        PaymentItem item = items.get(position).getActivePaymentItem();        
        return item.translateError(error);
    }

    Validator getValidator(int position) {
        return PaymentUI.getInstance().getValidator();
    }
    
    /**
     * Get the PaymentGroup at the given index
     *
     * @param index index of the PaymentGroup
     * @return PaymentGroup given the index or null if not found
     */
    PaymentGroup getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the group with its type matching the viewType
     *
     * @param type type of the view
     * @return PaymentGroup with the same type or null if not found
     */
    private PaymentGroup getGroupWithViewType(int type) {

        for (PaymentGroup group : items) {
            if (group.type == type) {
                return group;
            }
        }
        return null;
    }

    private List<FormWidget> createWidgets(List<InputElement> elements, LayoutInflater inflater, ViewGroup parent) {
        List<FormWidget> widgets = new ArrayList<>();
        for (InputElement element : elements) {
            widgets.add(createInputWidget(element, inflater, parent));
        }
        View view = inflater.inflate(R.layout.widget_button, parent, false);
        widgets.add(new ButtonWidget(BUTTON_WIDGET, view));
        return widgets;
    }

    private FormWidget createInputWidget(InputElement element, LayoutInflater inflater, ViewGroup parent) {
        FormWidget widget;
        String name = element.getName();
        String type = element.getType();
        
        switch (element.getType()) {
        case InputElementType.NUMERIC:
            View view = inflater.inflate(R.layout.widget_input_text, parent, false);
            widget = new NumericInputWidget(name, view, element);
            break;
        case InputElementType.INTEGER:
            view = inflater.inflate(R.layout.widget_input_text, parent, false);
            widget = new IntegerInputWidget(name, view, element);
            break;
        case InputElementType.SELECT:
            view = inflater.inflate(R.layout.widget_input_select, parent, false);
            widget = new SelectInputWidget(name, view, element);
            break;
        case InputElementType.CHECKBOX:
            view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
            widget = new CheckBoxInputWidget(name, view, element);
            break;
        case InputElementType.STRING:
        default:
            view = inflater.inflate(R.layout.widget_input_text, parent, false);
            widget = new StringInputWidget(name, view, element);
        }
        PaymentTheme theme = PaymentUI.getInstance().getPaymentTheme();
        widget.setIconResource(theme.getWidgetIconRes(name));
        return widget;
    }
    
    private void addWidgetsToHolder(PaymentListViewHolder holder, PaymentGroup group, LayoutInflater inflater, ViewGroup parent) {
        List<FormWidget> widgets = createWidgets(group.elements, inflater, parent);
        FormWidget widget;

        for (int i = widgets.size(); i > 0; ) {
            widget = widgets.get(--i);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
        holder.addWidgets(widgets);
    }

    /**
     * The item listener
     */
    public interface OnItemListener {
        void onItemClicked(PaymentGroup item, int position);

        void onActionClicked(PaymentGroup item, int position);
    }
}
