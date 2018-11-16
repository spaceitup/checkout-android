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

package net.optile.payment.ui.paymentpage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.validation.ValidationResult;

/**
 * The AccountCardViewHolder
 */
class AccountCardViewHolder extends PaymentCardViewHolder {

    final TextView title;
    final ImageView logo;

    AccountCardViewHolder(PaymentListAdapter adapter, View parent) {
        super(adapter, parent);
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
    }

    void onBind(boolean selected, AccountCardItem item) {
    }
}
