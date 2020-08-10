/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.basic;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import net.optile.payment.core.PaymentError;
import net.optile.payment.model.Interaction;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentTheme;
import net.optile.payment.ui.PaymentUI;

/**
 * This is the main Activity of this basic example app
 */
public final class BasicActivity extends AppCompatActivity {

    private final static int PAYMENT_REQUEST_CODE = 1;

    private SdkResult sdkResult;
    private RadioGroup themeGroup;
    private EditText listInput;
    private View resultLayout;
    private TextView resultHeaderView;
    private TextView resultInfoView;
    private TextView resultCodeView;
    private TextView interactionCodeView;
    private TextView interactionReasonView;
    private TextView paymentErrorView;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        themeGroup = findViewById(R.id.radio_themes);
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

        if (sdkResult != null) {
            showSdkResult(sdkResult);
            this.sdkResult = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PAYMENT_REQUEST_CODE) {
            return;
        }
        PaymentResult paymentResult = PaymentResult.fromResultIntent(data);
        if (paymentResult != null) {
            this.sdkResult = new SdkResult(resultCode, paymentResult);
        }
    }

    private void showSdkResult(SdkResult sdkResult) {
        PaymentResult paymentResult = sdkResult.paymentResult;

        resultHeaderView.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.VISIBLE);
        setText(resultCodeView, sdkResult.getResultCodeString());
        setText(resultInfoView, paymentResult.getResultInfo());

        Interaction interaction = paymentResult.getInteraction();
        String code = interaction != null ? interaction.getCode() : null;
        String reason = interaction != null ? interaction.getReason() : null;
        setText(interactionCodeView, code);
        setText(interactionReasonView, reason);

        PaymentError paymentError = paymentResult.getPaymentError();
        String text = paymentError != null ? paymentError.toString() : null;
        setText(paymentErrorView, text);
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
        String listUrl = listInput.getText().toString().trim();

        if (TextUtils.isEmpty(listUrl) || !Patterns.WEB_URL.matcher(listUrl).matches()) {
            showErrorDialog(getString(R.string.dialog_error_listurl_invalid));
            return;
        }
        PaymentUI paymentUI = PaymentUI.getInstance();
        paymentUI.setListUrl(listUrl);
        paymentUI.setPaymentTheme(createPaymentTheme());

        // The custom validation settings file, the default SDK validations are sufficient in most cases 
        //paymentUI.setValidationResId(R.raw.customvalidations);

        // Set the orientation to be fixed to landscape mode
        //paymentUI.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // The custom payment method group settings file
        // paymentUI.setGroupResId(R.raw.customgroups);
        paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
    }

    private PaymentTheme createPaymentTheme() {

        switch (themeGroup.getCheckedRadioButtonId()) {
            case R.id.radio_theme_custom:
                return SdkThemeBuilder.createCustomTheme();
            default:
                return SdkThemeBuilder.createDefaultTheme();
        }
    }

}
