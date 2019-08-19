/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.DialogHelper;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * The base activity for payment activities.
 */
abstract class BasePaymentActivity extends AppCompatActivity {

    boolean active;
    ProgressView progressView;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResultIntent(PaymentUI.RESULT_CODE_CANCELED, new PaymentResult("Initializing page."));
        setRequestedOrientation(PaymentUI.getInstance().getOrientation());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        active = false;

        if (progressView != null) {
            progressView.onStop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        active = true;
    }

    ThemedDialogFragment createMessageDialog(String message, ThemedDialogListener listener) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setListener(listener);
        dialog.setMessage(message);
        dialog.setNeutralButton(getString(R.string.pmdialog_cancel_button));
        return dialog;
    }

    ThemedDialogFragment createConnectionDialog(ThemedDialogListener listener) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setListener(listener);
        dialog.setMessage(getString(R.string.pmdialog_error_connection));
        dialog.setNeutralButton(getString(R.string.pmdialog_cancel_button));
        dialog.setPositiveButton(getString(R.string.pmdialog_retry_button));
        return dialog;
    }

    /**
     * Initialize the ProgressView, the UI elements needed by the ProgressView must be available in the root view of the activity.
     */
    void initProgressView() {
        progressView = new ProgressView(getRootView(), getPaymentTheme());
        progressView.setSendLabels(getString(R.string.pmprogress_sendheader),
            getString(R.string.pmprogress_sendinfo));
    }

    /**
     * Get the current PaymentTheme from the PaymentUI.
     *
     * @return the current PaymentTheme
     */
    PaymentTheme getPaymentTheme() {
        return PaymentUI.getInstance().getPaymentTheme();
    }

    /**
     * Get the root view of this Activity.
     *
     * @return the root view
     */
    View getRootView() {
        return ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * Set the action bar with a title and optional back arrow.
     *
     * @param title of the action bar
     * @param homeEnabled when true show the back arrow, false hide the arrow.
     */
    void setActionBar(String title, boolean homeEnabled) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }
        actionBar.setTitle(title);
        actionBar.setHomeButtonEnabled(homeEnabled);
        actionBar.setDisplayHomeAsUpEnabled(homeEnabled);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    /**
     * Show a snackbar to the user with the given message.
     *
     * @param message to be shown in the snackbar
     */
    void showSnackbar(String message) {
        if (!TextUtils.isEmpty(message)) {
            DialogHelper.createSnackbar(getRootView(), message).show();
        }
    }

    /**
     * Set the PaymentResult indicating that the user has closed the page.
     */
    void setUserClosedPageResult() {
        setResultIntent(PaymentUI.RESULT_CODE_CANCELED, new PaymentResult("Page closed by user."));
    }

    /**
     * Set the ActivityResult with the given resultCode and PaymentResult.
     *
     * @param resultCode of the ActivityResult
     * @param result to be added as extra to the intent
     */
    void setResultIntent(int resultCode, PaymentResult result) {
        Intent intent = new Intent();
        result.putInto(intent);
        setResult(resultCode, intent);
    }
}
