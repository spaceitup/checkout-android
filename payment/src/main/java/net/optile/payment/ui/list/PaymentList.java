/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import static net.optile.payment.localization.LocalizationKey.LIST_HEADER_ACCOUNTS;
import static net.optile.payment.localization.LocalizationKey.LIST_HEADER_NETWORKS;
import static net.optile.payment.localization.LocalizationKey.LIST_HEADER_NETWORKS_OTHER;
import static net.optile.payment.localization.LocalizationKey.LIST_HEADER_PRESET;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import net.optile.payment.R;
import net.optile.payment.localization.Localization;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.page.PaymentListActivity;

/**
 * The PaymentList showing available payment methods and accounts in a list
 */
public final class PaymentList {

    private final PaymentListActivity activity;
    private final ListAdapter adapter;
    private final RecyclerView recyclerView;
    private final TextView emptyMessage;
    private final List<ListItem> items;

    private PaymentSession session;
    private int selIndex;
    private int viewType;

    public PaymentList(PaymentListActivity activity, RecyclerView recyclerView, TextView emptyMessage) {
        this.activity = activity;
        this.items = new ArrayList<>();
        this.adapter = new ListAdapter(this, items);
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

    public void onStop() {
        hideKeyboard();
    }

    public void clear() {
        this.session = null;
        this.selIndex = -1;
        this.items.clear();

        emptyMessage.setText("");
        adapter.notifyDataSetChanged();
    }

    public void showPaymentSession(PaymentSession session) {

        if (this.session == session) {
            setVisible(true);
            return;
        }
        this.session = session;
        setEmptyMessage(session);
        setPaymentListItems(session);

        setVisible(true);
        adapter.notifyDataSetChanged();
        int startIndex = session.hasPresetCard() ? 0 : selIndex;
        recyclerView.scrollToPosition(startIndex);
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

    private void setEmptyMessage(PaymentSession session) {
        String msg = session.isEmpty() ? activity.getString(R.string.pmpage_error_empty) : "";
        emptyMessage.setText(msg);
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
