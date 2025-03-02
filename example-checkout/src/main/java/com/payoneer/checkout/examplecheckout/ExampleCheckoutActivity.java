/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package com.payoneer.checkout.examplecheckout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.ui.PaymentActivityResult;
import com.payoneer.checkout.ui.PaymentResult;
import com.payoneer.checkout.ui.PaymentTheme;
import com.payoneer.checkout.ui.PaymentUI;
import com.payoneer.checkout.ui.page.idlingresource.SimpleIdlingResource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

/**
 * This is the main Activity of this example app demonstrating how to use the Checkout SDK
 */
public final class ExampleCheckoutActivity extends AppCompatActivity {

    private final static int PAYMENT_REQUEST_CODE = 1;
    private PaymentActivityResult activityResult;
    private EditText listInput;
    private View resultLayout;
    private SwitchMaterial themeSwitch;
    private TextView resultHeaderView;
    private TextView resultInfoView;
    private TextView resultCodeView;
    private TextView interactionCodeView;
    private TextView interactionReasonView;
    private TextView paymentErrorView;
    private String listUrl;
    private SimpleIdlingResource resultHandledIdlingResource;
    private boolean resultHandled;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examplecheckout);

        themeSwitch = findViewById(R.id.switch_theme);
        listInput = findViewById(R.id.input_listurl);
        resultLayout = findViewById(R.id.layout_result);
        resultHeaderView = findViewById(R.id.label_resultheader);
        resultCodeView = findViewById(R.id.text_resultcode);
        resultInfoView = findViewById(R.id.text_resultinfo);
        interactionCodeView = findViewById(R.id.text_interactioncode);
        interactionReasonView = findViewById(R.id.text_interactionreason);
        paymentErrorView = findViewById(R.id.text_paymenterror);

        Button button = findViewById(R.id.button_action);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openPaymentPage();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        resultHandled = false;
        if (activityResult != null) {
            showPaymentActivityResult(activityResult);
            setResultHandledIdleState();
        }
        if (listUrl != null) {
            listInput.setText(listUrl);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYMENT_REQUEST_CODE) {
            activityResult = PaymentActivityResult.fromActivityResult(requestCode, resultCode, data);
        }
    }

    private void clearPaymentActivityResult() {
        resultHeaderView.setVisibility(View.GONE);
        resultLayout.setVisibility(View.GONE);
        this.activityResult = null;
    }

    private void showPaymentActivityResult(PaymentActivityResult sdkResult) {
        int resultCode = sdkResult.getResultCode();
        resultHeaderView.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.VISIBLE);
        setText(resultCodeView, PaymentActivityResult.resultCodeToString(resultCode));

        String info = null;
        String code = null;
        String reason = null;
        String error = null;
        PaymentResult paymentResult = sdkResult.getPaymentResult();

        if (paymentResult != null) {
            info = paymentResult.getResultInfo();
            Interaction interaction = paymentResult.getInteraction();
            code = interaction.getCode();
            reason = interaction.getReason();
            Throwable cause = paymentResult.getCause();
            error = cause != null ? cause.getMessage() : null;
        }
        setText(resultInfoView, info);
        setText(interactionCodeView, code);
        setText(interactionReasonView, reason);
        setText(paymentErrorView, error);
    }

    private void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            text = getString(R.string.empty_label);
        }
        textView.setText(text);
    }

    private void showErrorDialog(String message) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.dialog_error_title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.dialog_error_button), null);
        builder.create().show();
    }

    private void openPaymentPage() {
        closeKeyboard();
        clearPaymentActivityResult();

        String listUrl = listInput.getText().toString().trim();
        if (TextUtils.isEmpty(listUrl) || !Patterns.WEB_URL.matcher(listUrl).matches()) {
            showErrorDialog(getString(R.string.dialog_error_listurl_invalid));
            return;
        }
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);
        paymentUI.setPaymentTheme(createPaymentTheme());

        // Set the orientation to be fixed to landscape mode
        //paymentUI.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }

    private PaymentTheme createPaymentTheme() {
        if (themeSwitch.isChecked()) {
            return PaymentTheme.createBuilder().
                setPaymentListTheme(R.style.CustomTheme_Toolbar).
                setChargePaymentTheme(R.style.CustomTheme_NoToolbar).
                build();
        } else {
            return PaymentTheme.createDefault();
        }
    }

    /**
     * Only called from test, creates and returns a new paymentResult handled IdlingResource
     */
    @VisibleForTesting
    public IdlingResource getResultHandledIdlingResource() {
        if (resultHandledIdlingResource == null) {
            resultHandledIdlingResource = new SimpleIdlingResource(getClass().getSimpleName() + "-resultHandledIdlingResource");
        }
        if (resultHandled) {
            resultHandledIdlingResource.setIdleState(true);
        } else {
            resultHandledIdlingResource.reset();
        }
        return resultHandledIdlingResource;
    }

    /**
     * Set the result handled idle state for the IdlingResource
     */
    private void setResultHandledIdleState() {
        resultHandled = true;
        if (resultHandledIdlingResource != null) {
            resultHandledIdlingResource.setIdleState(true);
        }
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            IBinder binder = listInput.getWindowToken();
            imm.hideSoftInputFromWindow(binder, 0);
        }
    }
}
