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

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentTheme;

/**
 * The PaymentPageActivity showing available payment methods
 */
public final class PaymentPageActivity extends AppCompatActivity implements PaymentPageView, PaymentListAdapter.OnItemListener {

    private final static String TAG = "pay_PaymentPageActivity";
    private final static String EXTRA_LISTURL = "extra_listurl";
    private final static String EXTRA_PAYMENTTHEME = "extra_paymenttheme";

    private PaymentPagePresenter presenter;

    private PaymentListAdapter adapter;

    private String listUrl;

    private PaymentTheme theme;

    private boolean active;

    private RecyclerView recyclerView;

    private int selIndex;

    /**
     * Create the start intent for this Activity
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, String listUrl, PaymentTheme theme) {
        final Intent intent = new Intent(context, PaymentPageActivity.class);
        intent.putExtra(EXTRA_LISTURL, listUrl);
        intent.putExtra(EXTRA_PAYMENTTHEME, theme);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_LISTURL)) {
            this.listUrl = savedInstanceState.getString(EXTRA_LISTURL);
            this.theme = savedInstanceState.getParcelable(EXTRA_PAYMENTTHEME);
        } else {
            Intent intent = getIntent();
            this.listUrl = intent.getStringExtra(EXTRA_LISTURL);
            this.theme = intent.getParcelableExtra(EXTRA_PAYMENTTHEME);
        }
        setContentView(R.layout.activity_paymentpage);

        // initialize the toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the presenter
        this.presenter = new PaymentPagePresenter(this);

        // initialize the list adapter
        this.adapter = new PaymentListAdapter(this);
        this.adapter.setListener(this);

        this.recyclerView = findViewById(R.id.recyclerview_paymentlist);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_LISTURL, listUrl);
        savedInstanceState.putParcelable(EXTRA_PAYMENTTHEME, theme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        this.active = false;
        presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        this.active = true;
        presenter.onStart();
        presenter.refresh(this.listUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItems(int selIndex, List<PaymentGroup> items) {
        this.selIndex = selIndex;
        adapter.setItems(items);
        recyclerView.scrollToPosition(selIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearItems() {
        adapter.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showCenterMessage(int resId) {
        TextView view = findViewById(R.id.label_center);
        view.setText(resId);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideCenterMessage() {
        findViewById(R.id.label_center).setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abortPayment(String code, String reason) {
        Log.i(TAG, "abortPayment: " + code + ", " + reason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionClicked(PaymentGroup item, int position) {
        Log.i(TAG, "on Action Clicked: " + position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClicked(PaymentGroup item, int position) {

        if (position == this.selIndex) {
            return;
        }
        // first, hide the current selected element
        PaymentListViewHolder holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(this.selIndex);
        if (holder != null) {
            holder.expand(false);
            adapter.notifyItemChanged(position);
        }
        hideKeyboard();
        PaymentGroup curGroup = adapter.getItemFromIndex(this.selIndex);

        // second, expand the new selected element
        holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            holder.expand(true);
            adapter.notifyItemChanged(position);
            recyclerView.scrollToPosition(position);
        }
        this.selIndex = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoading(boolean show) {

        if (!isActive()) {
            return;
        }
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    int getSelected() {
        return selIndex;
    }
    
    String translate(String key, String defValue) {
        return presenter.translate(key, defValue);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
        }
    }
}
