/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

/** 
 * 
 */
public class PaymentRedirectActivity extends Activity {

    private static Uri returnUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        returnUri = null;
        if (getIntent() != null && getIntent().getData() != null) {
            returnUri = getIntent().getData();
        }
        finish();
    }

    public static Uri getReturnUri() {
        return returnUri;
    }

    public static void clear() {
        returnUri = null;
    }
}
