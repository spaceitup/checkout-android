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
import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.widget.RegisterWidget;
import android.view.ContextThemeWrapper;
import android.util.Log;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final ImageView logo;

    NetworkCardViewHolder(ListAdapter adapter, View parent) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
    }

    static ViewHolder createInstance(ListAdapter adapter, NetworkCard networkCard, ViewGroup parent) {
        PaymentTheme theme = adapter.getPaymentTheme();
        int themeId = theme.getThemeParameters().getCardViewTheme();
        View view = inflateWithTheme(adapter.getContext(), themeId, R.layout.list_item_network, null);
        NetworkCardViewHolder holder = new NetworkCardViewHolder(adapter, view);

        addInputWidgets(adapter, holder, networkCard);
        holder.addWidget(createRegisterWidget(adapter, PaymentInputType.AUTO_REGISTRATION, holder.formLayout));
        holder.addWidget(createRegisterWidget(adapter, PaymentInputType.ALLOW_RECURRENCE, holder.formLayout));
        holder.addWidget(createButtonWidget(adapter, holder.formLayout));
        holder.setLastImeOptions();
        return holder;
    }

    static RegisterWidget createRegisterWidget(ListAdapter adapter, String name, ViewGroup parent) {
        PaymentTheme theme = adapter.getPaymentTheme();
        int themeId = theme.getThemeParameters().getCheckBoxTheme();
        View view = inflateWithTheme(adapter.getContext(), themeId, R.layout.widget_input_checkbox, parent);
        RegisterWidget widget = new RegisterWidget(name, view);
        widget.applyTheme(theme);
        return widget;
    }

    void onBind(PaymentCard paymentCard) {

        if (!(paymentCard instanceof NetworkCard)) {
            throw new IllegalArgumentException("Expected Networkcard in onBind");
        }
        super.onBind(paymentCard);

        NetworkCard networkCard = (NetworkCard) paymentCard;
        PaymentNetwork network = networkCard.getActivePaymentNetwork();
        URL logoUrl = network.getLink("logo");
        title.setText(network.getLabel());

        if (logoUrl != null) {
            Glide.with(logo.getContext()).asBitmap().load(logoUrl.toString()).into(logo);
        }
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
    }

    private void bindRegistrationWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(PaymentInputType.AUTO_REGISTRATION);
        widget.setRegistrationType(network.getRegistration());

        LanguageFile lang = adapter.getPageLanguageFile();
        widget.setLabel(lang.translate(LanguageFile.KEY_AUTO_REGISTRATION, null));
    }

    private void bindRecurrenceWidget(PaymentNetwork network) {
        RegisterWidget widget = (RegisterWidget) getFormWidget(PaymentInputType.ALLOW_RECURRENCE);
        widget.setRegistrationType(network.getRecurrence());

        LanguageFile lang = adapter.getPageLanguageFile();
        widget.setLabel(lang.translate(LanguageFile.KEY_ALLOW_RECURRENCE, null));
    }
}
