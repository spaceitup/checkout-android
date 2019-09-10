/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.model.AccountMask;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.ListParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * The AccountCardViewHolder class holding and binding views for an AccountCard
 */
public final class AccountCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subTitle;

    private AccountCardViewHolder(ListAdapter adapter, View parent, AccountCard accountCard) {
        super(adapter, parent);

        PaymentTheme theme = adapter.getPaymentTheme();
        ListParameters params = theme.getListParameters();

        this.title = parent.findViewById(R.id.text_title);
        PaymentUtils.setTextAppearance(title, params.getAccountCardTitleStyle());
        this.subTitle = parent.findViewById(R.id.text_subtitle);
        PaymentUtils.setTextAppearance(subTitle, params.getAccountCardSubtitleStyle());

        addLogoView(parent, accountCard.getCode(), theme);
        addElementWidgets(accountCard, theme);
        addButtonWidget(theme);
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
        bindMaskedTitle(title, mask, card.getPaymentMethod());
        bindMaskedSubTitle(subTitle, mask);
        bindLogoView(card.getCode(), card.getLink("logo"), true);
    }
}
