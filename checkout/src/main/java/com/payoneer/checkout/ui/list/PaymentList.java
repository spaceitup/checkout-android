/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import java.util.ArrayList;
import java.util.List;

import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.model.PaymentSection;
import com.payoneer.checkout.ui.model.PaymentSession;
import com.payoneer.checkout.ui.page.PaymentListActivity;

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
        setPaymentSessionItems(session);

        setVisible(true);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(selIndex == 1 ? 0 : selIndex);
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

    private void setPaymentSessionItems(PaymentSession session) {
        items.clear();
        this.selIndex = -1;

        for (PaymentSection section : session.getPaymentSections()) {
            addPaymentSectionItems(section);
        }
    }

    private void addPaymentSectionItems(PaymentSection section) {
        items.add(new HeaderItem(nextViewType(), section.getLabel()));
        for (PaymentCard card : section.getPaymentCards()) {
            addPaymentCardItem(card);
        }
    }

    private void addPaymentCardItem(PaymentCard card) {
        items.add(new PaymentCardItem(nextViewType(), card));
        if (this.selIndex == -1 && card.isPreselected()) {
            this.selIndex = items.size() - 1;
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
