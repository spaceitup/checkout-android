/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.shop.summary;

import java.net.URL;
import java.util.Map;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.test.espresso.IdlingResource;
import net.optile.example.shop.R;
import net.optile.example.shop.confirm.ConfirmActivity;
import net.optile.example.shop.settings.SettingsActivity;
import net.optile.example.shop.shared.BaseActivity;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.page.idlingresource.SimpleIdlingResource;
import net.optile.payment.util.NetworkLogoLoader;
import net.optile.payment.util.PaymentUtils;

/**
 * Activity displaying the summary page with the Pay and Edit button.
 */
public final class SummaryActivity extends BaseActivity implements SummaryView {

    private SummaryPresenter presenter;
    private PresetAccount presetAccount;
    private TextView presetTitle;
    private TextView presetSubtitle;

    // For automated UI Testing
    private boolean loadCompleted;
    private SimpleIdlingResource loadIdlingResource;

    public Activity getActivity() {
        return this;
    }

    /**
     * Create an Intent to launch this checkout activity
     *
     * @param context the context
     * @param listUrl the URL pointing to the list
     * @return the newly created intent
     */
    public static Intent createStartIntent(Context context, String listUrl) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (TextUtils.isEmpty(listUrl)) {
            throw new IllegalArgumentException("listUrl may not be null or empty");
        }
        Intent intent = new Intent(context, SummaryActivity.class);
        intent.putExtra(EXTRA_LISTURL, listUrl);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initToolbar();
        presetTitle = findViewById(R.id.label_title);
        presetSubtitle = findViewById(R.id.label_subtitle);

        Button edit = findViewById(R.id.button_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPaymentList();
            }
        });
        Button button = findViewById(R.id.button_pay);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPayClicked();
            }
        });
        this.presenter = new SummaryPresenter(this);
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
        if (sdkResult != null) {
            presenter.handleSdkResult(sdkResult);
            this.sdkResult = null;
        } else {
            presenter.loadPaymentDetails(listUrl);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListUrl() {
        return listUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentConfirmation() {
        if (active) {
            startActivity(ConfirmActivity.createStartIntent(this));
            supportFinishAfterTransition();
            setResultHandledIdleState();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentDetails(PresetAccount presetAccount) {
        if (!active) {
            return;
        }
        showLoading(false);
        this.presetAccount = presetAccount;
        AccountMask mask = presetAccount.getMaskedAccount();
        ImageView view = findViewById(R.id.image_logo);
        URL logoUrl = getLink(presetAccount, "logo");
        String networkCode = presetAccount.getCode();
        NetworkLogoLoader.loadNetworkLogo(view, networkCode, logoUrl);

        presetSubtitle.setVisibility(View.GONE);
        if (mask != null) {
            setAccountMask(mask, presetAccount.getMethod());
        } else {
            presetTitle.setText(presetAccount.getCode());
        }
        // For automated UI testing
        this.loadCompleted = true;
        if (loadIdlingResource != null) {
            loadIdlingResource.setIdleState(loadCompleted);
        }
    }

    private void setAccountMask(AccountMask mask, String method) {
        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                presetTitle.setText(mask.getNumber());
                String date = PaymentUtils.getExpiryDateString(mask);
                if (date != null) {
                    presetSubtitle.setVisibility(View.VISIBLE);
                    presetSubtitle.setText(date);
                }
                break;
            default:
                presetTitle.setText(mask.getDisplayLabel());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopPaymentWithErrorMessage() {
        if (!active) {
            return;
        }
        showErrorDialog(R.string.dialog_error_message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoading(boolean val) {
        if (!active) {
            return;
        }
        ProgressBar bar = findViewById(R.id.progressbar_load);
        View content = findViewById(R.id.layout_content);

        if (val) {
            bar.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        } else {
            bar.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onErrorDialogClosed() {
        startActivity(SettingsActivity.createStartIntent(this));
        supportFinishAfterTransition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (active) {
            supportFinishAfterTransition();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentList() {
        if (active) {
            PaymentUI paymentUI = PaymentUI.getInstance();
            paymentUI.showPaymentPage(this, EDIT_REQUEST_CODE);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.summary_collapsed_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_medium);
        layout.setCollapsedTitleTypeface(typeface);
        layout.setExpandedTitleTypeface(typeface);
    }

    private void onPayClicked() {
        if (presetAccount != null) {
            PaymentUI paymentUI = PaymentUI.getInstance();
            paymentUI.chargePresetAccount(this, PAYMENT_REQUEST_CODE, presetAccount);
        }
    }

    private URL getLink(PresetAccount account, String name) {
        Map<String, URL> links = account.getLinks();
        return links != null ? links.get(name) : null;
    }

    /**
     * Only called from test, creates and returns a new IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getLoadIdlingResource() {
        if (loadIdlingResource == null) {
            loadIdlingResource = new SimpleIdlingResource("summaryLoadIdlingResource");
        }
        if (loadCompleted) {
            loadIdlingResource.setIdleState(loadCompleted);
        }
        return loadIdlingResource;
    }
}
