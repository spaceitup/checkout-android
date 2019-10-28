/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import static net.optile.payment.localization.LocalizationKey.BUTTON_CANCEL;
import static net.optile.payment.localization.LocalizationKey.BUTTON_RETRY;
import static net.optile.payment.localization.LocalizationKey.ERROR_CONNECTION;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import net.optile.payment.localization.Localization;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.DialogHelper;
import net.optile.payment.ui.dialog.MessageDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;

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
        dialog.setNeutralButton(Localization.translate(BUTTON_CANCEL));
        return dialog;
    }

    ThemedDialogFragment createConnectionDialog(ThemedDialogListener listener) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setListener(listener);
        dialog.setMessage(Localization.translate(ERROR_CONNECTION));
        dialog.setNeutralButton(Localization.translate(BUTTON_CANCEL));
        dialog.setPositiveButton(Localization.translate(BUTTON_RETRY));
        return dialog;
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
