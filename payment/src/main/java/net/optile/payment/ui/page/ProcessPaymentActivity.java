/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import java.net.URL;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import net.optile.payment.R;
import net.optile.payment.form.Operation;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;

/**
 * The ProcessPaymentActivity is the view displaying the loading animation while posting the operation.
 * The presenter of this view will post the PresetAccount operation to the Payment API.
 */
public final class ProcessPaymentActivity extends BasePaymentActivity implements ProcessPaymentView {

    private ProcessPaymentPresenter presenter;
    private Operation operation;

    /**
     * Create the start intent for this ProcessPaymentActivity
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
        Intent intent = new Intent(context, ProcessPaymentActivity.class);
        intent.putExtra(EXTRA_OPERATION, operation);
        return intent;
    }

    /**
     * Create the start intent for this ProcessPaymentActivity
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
        return createStartIntent(context, new Operation(url));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getPaymentTheme().getProcessParameters().getPageTheme();
        if (theme != 0) {
            setTheme(theme);
        }
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (bundle != null) {
            this.operation = bundle.getParcelable(EXTRA_OPERATION);
        }
        setContentView(R.layout.activity_processpayment);
        setActionBar(getString(R.string.pmprogress_sendtitle), false);
        initProgressView();
        this.presenter = new ProcessPaymentPresenter(this);
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
    public void showProgressView() {
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
    public Context getContext() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closePage() {
        if (!active) {
            return;
        }
        supportFinishAfterTransition();
        overridePendingTransition(R.anim.no_animation, R.anim.fade_out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaymentResult(int resultCode, PaymentResult result) {
        if (!active) {
            return;
        }
        setActivityResult(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgressDialog(ThemedDialogFragment dialog) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        dialog.show(getSupportFragmentManager(), "presetaccount_dialog");
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
}
