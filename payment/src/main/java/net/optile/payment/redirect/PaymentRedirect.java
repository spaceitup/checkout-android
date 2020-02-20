/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.redirect;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsService;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.Redirect;
import net.optile.payment.util.GsonHelper;
    
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * PaymentRedirect class to handle redirect payments.
 */
public final class PaymentRedirect {

    /**
     * Check if payment redirects are supported for this device.
     *
     * @param context The context in which this tabs is used
     * @return true if payment redirects are supported, false otherwise
     */
    public static boolean isSupported(Context context) {
        return ChromeCustomTabs.isSupported(context);
    }

    /** 
     * Open the Redirect for the given Context. 
     * 
     * @param context 
     * @param redirect
     * @param styles 
     */
    public static void open(Context context, Redirect redirect, RedirectStyles styles) throws PaymentException {
        if (!isSupported(context)) {
            throw new PaymentException("Redirect payments are not supported by this device"); 
        }
        Uri uri = createRedirectUri(context, redirect, styles);
        ChromeCustomTabs.open(context, uri);
    }

    private static Uri createRedirectUri(Context context, Redirect redirect, RedirectStyles styles) {
        String packageName = context.getApplicationContext().getPackageName();
        GsonHelper gson = GsonHelper.getInstance();
        String redirectJson = gson.toJson(redirect);
        String stylesJson = gson.toJson(styles);
        Uri uri = Uri.parse("https://apps.integration.oscato.com/mobile-redirect/");

        StringBuilder deepLink = new StringBuilder("androidsdk://");
        deepLink.append(packageName);
        deepLink.append(".paymentredirect");
        Log.i("pay", "deepLink: " + deepLink.toString());
        
        uri = uri.buildUpon().
            appendQueryParameter("redirect", redirectJson).
            appendQueryParameter("styles", stylesJson).
            appendQueryParameter("dl", deepLink.toString()).build();
        return uri;
    }
}
