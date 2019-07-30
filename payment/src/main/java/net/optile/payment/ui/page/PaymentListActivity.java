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
import android.view.MenuItem;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.form.Operation;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
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
                closePage();
                return true;
        }
        return false;
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
        overridePendingTransition(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        PaymentResult result = PaymentUI.getPaymentResult(data);
        if (result == null) {
            return;
        }
        presenter.setActivityResult(new ActivityResult(requestCode, resultCode, result));
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
    public void showProgressView() {
        if (!active) {
            return;
        }
        progressView.setVisible(true);
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
        overridePendingTransition(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passOnPaymentResult(int resultCode, PaymentResult result) {
        if (!active) {
            return;
        }
        setActivityResult(resultCode, result);
        supportFinishAfterTransition();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void showProcessPaymentScreen(int requestCode, Operation operation) {
        Intent intent = ProcessPaymentActivity.createStartIntent(this, operation);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.fade_in, R.anim.no_animation);
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
        dialog.show(getSupportFragmentManager(), "paymentlist_dialog");
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
    public void showWarningMessage(String message) {
        if (!active) {
            return;
        }
        showSnackbar(message);
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
