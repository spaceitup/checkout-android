/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;


import static net.optile.payment.core.PaymentInputType.ALLOW_RECURRENCE;
import static net.optile.payment.core.PaymentInputType.AUTO_REGISTRATION;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import net.optile.payment.R;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.SmartSwitch;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.util.PaymentUtils;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private NetworkLogosView logosView;

    public NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent);

        this.title = parent.findViewById(R.id.text_title);
        addElementWidgets(networkCard);
        addRegisterWidgets();
        addButtonWidget();
        addNetworkLogos(parent, networkCard);
        setLastImeOptions();
    }

    static ViewHolder createInstance(ListAdapter adapter, NetworkCard networkCard, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_networkcard, parent, false);
        return new NetworkCardViewHolder(adapter, view, networkCard);
    }

    void onBind(PaymentCard paymentCard) {
        if (!(paymentCard instanceof NetworkCard)) {
            throw new IllegalArgumentException("Expected Networkcard in onBind");
        }
        super.onBind(paymentCard);
        NetworkCard networkCard = (NetworkCard) paymentCard;
        PaymentNetwork network = networkCard.getVisibleNetwork();
        title.setText(networkCard.getLabel());

        if (networkCard.getPaymentNetworkSize() == 1) {
            bindLogoView(networkCard.getCode(), networkCard.getLink("logo"));
            setTestId("network");
        } else {
            logoView.setImageResource(R.drawable.ic_card);
            bindLogos(networkCard);
            setTestId("group");
        }
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
    }

    private void bindLogos(NetworkCard card) {
        SmartSwitch smartSwitch = card.getSmartSwitch();

        if (smartSwitch.getSelectedSize() == 1) {
            PaymentNetwork network = smartSwitch.getFirstSelected();
            logosView.setSelected(network.getCode());
            return;
        }
        logosView.setSelected(null);
    }

    private void addNetworkLogos(View parent, NetworkCard networkCard) {
        if (networkCard.getPaymentNetworkSize() > 1) {
            this.logosView = new NetworkLogosView(parent, networkCard.getPaymentNetworks());
        }
    }
        
    private void setTestId(String testId) {
        PaymentUtils.setTestId(itemView, "card", testId);
    }

    private void addRegisterWidgets() {
        FormWidget widget = WidgetInflater.inflateRegisterWidget(AUTO_REGISTRATION, formLayout);
        addWidget(widget);
        widget = WidgetInflater.inflateRegisterWidget(ALLOW_RECURRENCE, formLayout);
        addWidget(widget);
    }

    private void bindRegistrationWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(AUTO_REGISTRATION);
        widget.onBind(network.getRegistration());
    }

    private void bindRecurrenceWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(ALLOW_RECURRENCE);
        widget.onBind(network.getRecurrence());
    }
}
