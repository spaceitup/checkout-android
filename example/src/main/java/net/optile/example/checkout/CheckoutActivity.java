/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example.checkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import net.optile.example.R;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import net.optile.payment.ui.theme.ButtonParameters;
import net.optile.payment.ui.theme.CheckBoxParameters;
import net.optile.payment.ui.theme.IconParameters;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.DateParameters;
import net.optile.payment.ui.theme.ListParameters;
import net.optile.payment.ui.theme.MessageParameters;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * Activity for performing a checkout payment
 */
public final class CheckoutActivity extends AppCompatActivity implements CheckoutView {

    private static String TAG = "payment_CheckoutActivity";
    private static int PAYMENT_REQUEST_CODE = 1;

    private CheckoutPresenter presenter;
    private boolean active;
    private CheckoutResult checkoutResult;
    private boolean customTheme;
    
    /**
     * Create an Intent to launch this activity
     *
     * @param context the context
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        return new Intent(context, CheckoutActivity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.button_default);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClicked(false);
            }
        });

        button = findViewById(R.id.button_custom);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClicked(true);
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
        this.active = false;
        this.presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        this.active = true;

        if (checkoutResult != null) {
            presenter.handleCheckoutResult(checkoutResult);
            this.checkoutResult = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentSuccess() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSnackbar(R.string.payment_success);
            }
        }, 500);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentError(String message) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setTitle(getString(R.string.dialog_error_title));
        dialog.setMessage(String.format(getString(R.string.dialog_error_message), message));
        dialog.setNeutralButton(getString(R.string.dialog_error_button));
        dialog.show(getSupportFragmentManager(), "checkout_dialog");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != PAYMENT_REQUEST_CODE) {
            return;
        }
        PaymentResult result = null;
        boolean success = false;

        // The PaymentResult may be null if the user cancelled the PaymentPage without making any charge
        // request to be optile Payment API.
        if (data != null && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
            result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
        }
        if (resultCode == Activity.RESULT_OK) {
            success = true;
        }
        this.checkoutResult = new CheckoutResult(success, result);
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
    public void openPaymentPage(String listUrl) {
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);

        PaymentTheme theme = null;
        if (customTheme) {
            theme = CheckoutTheme.createCustomTheme();
        } else {
            theme = PaymentTheme.createDefault();
        }
        paymentUI.setPaymentTheme(theme);
        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }

    private void showSnackbar(int resId) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_activity),
            getString(resId), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void onButtonClicked(boolean customTheme) {
        this.customTheme = customTheme;
        presenter.createPaymentSession(this);
    }
}
