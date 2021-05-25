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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * The AccountCardViewHolder class holding and binding views for an AccountCard
 */
public final class AccountCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subtitle;
    private final MaterialCardView card;
    private final IconView iconView;
    
    private AccountCardViewHolder(ListAdapter adapter, View parent, AccountCard accountCard) {
        super(adapter, parent, accountCard);
        this.title = parent.findViewById(R.id.text_title);
        this.subtitle = parent.findViewById(R.id.text_subtitle);

        iconView = new IconView(parent);
        iconView.setListener(new IconView.IconClickListener() {
                public void onIconClick(int index) {
                    handleIconClicked(index);
                }

            });
        
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

    /**
     * {@inheritDoc}
     */
    @Override
    void onBind() {
        super.onBind();

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void expand(boolean expand) {
        super.expand(expand);
        boolean update = UPDATE.equals(paymentCard.getOperationType());

        if (update) {
            iconView.showIcon(expand ? 1 : 0);
        }
    }

    private void handleIconClicked(int index) {
        if (index == 1) {
            // Here we delete the card
        } else {
            handleCardClicked();
        }
    }
}
