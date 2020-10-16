/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import net.optile.payment.R;
import net.optile.payment.model.AccountMask;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.util.PaymentUtils;

/**
 * The AccountCardViewHolder class holding and binding views for an AccountCard
 */
public final class AccountCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subtitle;

    private AccountCardViewHolder(ListAdapter adapter, View parent, AccountCard accountCard) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.subtitle = parent.findViewById(R.id.text_subtitle);
        addElementWidgets(accountCard);
        addButtonWidget();
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
    }
}
