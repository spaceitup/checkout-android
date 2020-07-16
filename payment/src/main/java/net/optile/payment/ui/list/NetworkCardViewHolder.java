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

import java.util.HashMap;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import net.optile.payment.R;
import net.optile.payment.localization.Localization;
import net.optile.payment.localization.LocalizationKey;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.RegisterWidget;
import net.optile.payment.ui.widget.WidgetInflater;
import net.optile.payment.util.PaymentUtils;

/**
 * The NetworkCardViewHolder
 */
final class NetworkCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    final Map<String, ImageView> logos;

    public NetworkCardViewHolder(ListAdapter adapter, View parent, NetworkCard networkCard) {
        super(adapter, parent);

        this.title = parent.findViewById(R.id.text_title);
        this.logos = new HashMap<>();

        addElementWidgets(networkCard);
        addRegisterWidgets();
        addButtonWidget();
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

        if (networkCard.getPaymentNetworkSize() == 1) {
            bindSinglePaymentNetwork(networkCard);
        } else {
            bindGroupedPaymentNetworks(networkCard);
        }
    }

    private void bindSinglePaymentNetwork(NetworkCard networkCard) {
        PaymentUtils.setTestId(itemView, "card", "network");

        PaymentNetwork network = networkCard.getVisibleNetwork();
        title.setText(networkCard.getLabel());
        bindLogoView(network.getCode(), network.getLink("logo"));
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
    }

    private void bindGroupedPaymentNetworks(NetworkCard networkCard) {
        PaymentUtils.setTestId(itemView, "card", "group");

        PaymentNetwork network = networkCard.getVisibleNetwork();
        logoView.setImageResource(R.drawable.ic_card);
        title.setText(Localization.translate(LocalizationKey.LIST_GROUPEDCARDS_TITLE));
        bindRegistrationWidget(network);
        bindRecurrenceWidget(network);
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

    /*

      List<PaymentNetwork> networks = card.getPaymentNetworks();
      SmartSwitch smartSwitch = card.getSmartSwitch();
      boolean selected;

      for (PaymentNetwork network : networks) {
      selected = !smartSwitch.hasSelected() || smartSwitch.isSelected(network);
      bindLogoView(network.getCode(), network.getLink("logo"), selected);
      }

      void bindLogoView1(String name, URL url, boolean selected) {
      ImageView view = logos.get(name);

      if (view == null || url == null) {
      return;
      }
      ImageHelper.getInstance().loadImage(view, url);
      float alpha = selected ? ALPHA_SELECTED : ALPHA_DESELECTED;
      ObjectAnimator.ofFloat(view, "alpha", alpha).setDuration(ANIM_DURATION).start();
      }

      void addLogoViews(View parent, List<String> names) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      //float alpha = selected ? ALPHA_SELECTED : ALPHA_DESELECTED;
      //ObjectAnimator.ofFloat(view, "alpha", alpha).setDuration(ANIM_DURATION).start();

      Context context = parent.getContext();

      TableRow row = null;
      Resources res = context.getResources();
      int border = res.getDimensionPixelSize(R.dimen.pmborder_xsmall);
      int columnsPerRow = PaymentUtils.isLandscape(context) ? COLUMN_SIZE_LANDSCAPE : COLUMN_SIZE_PORTRAIT;
      int columnIndex = 0;
      int rowCount = 0;

      for (String name : names) {

      if (columnIndex % columnsPerRow == 0) {
      rowCount++;
      columnIndex = 0;
      row = new TableRow(context);
      logoLayout.addView(row);
      }
      int marginTop = rowCount > 1 ? border : 0;
      int marginRight = ++columnIndex < columnsPerRow ? border : 0;

      ImageView view = (ImageView) inflater.inflate(R.layout.list_item_logo, row, false);
      LayoutParams params = (LayoutParams) view.getLayoutParams();

      params.setMargins(0, marginTop, marginRight, 0);
      view.setLayoutParams(params);
      logos.put(name, view);
      row.addView(view);
      }
      }

      private void addNetworkLogos(View parent, NetworkCard networkCard) {

      List<String> names = new ArrayList<>();
      List<PaymentNetwork> networks = networkCard.getPaymentNetworks();

      for (PaymentNetwork network : networks) {
      names.add(network.getCode());
      }
      addLogoViews(parent, names);
      }*/
}
