/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.optile.example.demo.R;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.shared.BaseActivity;

/**
 * This is the main Activity of this demo app in which users can paste a listUrl and start the demo.
 */
public final class SettingsActivity extends BaseActivity {

    /**
     * Create an Intent to launch this settings activity
     *
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button = findViewById(R.id.button_settings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClicked();
            }
        });
    }

    private void onButtonClicked() {
        EditText listInput = findViewById(R.id.input_listurl);
        String listUrl = listInput.getText().toString().trim();

        if (TextUtils.isEmpty(listUrl) || !Patterns.WEB_URL.matcher(listUrl).matches()) {
            showErrorDialog(R.string.dialog_error_listurl_invalid);
            return;
        }
        Intent intent = CheckoutActivity.createStartIntent(this, listUrl);
        startActivity(intent);
    }
}
