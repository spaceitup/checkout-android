/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.checkout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import net.optile.payment.core.PaymentError;
import net.optile.payment.model.Interaction;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.PaymentTheme;

/**
 * This is the main Activity of this Checkout example app
 */
public final class CheckoutActivity extends AppCompatActivity {

    private final static int PAYMENT_REQUEST_CODE = 1;

    private CheckoutResult checkoutResult;
    private RadioGroup themeGroup;
    private EditText listInput;
    private View sdkResponseLayout;
    private View apiResponseLayout;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        themeGroup = findViewById(R.id.radio_themes);
        listInput = findViewById(R.id.input_listurl);
        sdkResponseLayout = findViewById(R.id.layout_sdkresponse);
        apiResponseLayout = findViewById(R.id.layout_apiresponse);

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

        if (checkoutResult != null) {
            showSdkResponse(checkoutResult);
            showApiResponse(checkoutResult);
            this.checkoutResult = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != PAYMENT_REQUEST_CODE) {
            return;
        }
        if (data != null && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
            PaymentResult result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
            this.checkoutResult = new CheckoutResult(resultCode, result);
        }
    }

    private void showSdkResponse(CheckoutResult result) {
        PaymentResult pr = result.paymentResult;

        apiResponseLayout.setVisibility(View.VISIBLE);
        setText(result.getResultCodeString(), R.id.label_activityresultcode, R.id.text_activityresultcode);

        sdkResponseLayout.setVisibility(View.VISIBLE);
        Interaction interaction = pr.getInteraction();
        String val = interaction == null ? pr.getResultInfo() : null;
        setText(val, R.id.label_sdkresultinfo, R.id.text_sdkresultinfo);

        PaymentError error = pr.getPaymentError();
        val = error != null ? error.toString() : null;
        setText(val, R.id.label_sdkpaymenterror, R.id.text_sdkpaymenterror);
    }

    private void showApiResponse(CheckoutResult result) {
        PaymentResult pr = result.paymentResult;
        Interaction interaction = pr.getInteraction();

        if (interaction == null) {
            apiResponseLayout.setVisibility(View.GONE);
            return;
        }
        apiResponseLayout.setVisibility(View.VISIBLE);
        setText(pr.getResultInfo(), R.id.label_apiresultinfo, R.id.text_apiresultinfo);
        setText(interaction.getCode(), R.id.label_apiinteractioncode, R.id.text_apiinteractioncode);
        setText(interaction.getReason(), R.id.label_apiinteractionreason, R.id.text_apiinteractionreason);
    }

    private void setText(String text, int labelResId, int textResId) {
        TextView labelView = findViewById(labelResId);
        TextView textView = findViewById(textResId);

        if (TextUtils.isEmpty(text)) {
            labelView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            return;
        }
        labelView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    private void showPaymentError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.dialog_error_title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_error_button),
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialog.show();
    }

    private void openPaymentPage() {
        String listUrl = listInput.getText().toString().trim();

        if (TextUtils.isEmpty(listUrl) || !Patterns.WEB_URL.matcher(listUrl).matches()) {
            showPaymentError(getString(R.string.dialog_error_listurl_invalid));
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
            case R.id.radio_theme_default:
                return CheckoutThemeBuilder.createDefaultTheme();
            case R.id.radio_theme_custom:
                return CheckoutThemeBuilder.createCustomTheme();
            default:
                return CheckoutThemeBuilder.createEmptyTheme();
        }
    }

}
