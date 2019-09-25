/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import net.optile.payment.R;
import net.optile.payment.util.PaymentUtils;

/**
 * The HeaderViewHolder holding and binding views for a header in the RecyclerView
 */
public final class HeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    HeaderViewHolder(View parent) {
        super(parent);
        this.title = parent.findViewById(R.id.text_title);
    }

    static ViewHolder createInstance(ListAdapter adapter) {
        View view = View.inflate(adapter.getContext(), R.layout.list_item_header, null);
        return new HeaderViewHolder(view);
    }

    void onBind(HeaderItem item) {
        PaymentUtils.setTestId(itemView, "label", "header");
        title.setText(item.title);
    }
}
