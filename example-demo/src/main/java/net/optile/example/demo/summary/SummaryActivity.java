/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.summary;

import java.net.URL;
import java.util.Map;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;
import net.optile.payment.ui.page.idlingresource.SimpleIdlingResource;

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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import net.optile.example.demo.R;
import net.optile.example.demo.confirm.ConfirmActivity;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.example.demo.shared.SdkResult;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.util.ImageHelper;
import net.optile.payment.util.PaymentUtils;

/**
 * Activity displaying the summary page with the Pay and Edit button.
 */
public final class SummaryActivity extends BaseActivity implements SummaryView {

    private SummaryPresenter presenter;
    private SdkResult sdkResult;
    private PresetAccount presetAccount;
    private TextView presetTitle;
    private TextView presetSubtitle;    

    // For automated UI Testing
    private boolean loadCompleted;    
    private SimpleIdlingResource loadIdlingResource;
    
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
        setTheme(R.style.DefaultCollapsingToolbarTheme);
        setContentView(R.layout.activity_summary);
        initToolbar();
        presetTitle = findViewById(R.id.label_title);
        presetSubtitle = findViewById(R.id.label_subtitle);        

        View edit = findViewById(R.id.text_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onEditClicked();
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
    public void showPaymentSuccess() {
        if (!active) {
            return;
        }
        Intent intent = ConfirmActivity.createStartIntent(this);
        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPaymentDetails(PresetAccount presetAccount, String method) {

        if (!active) {
            return;
        }
        showLoading(false);
        this.presetAccount = presetAccount;
        AccountMask mask = presetAccount.getMaskedAccount();
        ImageView view = findViewById(R.id.image_logo);
        URL url = getLink(presetAccount, "logo");

        if (url != null) {
            ImageHelper.getInstance().loadImage(view, url);
        }
        presetSubtitle.setVisibility(View.GONE);
        if (mask != null) {
            setAccountMask(mask, method);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PAYMENT_REQUEST_CODE && requestCode != EDIT_REQUEST_CODE) {
            return;
        }
        PaymentResult result = PaymentResult.fromResultIntent(data);
        if (result != null) {
            this.sdkResult = new SdkResult(requestCode, resultCode, result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closePayment(String message) {
        if (!active) {
            return;
        }
        if (message != null) {
            showErrorDialog(message);
        } else {
            openSettingsScreen();
        }
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
        openSettingsScreen();
    }

    private void openSettingsScreen() {
        Intent intent = SettingsActivity.createStartIntent(this);
        startActivity(intent);
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

    private void onEditClicked() {
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.showPaymentPage(this, EDIT_REQUEST_CODE);
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
