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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.model.AccountCard;

/**
 * The AccountCardViewHolder
 */
final class AccountCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final ImageView logo;

    AccountCardViewHolder(PaymentListAdapter adapter, View parent) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
    }

    static ViewHolder createInstance(PaymentListAdapter adapter, AccountCard accountCard, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_account, parent, false);
        AccountCardViewHolder holder = new AccountCardViewHolder(adapter, view);
        return holder;
    }

    void onBind(AccountCard accountCard) {
    }
}
