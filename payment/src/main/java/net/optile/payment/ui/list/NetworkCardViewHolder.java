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

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import net.optile.payment.util.PaymentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TableLayout logoLayout;
    private final Map<String, ImageView> logos;
    
    NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logos = new HashMap<>();
        this.logoLayout = parent.findViewById(R.id.tablelayout_logo);
        
        PaymentTheme theme = adapter.getPaymentTheme();
        addLogos(parent, networkCard, theme);
        addElementWidgets(networkCard.getInputElements(), theme);
        addRegisterWidgets(theme);
        addButtonWidget(theme);
        applyTheme(theme);
        setLastImeOptions();
    }

    private void addLogos(View parent, NetworkCard networkCard, PaymentTheme theme) {
        int logoBackground = theme.getPageParameters().getPaymentLogoBackground();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        List<PaymentNetwork> networks = networkCard.getPaymentNetworks();

        int count = 0;
        TableRow row = null;
        int border = parent.getContext().getResources().getDimensionPixelSize(R.dimen.pmborder_xsmall);

        for (PaymentNetwork network : networks) {
            int marginTop = 0;
            int marginRight = 0;
            
            if (count > 1) {
                marginTop = border;
            }
            if (count % 2 == 0) {
                row = new TableRow(parent.getContext());
                logoLayout.addView(row);
                marginRight = border;
            }
            ImageView view = (ImageView)inflater.inflate(R.layout.list_item_logo, (ViewGroup)row, false);
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.setMargins(0, marginTop, marginRight, 0);
            view.setLayoutParams(params);
            PaymentUtils.setImageBackground(view, logoBackground);
            logos.put(network.getCode(), view);
            row.addView(view);
            count++;
        }
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

        bindHeader(networkCard);
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
    }

    private void bindHeader(NetworkCard card) {
        bindTitle(card);
        bindLogos(card);
    }

    private void bindTitle(NetworkCard card) {
        List<PaymentNetwork> networks = card.getSmartSelected();
        if (networks.size() == 0) {
            networks = card.getPaymentNetworks();
        }
        List<String> labels = new ArrayList<>();

        for (PaymentNetwork network : networks) {
            labels.add(network.getLabel());
        }
        title.setText(TextUtils.join(" / ", labels));
    }

    private void bindLogos(NetworkCard card) {
        List<PaymentNetwork> networks = card.getPaymentNetworks();
        ImageView view;

        for (PaymentNetwork network : networks) {
            view = logos.get(network.getCode());
            if (view == null) {
                continue;
            }
            boolean selected = isExpanded() && card.isSmartSelected(network); 
            bindLogo(view, network.getLink("logo"), selected);
        }
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
