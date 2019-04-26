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
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import net.optile.example.demo.R;
import net.optile.example.demo.checkout.CheckoutActivity;
import net.optile.example.demo.shared.BaseActivity;
import net.optile.example.demo.shared.DemoSettings;

/**
 * This is the main Activity of this demo app in which users can set the DemoSettings before starting the demo.
 */
public final class SettingsActivity extends BaseActivity {

    private Switch themeSwitch;
    private Switch registeredSwitch;
    private Switch summarySwitch;

    /**
     * Create an Intent to launch this settings activity
     *
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context, final DemoSettings settings) {

        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        if (settings == null) {
            throw new IllegalArgumentException("settings may not be null");
        }
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_SETTINGS, settings);
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
        themeSwitch = findViewById(R.id.switch_theme);
        registeredSwitch = findViewById(R.id.switch_registered);
        summarySwitch = findViewById(R.id.switch_summary);

        if (settings != null) {
            themeSwitch.setChecked(settings.getCustomTheme());
            registeredSwitch.setChecked(settings.getRegistered());
            summarySwitch.setChecked(settings.getSummary());
        }
    }

    private void onButtonClicked() {
        DemoSettings settings = new DemoSettings(themeSwitch.isChecked(), registeredSwitch.isChecked(), summarySwitch.isChecked());
        Intent intent = CheckoutActivity.createStartIntent(this, settings);
        startActivity(intent);
    }
}
