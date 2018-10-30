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

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import net.optile.example.R;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.model.Interaction;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import android.support.design.widget.Snackbar;

/**
 * Activity for performing a checkout payment
 */
public final class CheckoutActivity extends AppCompatActivity implements CheckoutView {

    private static String TAG = "payment_CheckoutActivity";
    private static int PAYMENT_REQUEST_CODE = 1;

    private CheckoutPresenter presenter;

    private boolean active;

    private CheckoutResult checkoutResult;
    
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

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button = findViewById(R.id.button_checkout);
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

        if (paymentSuccess) {
            handlePaymentSuccess();
        } else if (paymentResult != null) {
            handlePaymentFailed();
        }
    }

    private void handlePaymentSuccess() {
        this.paymentSuccess = false;
        if (this.paymentResult != null) {
            
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSuccessSnackbar();
                }
            }, 500);
    }

    private void handlePaymentFailed() {
        this.paymentSuccess = false;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSuccessSnackbar();
                }
            }, 500);
    }
    
    private void showSuccessSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_activity),
                                          getString(R.string.payment_success),
                                          Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showError(String error) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setTitle(getString(R.string.dialog_error_title));
        dialog.setMessage(String.format(getString(R.string.dialog_error_message), error));
        dialog.setButton(getString(R.string.dialog_error_button), null);
        dialog.show(getSupportFragmentManager(), "error_dialog");
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
        
        if (data != null && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
            result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
        }
        if (resultCode == Active.RESULT_OK) {
            this.paymentSuccess = true;
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
        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }

    private void onButtonClicked() {
        presenter.createPaymentSession(this);
    }
}
