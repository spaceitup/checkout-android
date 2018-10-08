/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.paymentpage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * The PaymentList showing available payment methods in a list
 */
final class PaymentList implements PaymentListAdapter.OnItemListener {

    private final static String TAG = "pay_PaymentList";

    private final PaymentPageActivity activity;

    private final PaymentListAdapter adapter;

    private final RecyclerView recyclerView;

    private int selIndex;

    PaymentList(PaymentPageActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.adapter = new PaymentListAdapter(this);
        this.adapter.setListener(this);

        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    int getSelected() {
        return selIndex;
    }

    Context getContext() {
        return activity;
    }

    void setVisible(boolean visible) {
        recyclerView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    void showPaymentSession(PaymentSession session) {
        this.selIndex = session.selIndex;
        adapter.setPaymentSession(session);
        recyclerView.scrollToPosition(selIndex);
        setVisible(true);
    }

    void clear() {
        adapter.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionClicked(PaymentGroup item, int position) {
        PaymentListViewHolder holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            activity.makeChargeRequest(item, holder.widgets);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClicked(PaymentGroup item, int position) {
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

    private void collapseViewHolder(int position) {
        PaymentListViewHolder holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            holder.expand(false);
            adapter.notifyItemChanged(position);
        }
    }

    private void expandViewHolder(int position) {
        PaymentListViewHolder holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            holder.expand(true);
            adapter.notifyItemChanged(position);
            smoothScrollToPosition(position);
        }
    }
    
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
        }
    }

    private void smoothScrollToPosition(int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }
}
