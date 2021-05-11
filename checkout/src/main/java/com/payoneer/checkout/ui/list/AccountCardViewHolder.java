/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import static com.payoneer.checkout.model.NetworkOperationType.UPDATE;

import com.google.android.material.card.MaterialCardView;
import com.payoneer.checkout.R;
import com.payoneer.checkout.model.AccountMask;
import com.payoneer.checkout.ui.model.AccountCard;
import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.util.PaymentUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * The AccountCardViewHolder class holding and binding views for an AccountCard
 */
public final class AccountCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subtitle;
    private final ImageView icon;
    private final MaterialCardView card;
    private boolean update;
    
    private AccountCardViewHolder(ListAdapter adapter, View parent, AccountCard accountCard) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.subtitle = parent.findViewById(R.id.text_subtitle);
        this.icon = parent.findViewById(R.id.image_icon);
        card = parent.findViewById(R.id.card_account);
        card.setCheckable(true);

        addElementWidgets(accountCard);
        addButtonWidget();
        layoutWidgets();

        setLastImeOptions();
    }

    static ViewHolder createInstance(ListAdapter adapter, AccountCard accountCard, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_accountcard, parent, false);
        return new AccountCardViewHolder(adapter, view, accountCard);
    }

    void onBind(PaymentCard paymentCard) {

        if (!(paymentCard instanceof AccountCard)) {
            throw new IllegalArgumentException("Expected AccountCard in onBind");
        }
        super.onBind(paymentCard);
        PaymentUtils.setTestId(itemView, "card", "savedaccount");
        AccountCard card = (AccountCard) paymentCard;
        AccountMask mask = card.getMaskedAccount();
        subtitle.setVisibility(View.GONE);
        if (mask != null) {
            bindAccountMask(title, subtitle, mask, card.getPaymentMethod());
        } else {
            title.setText(card.getLabel());
        }
        bindCardLogo(card.getCode(), card.getLink("logo"));
        update = UPDATE.equals(paymentCard.getOperationType());
        icon.setVisibility(update ? View.VISIBLE : View.GONE);
    }

    void expand(boolean expand) {
        super.expand(expand);
        card.setChecked(expand ? update : false);
    }
}
