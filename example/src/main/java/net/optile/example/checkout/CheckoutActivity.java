/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */
package net.optile.example.checkout;

import android.util.Log;
import android.text.TextUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.TextView;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.core.PaymentError;
import net.optile.payment.model.Interaction;

/**
 * This is the main Activity of this Checkout example app
 */
public final class CheckoutActivity extends AppCompatActivity {

    private static int PAYMENT_REQUEST_CODE = 1;

    private CheckoutResult checkoutResult;
    private RadioGroup themeGroup;
    private EditText listInput; 
    
    /**
     * Create an Intent to launch this checkout activity
     *
     * @param context the context
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        return new Intent(context, CheckoutActivity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        themeGroup = findViewById(R.id.radio_themes);
        listInput = findViewById(R.id.input_listurl);
        
        Button button = findViewById(R.id.button_checkout);
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
            showCheckoutResult(checkoutResult);
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
        if (data != null  && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
            PaymentResult result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
            this.checkoutResult = new CheckoutResult(resultCode, result);
        }
    }

    private void showCheckoutResult(CheckoutResult result) {
        PaymentResult pr = result.paymentResult;

        setText(result.getResultCodeString(), R.id.label_resultcode, R.id.text_resultcode);
        setText(pr.getResultInfo(), R.id.label_resultinfo, R.id.text_resultinfo);

        Interaction interaction = pr.getInteraction();
        String val = interaction != null ? interaction.getCode() : null;
        setText(val, R.id.label_interactioncode, R.id.text_interactioncode);        

        val = interaction != null ? interaction.getReason() : null;
        setText(val, R.id.label_interactionreason, R.id.text_interactionreason);                
        
        PaymentError error = pr.getPaymentError();        
        val = error != null ? error.toString() : null;
        setText(val, R.id.label_paymenterror, R.id.text_paymenterror);        
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
        alertDialog.setMessage(String.format(getString(R.string.dialog_error_message), message));
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

        if (TextUtils.isEmpty(listUrl)) {
            showPaymentError(getString(R.string.dialog_error_listurl_empty));
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
