/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.test.espresso.IdlingResource;
import net.optile.payment.R;
import net.optile.payment.model.Interaction;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.PaymentDialogFragment.PaymentDialogListener;
import net.optile.payment.ui.dialog.PaymentDialogHelper;
import net.optile.payment.ui.page.idlingresource.SimpleIdlingResource;

/**
 * The base activity for payment activities.
 */
abstract class BasePaymentActivity extends AppCompatActivity implements PaymentView {

    boolean active;
    ProgressView progressView;

    /** For testing only */
    SimpleIdlingResource closeIdlingResource;
    SimpleIdlingResource dialogIdlingResource;
    boolean closed;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResultIntent(PaymentUI.RESULT_CODE_OK, new PaymentResult("Initializing page."));
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
        closed = false;
        active = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress(boolean visible) {
        if (active) {
            progressView.setVisible(visible);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showWarningMessage(String message) {
        if (active && !TextUtils.isEmpty(message)) {
            PaymentDialogHelper.createSnackbar(getRootView(), message).show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDefaultErrorDialog(PaymentDialogListener listener) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        DialogFragment dialog = PaymentDialogHelper.createDefaultErrorDialog(listener);
        showDialogFragment(dialog, "dialog_defaulterror");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showConnectionErrorDialog(PaymentDialogListener listener) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        DialogFragment dialog = PaymentDialogHelper.createConnectionErrorDialog(listener);
        showDialogFragment(dialog, "dialog_connectionerror");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInteractionDialog(Interaction interaction, PaymentDialogListener listener) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        DialogFragment dialog = PaymentDialogHelper.createInteractionDialog(interaction, listener);
        showDialogFragment(dialog, "dialog_interaction");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaymentResult(int resultCode, PaymentResult result) {
        if (!active) {
            return;
        }
        setResultIntent(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passOnActivityResult(ActivityResult activityResult) {
        if (!active) {
            return;
        }
        setResultIntent(activityResult.resultCode, activityResult.paymentResult);
        supportFinishAfterTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (!active) {
            return;
        }
        supportFinishAfterTransition();
        setOverridePendingTransition();

        // for automated testing
        setCloseIdleState();
    }

    /**
     * Show a dialog fragment to the user
     *
     * @param dialog to be shown
     * @param tag identifies the dialog
     */
    public void showDialogFragment(DialogFragment dialog, String tag) {
        dialog.show(getSupportFragmentManager(), tag);

        // For automated testing
        if (dialogIdlingResource != null) {
            dialogIdlingResource.setIdleState(true);
        }
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
     * Set the PaymentResult indicating that the user has closed the page.
     */
    void setUserClosedPageResult() {
        setResultIntent(PaymentUI.RESULT_CODE_OK, new PaymentResult("Page closed by user."));
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

    /**
     * Set the overridePendingTransition that will be used when moving back to another Activity
     */
    void setOverridePendingTransition() {
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
    }

    /**
     * Only called from test, creates and returns a new dialog IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getDialogIdlingResource() {
        if (dialogIdlingResource == null) {
            dialogIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-dialogIdlingResource");
        }
        dialogIdlingResource.reset();
        return dialogIdlingResource;
    }

    /**
     * Only called from test, creates and returns a new close IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getCloseIdlingResource() {
        if (closeIdlingResource == null) {
            closeIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-closeIdlingResource");
        }
        if (closed) {
            closeIdlingResource.setIdleState(true);
        } else {
            closeIdlingResource.reset();
        }
        return closeIdlingResource;
    }

    /**
     * Set the close idle state for the closeIdlingResource
     */
    void setCloseIdleState() {
        closed = true;
        if (closeIdlingResource != null) {
            closeIdlingResource.setIdleState(true);
        }
    }
}
