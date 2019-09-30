/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import static net.optile.payment.localization.LocalizationKey.CHARGE_TEXT;
import static net.optile.payment.localization.LocalizationKey.CHARGE_TITLE;

import java.net.URL;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import net.optile.payment.R;
import net.optile.payment.form.Operation;
import net.optile.payment.localization.Localization;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.page.idlingresource.SimpleIdlingResource;

/**
 * The ChargePaymentActivity is the view displaying the loading animation while posting the operation.
 * The presenter of this view will post the PresetAccount operation to the Payment API.
 */
public final class ChargePaymentActivity extends BasePaymentActivity implements ChargePaymentView {

    private final static String EXTRA_OPERATION = "operation";
    private ChargePaymentPresenter presenter;
    private Operation operation;

    // For automated UI testing
    private boolean chargeCompleted;
    private SimpleIdlingResource chargeIdlingResource;

    /**
     * Create the start intent for this ChargePaymentActivity
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, Operation operation) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation may not be null");
        }
        Intent intent = new Intent(context, ChargePaymentActivity.class);
        intent.putExtra(EXTRA_OPERATION, operation);
        return intent;
    }

    /**
     * Create the start intent for this ChargePaymentActivity
     *
     * @param context Context to create the intent
     * @param account the preset account that should be processed
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, PresetAccount account) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account may not be null");
        }
        Map<String, URL> links = account.getLinks();
        URL url = links != null ? links.get("operation") : null;

        if (url == null) {
            throw new IllegalArgumentException("PresetAccount does not contain an operation url");
        }
        return createStartIntent(context, new Operation(account.getCode(), url));
    }

    /**
     * Get the transition used when this Activity is being started
     *
     * @return the start transition of this activity
     */
    public static int getStartTransition() {
        return R.anim.fade_in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getPaymentTheme().getChargeParameters().getPageTheme();
        if (theme != 0) {
            setTheme(theme);
        }
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (bundle != null) {
            this.operation = bundle.getParcelable(EXTRA_OPERATION);
        }
        setContentView(R.layout.activity_chargepayment);
        progressView = new ProgressView(getRootView(), getPaymentTheme());
        progressView.setSendLabels(Localization.translate(CHARGE_TITLE),
                                   Localization.translate(CHARGE_TEXT));
        this.presenter = new ChargePaymentPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        presenter.onStop();
        progressView.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart(operation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        PaymentResult result = PaymentResult.fromResultIntent(data);
        if (result != null) {
            presenter.setActivityResult(new ActivityResult(requestCode, resultCode, result));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        if (!presenter.onBackPressed()) {
            return;
        }
        setUserClosedPageResult();
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation, R.anim.fade_out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress() {
        if (!active) {
            return;
        }
        progressView.setStyle(ProgressView.SEND);
        progressView.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showWarningMessage(String message) {
        if (!active) {
            return;
        }
        showSnackbar(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessageDialog(String message, ThemedDialogListener listener) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        ThemedDialogFragment dialog = createMessageDialog(message, listener);
        dialog.show(getSupportFragmentManager(), "dialog_message");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showConnectionDialog(ThemedDialogListener listener) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        ThemedDialogFragment dialog = createConnectionDialog(listener);
        dialog.show(getSupportFragmentManager(), "dialog_connection");
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
    public void close() {
        if (!active) {
            return;
        }
        supportFinishAfterTransition();
        overridePendingTransition(R.anim.no_animation, R.anim.fade_out);

        // For automated UI testing
        chargeCompleted = true;
        if (chargeIdlingResource != null) {
            chargeIdlingResource.setIdleState(true);
        }
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
     * Only called from test, creates and returns a new IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getChargeIdlingResource() {
        if (chargeIdlingResource == null) {
            chargeIdlingResource = new SimpleIdlingResource("chargeIdlingResource");
        }
        if (chargeCompleted) {
            chargeIdlingResource.setIdleState(chargeCompleted);
        }
        return chargeIdlingResource;
    }
}
