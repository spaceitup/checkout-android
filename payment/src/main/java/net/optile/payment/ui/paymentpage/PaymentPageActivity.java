/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.paymentpage;

import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.PaymentTheme;

/**
 * The PaymentPageActivity showing available payment methods
 */
public final class PaymentPageActivity extends AppCompatActivity implements PaymentPageView {

    private final static String TAG = "pay_PaymentPageActivity";
    private final static String EXTRA_LISTURL = "extra_listurl";
    private final static String EXTRA_PAYMENTTHEME = "extra_paymenttheme";

    private PaymentPagePresenter presenter;

    private String listUrl;

    private PaymentTheme theme;

    private boolean active;

    private PaymentList paymentList;

    /**
     * Create the start intent for this Activity
     *
     * @param context Context to create the intent
     * @return newly created start intent
     */
    public static Intent createStartIntent(Context context, String listUrl, PaymentTheme theme) {
        final Intent intent = new Intent(context, PaymentPageActivity.class);
        intent.putExtra(EXTRA_LISTURL, listUrl);
        intent.putExtra(EXTRA_PAYMENTTHEME, theme);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_LISTURL)) {
            this.listUrl = savedInstanceState.getString(EXTRA_LISTURL);
            this.theme = savedInstanceState.getParcelable(EXTRA_PAYMENTTHEME);
        } else {
            Intent intent = getIntent();
            this.listUrl = intent.getStringExtra(EXTRA_LISTURL);
            this.theme = intent.getParcelableExtra(EXTRA_PAYMENTTHEME);
        }
        setContentView(R.layout.activity_paymentpage);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.presenter = new PaymentPagePresenter(this);
        this.paymentList = new PaymentList(this, findViewById(R.id.recyclerview_paymentlist));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_LISTURL, listUrl);
        savedInstanceState.putParcelable(EXTRA_PAYMENTTHEME, theme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        this.active = false;
        presenter.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        this.active = true;
        presenter.onStart();
        presenter.loadPaymentSession(this.listUrl);
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
    public void showPaymentSession(PaymentSession session) {
        paymentList.setPaymentSession(session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        paymentList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showCenterMessage(int resId) {
        TextView view = findViewById(R.id.label_center);
        view.setText(resId);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideCenterMessage() {
        findViewById(R.id.label_center).setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abortPayment(String code, String reason, String message) {
        Log.i(TAG, "abortPayment: " + code + ", " + reason + ", " + message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoading(boolean show) {

        if (!isActive()) {
            return;
        }
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        paymentList.setVisible(!show);
    }

    void makeChargeRequest(PaymentGroup group, Map<String, FormWidget> widgets) {
        presenter.makeChargeRequest(widgets, group);
    }
}
