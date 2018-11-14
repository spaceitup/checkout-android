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
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
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
class PaymentListAdapter extends RecyclerView.Adapter<PaymentListViewHolder> {

    private final static String TAG = "pay_PaymentListAdapter";
    private final static String WIDGET_BUTTON = "widgetButton";

    private final static String PAGEKEY_BUTTON_DATE = "button.update.label";
    private final static String PAGEKEY_AUTO_REGISTRATION = "autoRegistrationLabel";
    private final static String PAGEKEY_ALLOW_RECURRENCE = "allowRecurrenceLabel";

    private final List<PaymentListSection> sections;
    private final PaymentList list;

    PaymentListAdapter(PaymentList list, List<PaymentListSection> sections) {
        this.list = list;
        this.sections = sections;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull
    PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PaymentListItem item = getItemWithViewType(viewType);

        View view = inflater.inflate(R.layout.list_item_paymentpage, parent, false);
        PaymentListViewHolder holder = new PaymentListViewHolder(this, view);

        //if (item != null) {
        //  addWidgetsToHolder(holder, group, inflater, parent);
        //}
        return holder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull PaymentListViewHolder holder, int position) {
        PaymentListItem item = items.get(position);
        //PaymentNetwork item = group.getActiveNetworkItem();

        //URL logoUrl = item.getLogoLink();
        //holder.title.setText(item.getLabel());

        //if (logoUrl != null) {
        //    Glide.with(list.getContext()).asBitmap().load(logoUrl.toString()).into(holder.logo);
        // /}
        //bindRegistrationWidget(item, holder);
        //bindRecurrenceWidget(item, holder);
        //bindButtonWidget(item, holder);
        //holder.expand(position == list.getSelected());
    }

    private void bindRegistrationWidget(PaymentNetwork item, PaymentListViewHolder holder) {
        RegisterWidget widget = (RegisterWidget) holder.getFormWidget(PaymentInputType.AUTO_REGISTRATION);
        widget.setLabel(translate(PAGEKEY_AUTO_REGISTRATION));
        widget.setRegistrationType(item.getRegistration());
    }

    private void bindRecurrenceWidget(PaymentNetwork item, PaymentListViewHolder holder) {
        RegisterWidget widget = (RegisterWidget) holder.getFormWidget(PaymentInputType.ALLOW_RECURRENCE);
        widget.setLabel(translate(PAGEKEY_ALLOW_RECURRENCE));
        widget.setRegistrationType(item.getRecurrence());
    }

    private void bindButtonWidget(PaymentNetwork item, PaymentListViewHolder holder) {
        ButtonWidget widget = (ButtonWidget) holder.getFormWidget(WIDGET_BUTTON);
        String buttonLabel = translate(item.getButton());

        widget.setLabel(buttonLabel);
        widget.setVisible(true);
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
        PaymentListItem item = items.get(position);
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

    /**
     * Get the PaymentListItem at the given index
     *
     * @param index index of the NetworkCard
     * @return PaymentListItem given the index or null if not found
     */
    PaymentListItem getItemFromIndex(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    /**
     * Get the list item with its type matching the viewType
     *
     * @param type type of the view
     * @return PaymentListItem with the same type or null if not found
     */
    private PaymentListItem getItemWithViewType(int viewType) {

        for (PaymentListItem item : items) {
            if (item.viewType == viewType) {
                return item;
            }
        }
        return null;
    }

    private String translate(String key) {
        LanguageFile lang = list.getPaymentSession().getLang();
        return lang.translate(key, key);
    }

    private List<FormWidget> createWidgets(NetworkCard card, LayoutInflater inflater, ViewGroup parent) {
        PaymentTheme theme = PaymentUI.getInstance().getPaymentTheme();
        List<FormWidget> widgets = new ArrayList<>();
        DateWidget dateWidget = null;

        for (InputElement element : card.elements) {

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
        widgets.add(createRegisterWidget(inflater, parent, PaymentInputType.AUTO_REGISTRATION));
        widgets.add(createRegisterWidget(inflater, parent, PaymentInputType.ALLOW_RECURRENCE));

        View view = inflater.inflate(R.layout.widget_button, parent, false);
        widgets.add(new ButtonWidget(WIDGET_BUTTON, view));
        return widgets;
    }

    private RegisterWidget createRegisterWidget(LayoutInflater inflater, ViewGroup parent, String name) {
        View view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
        return new RegisterWidget(name, view);
    }

    private DateWidget createDateWidget(PaymentTheme theme, NetworkCard card, LayoutInflater inflater, ViewGroup parent) {
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

    private void addWidgetsToHolder(PaymentListViewHolder holder, NetworkCard group, LayoutInflater inflater, ViewGroup parent) {
        List<FormWidget> widgets = createWidgets(group, inflater, parent);
        FormWidget widget;

        for (int i = widgets.size(); i > 0; ) {
            widget = widgets.get(--i);
            if (widget.setLastImeOptionsWidget()) {
                break;
            }
        }
        holder.addWidgets(widgets);
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position < items.size();
    }
}
