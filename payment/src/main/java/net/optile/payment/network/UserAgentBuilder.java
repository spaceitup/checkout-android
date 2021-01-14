/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import net.optile.payment.BuildConfig;
import net.optile.payment.core.PaymentException;

/**
 * Class for constructing the custom UserAgent header value
 */
public final class UserAgentBuilder {

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
    
    public String build() {
        return "android-sdk/" + 
            sdkVersionName + " (" +
            sdkVersionCode + ") " +

            "App/" + appVersionName + " (" +
            appPackageName + "; " +
            appName + "; " +
            appVersionCode + ") " +

            "Platform/" + buildVersionSdkInt + " (" +
            buildManufacturer + "; " +
            buildModel + "; " +
            buildVersionRelease + ")";
    }

    /**
     * Construct a new UserAgent value from the provided Context
     *
     * @param context used to construct the default UserAgent
     * @return the newly created UserAgent value
     */
    public static String createFromContext(Context context) throws PaymentException {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        try {
            Context appContext = context.getApplicationContext();
            String appPackageName = appContext.getPackageName();
            PackageManager packageManager = appContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appPackageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(appPackageName, 0);

            return new UserAgentBuilder().
                setSdkVersionName(BuildConfig.VERSION_NAME).
                setSdkVersionCode(BuildConfig.VERSION_CODE).
                setAppVersionName(packageInfo.versionName).
                setAppVersionCode(packageInfo.versionCode).
                setAppPackageName(appPackageName).
                setAppName(packageManager.getApplicationLabel(appInfo).toString()).
                setBuildManufacturer(android.os.Build.MANUFACTURER).
                setBuildModel(android.os.Build.MODEL).
                setBuildVersionSdkInt(android.os.Build.VERSION.SDK_INT).
                setBuildVersionRelease(android.os.Build.VERSION.RELEASE).
                build();
        } catch (final PackageManager.NameNotFoundException e) {
            throw new PaymentException(e);
        }
    }

    public UserAgentBuilder setSdkVersionName(String sdkVersionName) {
        this.sdkVersionName = sdkVersionName;
        return this;
    }

    public UserAgentBuilder setSdkVersionCode(int sdkVersionCode) {
        this.sdkVersionCode = sdkVersionCode;
        return this;
    }

    public UserAgentBuilder setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
        return this;
    }

    public UserAgentBuilder setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
        return this;
    }

    public UserAgentBuilder setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
        return this;
    }

    public UserAgentBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public UserAgentBuilder setBuildManufacturer(String buildManufacturer) {
        this.buildManufacturer = buildManufacturer;
        return this;
    }

    public UserAgentBuilder setBuildModel(String buildModel) {
        this.buildModel = buildModel;
        return this;
    }

    public UserAgentBuilder setBuildVersionSdkInt(int buildVersionSdkInt) {
        this.buildVersionSdkInt = buildVersionSdkInt;
        return this;
    }

    public UserAgentBuilder setBuildVersionRelease(String buildVersionRelease) {
        this.buildVersionRelease = buildVersionRelease;
        return this;
    }
}
