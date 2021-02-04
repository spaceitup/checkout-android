/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.network;

import com.payoneer.mrs.payment.BuildConfig;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

/**
 * Class for constructing the custom UserAgent header value
 */
final class UserAgentBuilder {

    private String sdkVersionName;
    private int sdkVersionCode;
    private String appVersionName;
    private int appVersionCode;
    private String appPackageName;
    private String appName;
    private String buildManufacturer;
    private String buildModel;
    private int buildVersionSdkInt;
    private String buildVersionRelease;

    String build() {
        return "android-sdk/" +
            toEmptyString(sdkVersionName) + " (" +
            sdkVersionCode + ") " +

            "App/" + toEmptyString(appVersionName) + " (" +
            toEmptyString(appPackageName) + "; " +
            toEmptyString(appName) + "; " +
            appVersionCode + ") " +

            "Platform/" + buildVersionSdkInt + " (" +
            toEmptyString(buildManufacturer) + "; " +
            toEmptyString(buildModel) + "; " +
            toEmptyString(buildVersionRelease) + ")";
    }

    /**
     * Construct a new UserAgent value from the provided Context
     *
     * @param context used to construct the default UserAgent
     * @return the newly created UserAgent value or null when it could not be created
     */
    @SuppressWarnings("deprecation")
    static String createFromContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        try {
            Context appContext = context.getApplicationContext();
            String appPackageName = appContext.getPackageName();
            PackageManager packageManager = appContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appPackageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(appPackageName, 0);

            int versionCode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = (int)packageInfo.getLongVersionCode();
            } else {
                versionCode = packageInfo.versionCode;
            }
            return new UserAgentBuilder().
                setSdkVersionName(BuildConfig.VERSION_NAME).
                setSdkVersionCode(BuildConfig.VERSION_CODE).
                setAppVersionName(packageInfo.versionName).
                setAppVersionCode(versionCode).
                setAppPackageName(appPackageName).
                setAppName(packageManager.getApplicationLabel(appInfo).toString()).
                setBuildManufacturer(android.os.Build.MANUFACTURER).
                setBuildModel(android.os.Build.MODEL).
                setBuildVersionSdkInt(android.os.Build.VERSION.SDK_INT).
                setBuildVersionRelease(android.os.Build.VERSION.RELEASE).
                build();
        } catch (final PackageManager.NameNotFoundException e) {
            Log.w("android-sdk", e);
        }
        return null;
    }

    UserAgentBuilder setSdkVersionName(String sdkVersionName) {
        this.sdkVersionName = sdkVersionName;
        return this;
    }

    UserAgentBuilder setSdkVersionCode(int sdkVersionCode) {
        this.sdkVersionCode = sdkVersionCode;
        return this;
    }

    UserAgentBuilder setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
        return this;
    }

    UserAgentBuilder setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
        return this;
    }

    UserAgentBuilder setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
        return this;
    }

    UserAgentBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    UserAgentBuilder setBuildManufacturer(String buildManufacturer) {
        this.buildManufacturer = buildManufacturer;
        return this;
    }

    UserAgentBuilder setBuildModel(String buildModel) {
        this.buildModel = buildModel;
        return this;
    }

    UserAgentBuilder setBuildVersionSdkInt(int buildVersionSdkInt) {
        this.buildVersionSdkInt = buildVersionSdkInt;
        return this;
    }

    UserAgentBuilder setBuildVersionRelease(String buildVersionRelease) {
        this.buildVersionRelease = buildVersionRelease;
        return this;
    }

    private String toEmptyString(String str) {
        return str == null ? "" : str;
    }
}
