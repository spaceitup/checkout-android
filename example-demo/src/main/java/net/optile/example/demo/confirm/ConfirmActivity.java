/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.confirm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.optile.example.demo.R;
import net.optile.example.demo.settings.SettingsActivity;
import net.optile.example.demo.shared.BaseActivity;

/**
 * This is the confirm screen shown after a charge operation has been completed.
 */
public final class ConfirmActivity extends BaseActivity {

    /**
     * Create an Intent to launch this confirm activity
     *
     * @return the newly created intent
     */
    public static Intent createStartIntent(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context may not be null");
        }
        return new Intent(context, ConfirmActivity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DefaultConfirmTheme);
        setContentView(R.layout.activity_confirm);

        Button button = findViewById(R.id.button_neworder);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettingsScreen();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        openSettingsScreen();
    }

    private void openSettingsScreen() {
        Intent intent = SettingsActivity.createStartIntent(this);
        startActivity(intent);
    }
}
