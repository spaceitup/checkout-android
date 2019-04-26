/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.checkout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import net.optile.example.demo.R;
import net.optile.example.demo.confirm.ConfirmActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.example.demo.shared.DemoSettings;
import net.optile.example.demo.shared.SdkResult;
import net.optile.example.demo.summary.SummaryActivity;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * Activity displaying the checkout page, this will create a new PaymentSession and open the payment page of the Android SDK.
 */
public final class CheckoutActivity extends BaseActivity implements CheckoutView {

    private CheckoutPresenter presenter;
    private SdkResult sdkResult;

    /**
     * Create an Intent to launch this checkout activity
     *
     * @param context the context
     * @param settings the demo settings
     * @return the newly created intent
     */
    public static Intent createStartIntent(Context context, DemoSettings settings) {

        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (settings == null) {
            throw new IllegalArgumentException("settings may not be null");
        }
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(EXTRA_SETTINGS, settings);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DefaultCollapsingToolbarTheme);
        setContentView(R.layout.activity_checkout);
        initToolbar(settings);

        Button button = findViewById(R.id.button_checkout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClicked();
            }
        });
        this.presenter = new CheckoutPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        this.presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();

        if (sdkResult != null) {
            presenter.handleSdkResult(sdkResult);
            this.sdkResult = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentError(String error) {

        if (!active) {
            return;
        }
        showErrorDialog(error);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentSuccess() {

        if (!active) {
            return;
        }
        Intent intent;
        if (settings.getSummary()) {
            intent = SummaryActivity.createStartIntent(this, listUrl, settings);
        } else {
            intent = ConfirmActivity.createStartIntent(this, settings);
        }
        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closePayment() {

        if (!active) {
            return;
        }
        supportFinishAfterTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != PAYMENT_REQUEST_CODE) {
            return;
        }
        if (data != null && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
            PaymentResult result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
            this.sdkResult = new SdkResult(requestCode, resultCode, result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openPaymentPage(String listUrl) {

        if (!active) {
            return;
        }
        this.listUrl = listUrl;
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);
        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context getContext() {
        return this;
    }

    private void initToolbar(DemoSettings settings) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.checkout_collapsed_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_medium);
        layout.setCollapsedTitleTypeface(typeface);
        layout.setExpandedTitleTypeface(typeface);
    }

    private void onButtonClicked() {

        if (TextUtils.isEmpty(listUrl)) {
            presenter.createPaymentSession(settings);
        } else {
            openPaymentPage(listUrl);
        }
    }
}
