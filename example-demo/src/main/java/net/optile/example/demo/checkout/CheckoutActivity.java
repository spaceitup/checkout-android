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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import net.optile.example.demo.R;
import net.optile.example.demo.confirm.ConfirmActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.example.demo.shared.SdkResult;
import net.optile.example.demo.summary.SummaryActivity;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;

/**
 * Activity displaying the checkout page, this page will open the Android SDK payment page.
 */
public final class CheckoutActivity extends BaseActivity implements CheckoutView {
    private CheckoutPresenter presenter;
    private SdkResult sdkResult;

    /**
     * Create an Intent to launch this checkout activity
     *
     * @param context the context
     * @param listUrl url of the current list
     * @return the newly created intent
     */
    public static Intent createStartIntent(Context context, String listUrl) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (TextUtils.isEmpty(listUrl)) {
            throw new IllegalArgumentException("listUrl may not be null or empty");
        }
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(EXTRA_LISTURL, listUrl);
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
        initToolbar();

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
    public void showPaymentSummary() {
        if (!active) {
            return;
        }
        Intent intent = SummaryActivity.createStartIntent(this, listUrl);
        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentConfirmed() {
        if (!active) {
            return;
        }
        Intent intent = ConfirmActivity.createStartIntent(this);
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
        PaymentResult result = PaymentResult.fromResultIntent(data);
        if (result != null) {
            this.sdkResult = new SdkResult(requestCode, resultCode, result);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);
        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }
}
