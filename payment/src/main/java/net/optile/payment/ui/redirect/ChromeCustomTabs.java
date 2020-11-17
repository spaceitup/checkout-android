/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsService;
import net.optile.payment.core.PaymentException;

/**
 * Helper class for working with Chrome Custom Tabs
 */
public class ChromeCustomTabs {

    private final static String PACKAGE_NAME = "com.android.chrome";

    /**
     * Checks to see if this device supports Chrome Custom Tabs and if Chrome Custom Tabs are available.
     *
     * @param context The context in which this tabs is used
     * @return true if Chrome Custom Tabs are supported and available, false otherwise
     */
    public static boolean isSupported(Context context) {
        if (SDK_INT < JELLY_BEAN_MR2) {
            return false;
        }
        Intent serviceIntent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION).setPackage(PACKAGE_NAME);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        boolean available = context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE | Context.BIND_WAIVE_PRIORITY);
        context.unbindService(connection);
        return available;
    }

    /**
     * Open the url in the custom chrome tab
     *
     * @param context in which the browser window should be opened
     * @param uri to be opened in the external browser window
     */
    public static void open(Context context, Uri uri) throws PaymentException {

        if (!isSupported(context)) {
            throw new PaymentException("ChromeCustomTabs is not available in this device");
        }
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(PACKAGE_NAME);
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            customTabsIntent.launchUrl(context, uri);
        } catch (ActivityNotFoundException e) {
            throw new PaymentException("Error ocurred while opening ChromeCustomTabs", e);
        }
    }
}
