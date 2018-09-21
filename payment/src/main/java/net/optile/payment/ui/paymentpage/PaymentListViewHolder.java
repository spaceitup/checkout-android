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
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import net.optile.payment.R;

/**
 * The PaymentListViewHolder holding all Views for easy access
 */
class PaymentListViewHolder extends RecyclerView.ViewHolder {

    final TextView title;

    final ImageView logo;

    final View inputFields;
    
    final PaymentListAdapter adapter;
    
    PaymentListViewHolder(PaymentListAdapter adapter, View parent) {
        super(parent);
        this.adapter = adapter;
        this.title = parent.findViewById(R.id.text_title);
        this.logo = parent.findViewById(R.id.image_logo);
        this.inputFields = parent.findViewById(R.id.layout_inputfields);
        
        View view = parent.findViewById(R.id.cardview);
        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.handleOnClick(getAdapterPosition());
                }
            });
    }

    void toggle() {
        inputFields.setVisibility(inputFields.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
