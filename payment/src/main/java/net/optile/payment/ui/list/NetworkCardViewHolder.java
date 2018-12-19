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

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.util.ImageHelper;
import net.optile.payment.util.PaymentUtils;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final ImageView logo;

    NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
        PaymentTheme theme = adapter.getPaymentTheme();

        addElementWidgets(networkCard.getInputElements(), theme);
        addRegisterWidgets(theme);
        addButtonWidget(theme);

        applyTheme(theme);
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
        PaymentNetwork network = networkCard.getActivePaymentNetwork();
        URL logoUrl = network.getLink("logo");
        title.setText(network.getLabel());

        if (logoUrl != null) {
            ImageHelper.getInstance().loadImage(logo, logoUrl);
        }
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
    }

    private void addRegisterWidgets(PaymentTheme theme) {
        FormWidget widget = WidgetInflater.inflateRegisterWidget(PaymentInputType.AUTO_REGISTRATION, formLayout, theme);
        addWidget(widget);
        widget = WidgetInflater.inflateRegisterWidget(PaymentInputType.ALLOW_RECURRENCE, formLayout, theme);
        addWidget(widget);
    }

    private void applyTheme(PaymentTheme theme) {
        PageParameters params = theme.getPageParameters();
        PaymentUtils.setTextAppearance(title, params.getNetworkCardTitleStyle());
        PaymentUtils.setImageBackground(logo, params.getPaymentLogoBackground());
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
