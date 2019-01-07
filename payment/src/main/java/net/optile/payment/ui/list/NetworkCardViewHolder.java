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
import java.util.List;
import java.util.ArrayList;

import android.util.Log;
import android.text.TextUtils;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.GridView;
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
import android.widget.LinearLayout.LayoutParams;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final TableLayout logoLayout;
    final List<ImageView> logos;
    
    NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logos = new ArrayList<>();
        this.logoLayout = parent.findViewById(R.id.tablelayout_logo);

        initLogos(parent, networkCard);
        
        PaymentTheme theme = adapter.getPaymentTheme();

        addElementWidgets(networkCard.getInputElements(), theme);
        addRegisterWidgets(theme);
        addButtonWidget(theme);

        applyTheme(theme);
        setLastImeOptions();
    }

    private void initLogos(View parent, NetworkCard networkCard) {
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
            logos.add(view);
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

        for (ImageView logo : logos) {
            PaymentUtils.setImageBackground(logo, params.getPaymentLogoBackground());
        }
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
        PaymentNetwork network;
        ImageView view;
        URL url;
        
        for (int i = 0, e = networks.size() ; i < e ; i++) {
            network = networks.get(i);
            view = logos.get(i);
            url = network.getLink("logo");

            if (url != null) {
                boolean expanded = isExpanded() && card.isSmartSelected(network);
                view.setImageAlpha(expanded ? LOGO_SELECTED_ALPHA : LOGO_DESELECTED_ALPHA); 
                ImageHelper.getInstance().loadImage(view, url);
            }
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
