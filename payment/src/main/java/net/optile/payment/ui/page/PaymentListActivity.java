/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import java.util.Map;

import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.dialog.ThemedDialogFragment.ThemedDialogListener;
import net.optile.payment.ui.list.PaymentList;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.util.PaymentUtils;

/**
 * The PaymentListActivity showing available payment methods in a list.
 */
public final class PaymentListActivity extends BasePaymentActivity implements PaymentListView {

    private PaymentListPresenter presenter;
    private PaymentList paymentList;
    private int cachedListIndex;

    /**
     * Create the start intent for this PaymentListActivity.
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context) {
        return new Intent(context, PaymentListActivity.class);
    }

    /**
     * Get the transition used when this Activity is being started
     *
     * @return the start transition of this activity
     */
    public static int getStartTransition() {
        return R.anim.no_animation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getPaymentTheme().getListParameters().getPageTheme();
        if (theme != 0) {
            setTheme(theme);
        }
        this.cachedListIndex = -1;

        setContentView(R.layout.activity_paymentlist);
        setActionBar(getString(R.string.pmpage_title), true);

        initProgressView();
        initPaymentList();
        this.presenter = new PaymentListPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        this.cachedListIndex = paymentList.getSelected();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.onStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setUserClosedPageResult();
                close();
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        setUserClosedPageResult();
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
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
    public void clearList() {
        if (!active) {
            return;
        }
        paymentList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentSession(PaymentSession session) {
        if (!active) {
            return;
        }
        progressView.setVisible(false);
        setActionBar(getString(R.string.pmpage_title), true);
        paymentList.showPaymentSession(session, cachedListIndex);
        this.cachedListIndex = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showChargePaymentScreen(int requestCode, Operation operation) {
        Intent intent = ChargePaymentActivity.createStartIntent(this, operation);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(ChargePaymentActivity.getStartTransition(), R.anim.no_animation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgress() {
        if (!active) {
            return;
        }
        progressView.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        Log.i("pay", "closing PaymentListActivity");
        if (!active) {
            return;
        }
        supportFinishAfterTransition();
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation);
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

    public void onActionClicked(PaymentCard item, Map<String, FormWidget> widgets) {
        presenter.onActionClicked(item, widgets);
    }

    private void initPaymentList() {
        TextView empty = findViewById(R.id.label_empty);
        PaymentUtils.setTextAppearance(empty, getPaymentTheme().getListParameters().getEmptyListLabelStyle());
        this.paymentList = new PaymentList(this, findViewById(R.id.recyclerview_paymentlist), empty);
    }
}
