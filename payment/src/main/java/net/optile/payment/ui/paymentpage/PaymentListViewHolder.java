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

import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import net.optile.payment.R;

/**
 * The PaymentListViewHolder holding all Views for easy access
 */
class PaymentListViewHolder extends RecyclerView.ViewHolder {

    final TextView title;
    
    PaymentListViewHolder(View parent) {
        super(parent);
        this.title = parent.findViewById(R.id.text_title);
    }
}
