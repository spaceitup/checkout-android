/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import static net.optile.payment.core.Localization.BUTTON_BACK;
import static net.optile.payment.core.Localization.LIST_HEADER_NETWORKS;
import static net.optile.payment.core.Localization.LIST_HEADER_OTHERACCOUNTS;
import static net.optile.payment.core.Localization.LIST_HEADER_PRESET;
import static net.optile.payment.core.Localization.LIST_HEADER_SAVEDACCOUNTS;

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
import net.optile.payment.R;
import net.optile.payment.core.Localization;
import net.optile.payment.ui.dialog.DialogHelper;
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
        String msg = "";

        if (session.getApplicableNetworkSize() == 0) {
            msg = activity.getString(R.string.pmpage_error_empty);
        } else if (session.getNetworkCardSize() == 0) {
            msg = activity.getString(R.string.pmpage_error_notsupported);
        } else {
            int startIndex = session.hasPresetCard() ? 0 : selIndex;
            recyclerView.scrollToPosition(startIndex);
        }
        emptyMessage.setText(msg);
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
        activity.showDialogFragment(dialog, tag);
    }

    void onHintClicked(int position, String type) {
        ListItem item = items.get(position);
        PaymentCard card = item.getPaymentCard();
        
        if (card != null) {
            String button = Localization.translate(card.getCode(), BUTTON_BACK);
            DialogFragment dialog = DialogHelper.createHintDialog(card, type, button);
            showDialogFragment(dialog, "hint_dialog");
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

    private void setPaymentListItems(PaymentSession session, int cachedListIndex) {
        items.clear();
        this.selIndex = cachedListIndex;
        int accountSize = session.getAccountCardSize();
        int networkSize = session.getNetworkCardSize();

        if (session.hasPresetCard()) {
            items.add(new HeaderItem(nextViewType(), Localization.translate(LIST_HEADER_PRESET)));
            items.add(new PaymentCardItem(nextViewType(), session.getPresetCard()));
            this.selIndex = 1;
        }

        if (accountSize > 0) {
            items.add(new HeaderItem(nextViewType(), Localization.translate(LIST_HEADER_SAVEDACCOUNTS)));
        }
        for (AccountCard card : session.getAccountCards()) {
            items.add(new PaymentCardItem(nextViewType(), card));
            if (this.selIndex == -1 && card.isPreselected()) {
                this.selIndex = items.size() - 1;
            }
        }
        if (networkSize > 0) {
            String key = accountSize == 0 ? LIST_HEADER_NETWORKS : LIST_HEADER_OTHERACCOUNTS;
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
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager != null) {
            manager.startSmoothScroll(smoothScroller);
        }
    }
}
