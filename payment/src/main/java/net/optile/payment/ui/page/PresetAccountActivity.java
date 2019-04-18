/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.DialogHelper;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.list.PaymentList;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.util.PaymentUtils;

/**
 * The PresetAccountActivity showing available payment methods
 */
public final class PresetAccountActivity extends AppCompatActivity implements PresetAccountView {

    private PresetAccountPresenter presenter;
    private boolean active;
    private ProgressView progress;
    
    /**
     * Create the start intent for this PresetAccountActivity.
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context) {
        return new Intent(context, PresetAccountActivity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaymentResult result = new PaymentResult("Inializing Payment Page.");
        setActivityResult(PaymentUI.RESULT_CODE_CANCELED, result);

        PaymentTheme theme = PaymentUI.getInstance().getPaymentTheme();
        initPageTheme(theme);

        setContentView(R.layout.activity_presetaccount);
        setRequestedOrientation(PaymentUI.getInstance().getOrientation());

        initActionBar(getString(R.string.pmpage_title), true);
        initProgressView(theme);
        this.presenter = new PresetAccountPresenter(this);
    }

    private void initProgressView(PaymentTheme theme) {
        View rootView = findViewById(R.id.layout_paymentpage);
        progress = new ProgressView(rootView, theme);
        progress.setSendLabels(getString(R.string.pmprogress_sendheader),
            getString(R.string.pmprogress_sendinfo));
    }

    private void initPageTheme(PaymentTheme theme) {
        int pageTheme = theme.getPageParameters().getPageTheme();

        if (pageTheme != 0) {
            setTheme(pageTheme);
        }
    }

    private void initActionBar(String title, boolean homeEnabled) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setHomeButtonEnabled(homeEnabled);
            actionBar.setDisplayHomeAsUpEnabled(homeEnabled);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        this.active = false;
        this.presenter.onStop();
        this.progress.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        this.active = true;
        presenter.onStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        if (presenter.onBackPressed()) {
            return;
        }
        setUserClosedPageResult();
        super.onBackPressed();
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
    public Context getContext() {
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRes(int resId) {
        return getString(resId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress(boolean show, int style) {
        if (!isActive()) {
            return;
        }
        if (show) {
            if (style == ProgressView.SEND) {
                initActionBar(getString(R.string.pmprogress_sendtitle), false);
            }
            progress.setStyle(style);
            progress.setVisible(true);
        } else {
            initActionBar(getString(R.string.pmpage_title), true);
            progress.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closePage() {
        if (!isActive()) {
            return;
        }
        finish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaymentResult(int resultCode, PaymentResult result) {
        if (!isActive()) {
            return;
        }
        setActivityResult(resultCode, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDialog(ThemedDialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "paymentpage_dialog");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showSnackbar(String message) {

        if (TextUtils.isEmpty(message)) {
            return;
        }
        Snackbar snackbar = DialogHelper.createSnackbar(findViewById(R.id.layout_paymentpage), message);
        snackbar.show();
    }

    public void onActionClicked(PaymentCard item, Map<String, FormWidget> widgets) {
        presenter.onActionClicked(item, widgets);
    }

    private void setUserClosedPageResult() {
        PaymentResult result = new PaymentResult("Payment Page closed by user.");
        setActivityResult(PaymentUI.RESULT_CODE_CANCELED, result);
    }

    private void setActivityResult(int resultCode, PaymentResult result) {
        Intent intent = new Intent();
        intent.putExtra(PaymentUI.EXTRA_PAYMENT_RESULT, result);
        setResult(resultCode, intent);
    }
}
