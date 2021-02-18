/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.text.TextUtils;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class UserAgentBuilderTest {

    final String sdkVersionName = "5.3.0";
    final Integer sdkVersionCode = 51;
    final String appVersionName = "6.1.0";
    final Integer appVersionCode = 73;
    final String appPackageName = "app.package.name";
    final String appName = "App Name";
    final Integer buildVersionSdkInt = 28;
    final String buildManufacturer = "Google";
    final String buildModel = "Android SDK built for x86_64";
    final String buildVersionRelease = "9";

    
    @Test(expected = IllegalArgumentException.class)
    public void createFromContext_IllegalArgumentException() {
        UserAgentBuilder.createFromContext(null);
    }

    @Test
    public void createFromContext_success() {
        final Context context = ApplicationProvider.getApplicationContext();
        final String value = UserAgentBuilder.createFromContext(context);
        assertFalse(TextUtils.isEmpty(value));
    }

    @Test
    public void testBuild_withAllSections() {
        final String result = new UserAgentBuilder().
            setSdkVersionName(sdkVersionName).
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
            "AndroidSDK/5.3.0 (51) AndroidApp/6.1.0 (app.package.name; App Name; 73) AndroidPlatform/28 (Google; Android SDK built for x86_64; 9)";
        assertEquals(expected, result);
    }

    @Test
    public void testBuild_missingAppVersionName() {
        final String result = new UserAgentBuilder().
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
            "AndroidApp/6.1.0 (app.package.name; App Name; 73) AndroidPlatform/28 (Google; Android SDK built for x86_64; 9)";        
        assertEquals(expected, result);
    }


    @Test
    public void testBuild_missingSectionValues() {
        final String result = new UserAgentBuilder().
            setSdkVersionName(sdkVersionName).
            setSdkVersionCode(sdkVersionCode).
            setAppVersionName(appVersionName).
            setAppVersionCode(appVersionCode).
            setAppPackageName(appPackageName).
            setBuildVersionSdkInt(buildVersionSdkInt).build();

        String expected =
            "AndroidSDK/5.3.0 (51) AndroidApp/6.1.0 (app.package.name; ; 73) AndroidPlatform/28 (; ; )";
        assertEquals(expected, result);
    }

    
    @Test
    public void testBuild_withoutSectionVersions() {
        String result = new UserAgentBuilder().
            setSdkVersionCode(sdkVersionCode).
            setAppVersionCode(appVersionCode).
            setAppPackageName(appPackageName).
            setAppName(appName).
            setBuildManufacturer(buildManufacturer).
            setBuildModel(buildModel).
            setBuildVersionRelease(buildVersionRelease).build();

        assertNull(result);
    }
}
