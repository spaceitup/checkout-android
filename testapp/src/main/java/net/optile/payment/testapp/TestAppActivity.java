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

package net.optile.payment.testapp;

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
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import net.optile.payment.ui.theme.PaymentTheme;
import android.text.TextUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * This is the main Activity of this Checkout example app. 
 * There are two buttons visible, one button is used to launch the Android PaymentPage with the default optile theme,
 * and one with a custom theme defined in this example app.
 */
public final class TestAppActivity extends AppCompatActivity implements TestAppView {

    private static int PAYMENT_REQUEST_CODE = 1;

    private TestAppPresenter presenter;
    private TestAppResult testAppResult;
    private boolean setCustomTheme;

    /**
     * Create an Intent to launch this checkout activity
     *
     * @param context the context
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        return new Intent(context, TestAppActivity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.button_default);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDefaultThemePage();
            }
        });
        button = findViewById(R.id.button_custom);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCustomThemePage();
            }
        });
        this.presenter = new TestAppPresenter(this);
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

        if (testAppResult != null) {
            presenter.handleCheckoutResult(testAppResult);
            this.testAppResult = null;
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
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.dialog_error_title);
        alertDialog.setMessage(String.format(getString(R.string.dialog_error_message), message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_error_button),
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialog.show();
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
        this.testAppResult = new TestAppResult(success, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openPaymentPage(String listUrl) {
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);

        PaymentTheme theme;
        if (setCustomTheme) {
            theme = TestAppTheme.createCustomTheme();
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

    private void openDefaultThemePage() {
        this.setCustomTheme = false;
        presenter.createPaymentSession(this);
    }

    private void openCustomThemePage() {
        this.setCustomTheme = true;
        presenter.createPaymentSession(this);
    }
}
