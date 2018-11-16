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

package net.optile.payment.ui.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.page.PaymentPageActivity;
import net.optile.payment.validation.ValidationResult;

/**
 * The PaymentList showing available payment methods in a list
 */
public final class PaymentList {

    private final PaymentPageActivity activity;
    private final PaymentListAdapter adapter;
    private final RecyclerView recyclerView;
    private final TextView emptyMessage;

    private PaymentSession session;
    private int selIndex;
    private int viewType;
    private List<ListItem> items;

    public PaymentList(PaymentPageActivity activity, RecyclerView recyclerView, TextView emptyMessage) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.adapter = new PaymentListAdapter(this, items);
        this.emptyMessage = emptyMessage;
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

    public void clear() {
        this.session = null;
        this.selIndex = -1;
        this.items.clear();

        emptyMessage.setText("");
        adapter.notifyDataSetChanged();
    }

    public void showPaymentSession(PaymentSession session, int cachedListIndex) {

        if (this.session == session) {
            setVisible(true);
            return;
        }
        this.session = session;
        setPaymentListItems(session, cachedListIndex);

        if (items.size() == 0) {
            emptyMessage.setText(session.getEmptyMessage());
        } else {
            emptyMessage.setText("");
            recyclerView.scrollToPosition(selIndex);
        }
        adapter.notifyDataSetChanged();
        setVisible(true);
    }

    public void setVisible(boolean visible) {
        emptyMessage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
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

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    Context getContext() {
        return activity;
    }


    PaymentSession getPaymentSession() {
        return this.session;
    }

    void showDialogFragment(DialogFragment dialog, String tag) {
        dialog.show(activity.getSupportFragmentManager(), tag);
    }

    void onActionClicked(PaymentCard card, int position) {
        PaymentCardViewHolder holder = (PaymentCardViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            activity.makeChargeRequest(card, holder.widgets);
        }
    }

    void onItemClicked(ListItem item, int position) {

        if (item instanceof HeaderItem) {
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

    ValidationResult validate(PaymentCard item, String type, String value1, String value2) {
        return activity.validate(item, type, value1, value2);
    }

    private int nextViewType() {
        return viewType++;
    }

    private void setPaymentListItems(PaymentSession session, int cachedListIndex) {
        items.clear();

        int index = 0;
        int selIndex = cachedListIndex;

        if (session.accounts.size() > 0) {
            items.add(new HeaderItem(nextViewType(), "Account Header"));
            index++;
        }
        for (AccountCard card : session.accounts) {
            items.add(new AccountCardItem(nextViewType(), card));
            if (this.selIndex == -1 && card.isPreselected()) {
                this.selIndex = index;
            }
            index++;
        }
        if (session.networks.size() > 0) {
            items.add(new HeaderItem(nextViewType(), "network Header"));
        }
        for (NetworkCard card : session.networks) {
            items.add(new NetworkCardItem(nextViewType(), card));
            if (this.selIndex == -1 && card.isPreselected()) {
                this.selIndex = index;
            }
            index++;
        }
    }

    private void collapseViewHolder(int position) {
        PaymentCardViewHolder holder = (PaymentCardViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

        if (holder != null) {
            holder.expand(false);
            adapter.notifyItemChanged(position);
        }
    }

    private void expandViewHolder(int position) {
        PaymentCardViewHolder holder = (PaymentCardViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

        if (holder != null) {
            holder.expand(true);
            adapter.notifyItemChanged(position);
            smoothScrollToPosition(position);
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
