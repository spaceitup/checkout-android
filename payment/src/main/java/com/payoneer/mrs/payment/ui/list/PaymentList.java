/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.list;

import static com.payoneer.mrs.payment.localization.LocalizationKey.LIST_HEADER_ACCOUNTS;
import static com.payoneer.mrs.payment.localization.LocalizationKey.LIST_HEADER_NETWORKS;
import static com.payoneer.mrs.payment.localization.LocalizationKey.LIST_HEADER_NETWORKS_OTHER;
import static com.payoneer.mrs.payment.localization.LocalizationKey.LIST_HEADER_PRESET;

import java.util.ArrayList;
import java.util.List;

import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.ui.model.AccountCard;
import com.payoneer.mrs.payment.ui.model.NetworkCard;
import com.payoneer.mrs.payment.ui.model.PaymentCard;
import com.payoneer.mrs.payment.ui.model.PaymentSession;
import com.payoneer.mrs.payment.ui.page.PaymentListActivity;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * The PaymentList showing available payment methods and accounts in a list
 */
public final class PaymentList {

    private final PaymentListActivity activity;
    private final ListAdapter adapter;
    private final RecyclerView recyclerView;
    private final List<ListItem> items;

    private PaymentSession session;
    private int selIndex;
    private int viewType;

    public PaymentList(PaymentListActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.adapter = new ListAdapter(this, items);
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    public int getSelected() {
        return selIndex;
    }

    public void onStop() {
        hideKeyboard();
    }

    public void clear() {
        this.session = null;
        this.selIndex = -1;
        this.items.clear();
        adapter.notifyDataSetChanged();
    }

    public void showPaymentSession(PaymentSession session) {

        if (this.session == session) {
            setVisible(true);
            return;
        }
        this.session = session;
        setPaymentListItems(session);

        setVisible(true);
        adapter.notifyDataSetChanged();
        int startIndex = session.hasPresetCard() ? 0 : selIndex;
        recyclerView.scrollToPosition(startIndex);
    }

    public void setVisible(boolean visible) {
        recyclerView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            View curFocus = activity.getCurrentFocus();
            IBinder binder = curFocus != null ? curFocus.getWindowToken() : recyclerView.getWindowToken();
            imm.hideSoftInputFromWindow(binder, 0);
        }
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    Context getContext() {
        return activity;
    }

    PaymentSession getPaymentSession() {
        return this.session;
    }

    void onHintClicked(int position, String type) {
        ListItem item = items.get(position);
        PaymentCard card = item.getPaymentCard();

        if (card != null) {
            activity.showHintDialog(card.getCode(), type, null);
        }
    }

    void onActionClicked(int position) {
        ListItem item = items.get(position);
        PaymentCardViewHolder holder = (PaymentCardViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

        if (holder != null && item.hasPaymentCard()) {
            hideKeyboard();
            activity.onActionClicked(item.getPaymentCard(), holder.widgets);
        }
    }

    void onItemClicked(int position) {
        ListItem item = items.get(position);

        if (!item.hasPaymentCard()) {
            return;
        }
        hideKeyboard();
        if (position == this.selIndex) {
            this.selIndex = -1;
            collapseViewHolder(position);
        } else {
            int curIndex = this.selIndex;
            this.selIndex = position;
            collapseViewHolder(curIndex);
            expandViewHolder(position);
        }
    }

    private int nextViewType() {
        return viewType++;
    }

    private void setPaymentListItems(PaymentSession session) {
        items.clear();
        this.selIndex = -1;
        int accountSize = session.getAccountCardSize();
        int networkSize = session.getNetworkCardSize();

        if (session.hasPresetCard()) {
            items.add(new HeaderItem(nextViewType(), Localization.translate(LIST_HEADER_PRESET)));
            items.add(new PaymentCardItem(nextViewType(), session.getPresetCard()));
            this.selIndex = 1;
        }

        if (accountSize > 0) {
            items.add(new HeaderItem(nextViewType(), Localization.translate(LIST_HEADER_ACCOUNTS)));
        }
        for (AccountCard card : session.getAccountCards()) {
            items.add(new PaymentCardItem(nextViewType(), card));
            if (this.selIndex == -1 && card.isPreselected()) {
                this.selIndex = items.size() - 1;
            }
        }
        if (networkSize > 0) {
            String key = accountSize == 0 ? LIST_HEADER_NETWORKS : LIST_HEADER_NETWORKS_OTHER;
            items.add(new HeaderItem(nextViewType(), Localization.translate(key)));
        }
        for (NetworkCard card : session.getNetworkCards()) {
            items.add(new PaymentCardItem(nextViewType(), card));
            if (this.selIndex == -1 && card.isPreselected()) {
                this.selIndex = items.size() - 1;
            }
        }
    }

    private void collapseViewHolder(int position) {
        adapter.notifyItemChanged(position);
    }

    private void expandViewHolder(int position) {
        adapter.notifyItemChanged(position);
        smoothScrollToPosition(position);
    }

    private void smoothScrollToPosition(int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager != null) {
            manager.startSmoothScroll(smoothScroller);
        }
    }
}
