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
import net.optile.payment.R;
import android.content.Intent;
import android.content.Context;

import net.optile.payment.ui.PaymentTheme;

/**
 * The PaymentPageActivity showing available payment methods
 */
public final class PaymentPageActivity extends AppCompatActivity implements PaymentPageView {

    private static String TAG = "payment_PaymentPageActivity";
    private static String EXTRA_LISTURL = "extra_listurl";
    private static String EXTRA_PAYMENTTHEME = "extra_paymenttheme";

    /** The url pointing to the ListResult in the Payment API */
    private String listUrl;

    /** The theme to apply to this PaymentPage */
    private PaymentTheme theme;

    /** The presenter of this view */
    private PaymentPagePresenter presenter;

    /** Is this view currently active or not */
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
    protected void onCreate(final Bundle savedInstanceState) {
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

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new PaymentPagePresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_LISTURL, listUrl);
        savedInstanceState.putString(EXTRA_PAYMENTTHEME, theme);
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
        presenter.onStart(this.listUrl);
    }

    /** 
     * Is this view currently active
     * 
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return this.active;
    }
}
