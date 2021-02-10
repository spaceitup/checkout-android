/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.redirect;

import com.payoneer.mrs.payment.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * The Activity that is launched after the browser is redirecting to the deep link URL.
 */
public class PaymentRedirectActivity extends Activity {

    private static Uri resultUri;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        final Intent intent = getIntent();
        resultUri = (intent == null) ? null : intent.getData();
        finish();
    }

    public static Uri getResultUri() {
        return resultUri;
    }

    public static void clearResultUri() {
        resultUri = null;
    }
}
