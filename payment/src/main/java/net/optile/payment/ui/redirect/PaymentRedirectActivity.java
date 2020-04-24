/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import net.optile.payment.R;

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
