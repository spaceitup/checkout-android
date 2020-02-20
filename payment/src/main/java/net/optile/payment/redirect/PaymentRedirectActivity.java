/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.redirect;

import android.util.Log;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;

/** 
 * The Activity which will be launched after the browser is redirecting to the deep link url.
 */
public class PaymentRedirectActivity extends Activity {

    private static Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String listUrl = PaymentUI.getInstance().getListUrl();
        Log.i("pay", "listUrl: " + listUrl);

        setContentView(R.layout.activity_redirect);
        resultUri = null;

        if (getIntent() != null && getIntent().getData() != null) {
            resultUri = getIntent().getData();
        }
        finish();
    }

    public static Uri getResultUri() {
        return resultUri;
    }

    public static void clearResultUri() {
        resultUri = null;
    }
}
