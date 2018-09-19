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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import net.optile.payment.R;
import android.content.Intent;
import android.content.Context;
import java.util.List;
import net.optile.payment.ui.PaymentTheme;

/**
 * The PaymentPageActivity showing available payment methods
 */
public final class PaymentPageActivity extends AppCompatActivity implements PaymentPageView {

    private final static String EXTRA_LISTURL = "extra_listurl";
    private final static String EXTRA_PAYMENTTHEME = "extra_paymenttheme";

    private PaymentPagePresenter presenter;

    private PaymentListAdapter adapter;
    
    private String listUrl;

    private PaymentTheme theme;

    private boolean active;
    
    /** 
     * Create the start intent for this Activity
     * 
     * @param context Context to create the intent
     * @return the newly created start intent
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
    public void onCreate(final Bundle savedInstanceState) {
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

        initToolbar();
        initPresenter();
        initPaymentList();
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    
    private void initPresenter() {
        this.presenter = new PaymentPagePresenter(this);
    }

    private void initPaymentList() {
        this.adapter = new PaymentListAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerview_paymentlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter.setListener(this);
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
    public void setItems(List<PaymentListItem> items) {
        adapter.setItems(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abortPayment(String code, String reason) {
    }
}
