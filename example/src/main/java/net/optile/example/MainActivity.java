/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.optile.example.checkout.CheckoutActivity;

/**
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    public final static String TAG  = "payment_MainActivity";
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "test string: " + getString(R.string.payment_authorization));


        Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onButtonClicked();
                }
            });
    }

    private void onButtonClicked() {
        Intent intent = CheckoutActivity.createStartIntent(this);
        startActivity(intent);
    }
}
