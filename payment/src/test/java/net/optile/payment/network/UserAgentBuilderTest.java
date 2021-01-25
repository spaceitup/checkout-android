/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.text.TextUtils;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class UserAgentBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void createFromContext_IllegalArgumentException() {
        UserAgentBuilder.createFromContext(null);
    }

    @Test
    public void createFromContext_success() {
        Context context = ApplicationProvider.getApplicationContext();
        String value = UserAgentBuilder.createFromContext(context);
        assertFalse(TextUtils.isEmpty(value));
    }

    @Test
    public void build_success() {
        UserAgentBuilder builder = new UserAgentBuilder();
        String sdkVersionName = "5.3.0";
        int sdkVersionCode = 51;
        String appVersionName = "6.1.0";
        int appVersionCode = 73;
        String appPackageName = "app.package.name";
        String appName = "App Name";
        String buildManufacturer = "Google";
        String buildModel = "Android SDK built for x86_64";
        int buildVersionSdkInt = 28;
        String buildVersionRelease = "9";

        String result = builder.setSdkVersionName(sdkVersionName).
            setSdkVersionCode(sdkVersionCode).
            setAppVersionName(appVersionName).
            setAppVersionCode(appVersionCode).
            setAppPackageName(appPackageName).
            setAppName(appName).
            setBuildManufacturer(buildManufacturer).
            setBuildModel(buildModel).
            setBuildVersionSdkInt(buildVersionSdkInt).
            setBuildVersionRelease(buildVersionRelease).build();

        String expected =
            "android-sdk/5.3.0 (51) App/6.1.0 (app.package.name; App Name; 73) Platform/28 (Google; Android SDK built for x86_64; 9)";
        assertEquals(expected, result);
    }
}
