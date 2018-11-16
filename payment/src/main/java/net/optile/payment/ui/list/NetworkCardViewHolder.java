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

import java.net.URL;

import com.bumptech.glide.Glide;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.widget.RegisterWidget;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final ImageView logo;

    NetworkCardViewHolder(PaymentListAdapter adapter, View parent) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
    }

    static ViewHolder createInstance(PaymentListAdapter adapter, NetworkCardItem item, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_network, parent, false);
        NetworkCardViewHolder holder = new NetworkCardViewHolder(adapter, view);

        addInputWidgets(holder, inflater, parent, item.networkCard);
        addRegisterWidget(holder, PaymentInputType.AUTO_REGISTRATION, inflater, parent);
        addRegisterWidget(holder, PaymentInputType.ALLOW_RECURRENCE, inflater, parent);
        addButtonWidget(holder, inflater, parent);
        return holder;
    }

    static RegisterWidget addRegisterWidget(NetworkCardViewHolder holder, String name, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.widget_input_checkbox, parent, false);
        RegisterWidget widget = new RegisterWidget(name, view);
        holder.addWidget(widget);
        return widget;
    }

    void onBind(NetworkCardItem item) {
        PaymentNetwork network = item.networkCard.getActivePaymentNetwork();
        URL logoUrl = network.getLink("logo");
        title.setText(network.getLabel());

        if (logoUrl != null) {
            Glide.with(logo.getContext()).asBitmap().load(logoUrl.toString()).into(logo);
        }
        bindButtonWidget(item.networkCard);
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
    }

    private void bindRegistrationWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(PaymentInputType.AUTO_REGISTRATION);
        widget.setRegistrationType(network.getRegistration());
    }

    private void bindRecurrenceWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(PaymentInputType.ALLOW_RECURRENCE);
        widget.setRegistrationType(network.getRecurrence());
    }
}
