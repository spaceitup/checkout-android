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

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxInputWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.SelectInputWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentListAdapter containing the list of items
 */
class PaymentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "pay_PaymentListAdapter";
    private final static String WIDGET_BUTTON = "widgetButton";

    private final static String PAGEKEY_BUTTON_DATE = "button.update.label";
    private final static String PAGEKEY_AUTO_REGISTRATION = "autoRegistrationLabel";
    private final static String PAGEKEY_ALLOW_RECURRENCE = "allowRecurrenceLabel";

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
            holder = createNetworkViewHolder(parent, inflater, (NetworkCardItem)item);
        } else if (item instanceof AccountCardItem) {
            holder = createAccountViewHolder(parent, inflater, (AccountCardItem)item);            
        } else if (item instanceof HeaderItem) {
            holder = createHeaderViewHolder(parent, inflater, (HeaderItem)item);
        }
        return holder;
    }

    private ViewHolder createNetworkViewHolder(ViewGroup parent, LayoutInflater inflater, NetworkCardItem item) {
        View view = inflater.inflate(R.layout.list_item_network, parent, false);
        NetworkCardViewHolder holder = new NetworkCardViewHolder(this, view);
        addWidgetsToHolder(holder, item.network, inflater, parent);            
        return holder;
    }            

    private ViewHolder createAccountViewHolder(ViewGroup parent, LayoutInflater inflater, AccountCardItem item) {
        View view = inflater.inflate(R.layout.list_item_account, parent, false);
        AccountCardViewHolder holder = new AccountCardViewHolder(this, view);
        addWidgetsToHolder(holder, item.account, inflater, parent);
        return holder;
    }            

    private ViewHolder createHeaderViewHolder(ViewGroup parent, LayoutInflater inflater, HeaderItem item) {
        View view = inflater.inflate(R.layout.list_item_header, parent, false);
        return new HeaderViewHolder(view);
    }            

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);
        boolean selected = list.getSelected() == position;
        
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder)holder).onBind((HeaderItem)item);    
        } else if (holder instanceof AccountCardViewHolder) {
            ((AccountCardViewHolder)holder).onBind(selected, (AccountCardItem)item);
        } else if (holder instanceof NetworkCardViewHolder) {
            ((NetworkCardViewHolder)holder).onBind(selected, (NetworkCardItem)item);
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
        //NetworkCard item = items.get(position);
        //list.onItemClicked(item, position);
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

    String translate(String key) {
        LanguageFile lang = list.getPaymentSession().getLang();
        return lang.translate(key, key);
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

    private void addWidgetsToHolder(PaymentCardViewHolder holder, PaymentCard card, LayoutInflater inflater, ViewGroup parent) {
        List<FormWidget> widgets = createWidgets(card, inflater, parent);
        FormWidget widget;

        for (int i = widgets.size(); i > 0; ) {
            widget = widgets.get(--i);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
        holder.addWidgets(widgets);
    }

    private List<FormWidget> createWidgets(PaymentCard card, LayoutInflater inflater, ViewGroup parent) {
        PaymentTheme theme = PaymentUI.getInstance().getPaymentTheme();
        List<FormWidget> widgets = new ArrayList<>();
        DateWidget dateWidget = null;

        for (InputElement element : card.getInputElements()) {

            if (!card.hasExpiryDate()) {
                widgets.add(createInputWidget(theme, element, inflater, parent));
                continue;
            }
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                    if (dateWidget == null) {
                        dateWidget = createDateWidget(theme, card, inflater, parent);
                        widgets.add(dateWidget);
                    }
                    dateWidget.setMonthInputElement(element);
                    break;
                case PaymentInputType.EXPIRY_YEAR:
                    if (dateWidget == null) {
                        dateWidget = createDateWidget(theme, card, inflater, parent);
                        widgets.add(dateWidget);
                    }
                    dateWidget.setYearInputElement(element);
                    break;
                default:
                    widgets.add(createInputWidget(theme, element, inflater, parent));
            }
        }
        widgets.add(createRegisterWidget(inflater, parent, PaymentInputType.AUTO_REGISTRATION, PAGEKEY_AUTO_REGISTRATION));
        widgets.add(createRegisterWidget(inflater, parent, PaymentInputType.ALLOW_RECURRENCE, PAGEKEY_ALLOW_RECURRENCE));
        widgets.add(createButtonWidget(inflater, parent));
        return widgets;
    }

    private ButtonWidget createButtonWidget(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_button, parent, false);
        return new ButtonWidget(WIDGET_BUTTON, view);
    }
            
    private RegisterWidget createRegisterWidget(LayoutInflater inflater, ViewGroup parent, String name, String label) {
        View view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
        RegisterWidget widget = new RegisterWidget(name, view);
        widget.setLabel(translate(label));
        return widget;
    }

    private DateWidget createDateWidget(PaymentTheme theme, PaymentCard card, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_input_date, parent, false);
        DateWidget widget = new DateWidget(PaymentInputType.EXPIRY_DATE, view);
        String label = card.getLang().translateAccountLabel(PaymentInputType.EXPIRY_DATE);

        widget.setLabel(label);
        widget.setButton(translate(PAGEKEY_BUTTON_DATE));
        widget.setIconResource(theme.getWidgetIconRes(PaymentInputType.EXPIRY_DATE));
        return widget;
    }

    private FormWidget createInputWidget(PaymentTheme theme, InputElement element, LayoutInflater inflater, ViewGroup parent) {
        FormWidget widget;
        String name = element.getName();

        switch (element.getType()) {
            case InputElementType.SELECT:
                View view = inflater.inflate(R.layout.widget_input_select, parent, false);
                widget = new SelectInputWidget(name, view, element);
                break;
            case InputElementType.CHECKBOX:
                view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
                widget = new CheckBoxInputWidget(name, view);
                break;
            default:
                view = inflater.inflate(R.layout.widget_input_text, parent, false);
                widget = new TextInputWidget(name, view, element);
        }
        widget.setIconResource(theme.getWidgetIconRes(name));
        widget.setLabel(element.getLabel());
        return widget;
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position < items.size();
    }
}
