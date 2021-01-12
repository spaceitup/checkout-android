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
public final class UserAgent {

    private final String userAgentValue;

    private UserAgent(Builder builder) {
        this.userAgentValue = "android-sdk/" + 
            builder.sdkVersionName + " (" + 
            builder.sdkVersionCode + ") " +

            "App/" + builder.appVersionName + " (" +
            builder.appPackageName + "; " + 
            builder.appName + "; " +
            builder.appVersionCode + ") " +

            "Platform/" + builder.buildVersionSdkInt + " (" +
            builder.buildManufacturer + "; " +
            builder.buildModel + "; " +
            builder.buildVersionRelease + ")";
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    /**
     * Construct a new UserAgent from the provided Context
     *
     * @param context used to construct the default UserAgent
     * @return the newly created default UserAgent
     */
    public static UserAgent createFromContext(Context context) throws PaymentException {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        Builder builder = createBuilder();
        try {
            Context appContext = context.getApplicationContext();
            String appPackageName = appContext.getPackageName();
            PackageManager packageManager = appContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appPackageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(appPackageName, 0);

            return createBuilder().
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

    public String getValue() {
        return userAgentValue;
    }

    public static final class Builder {
        String sdkVersionName;
        int sdkVersionCode;
        String appVersionName;
        int appVersionCode;
        String appPackageName;
        String appName;
        String buildManufacturer;
        String buildModel;
        int buildVersionSdkInt;
        String buildVersionRelease;

        Builder() {
        }

        public Builder setSdkVersionName(String sdkVersionName) {
            this.sdkVersionName = sdkVersionName;
            return this;
        }

        public Builder setSdkVersionCode(int sdkVersionCode) {
            this.sdkVersionCode = sdkVersionCode;
            return this;
        }

        public Builder setAppVersionName(String appVersionName) {
            this.appVersionName = appVersionName;
            return this;
        }

        public Builder setAppVersionCode(int appVersionCode) {
            this.appVersionCode = appVersionCode;
            return this;
        }

        public Builder setAppPackageName(String appPackageName) {
            this.appPackageName = appPackageName;
            return this;
        }

        public Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder setBuildManufacturer(String buildManufacturer) {
            this.buildManufacturer = buildManufacturer;
            return this;
        }

        public Builder setBuildModel(String buildModel) {
            this.buildModel = buildModel;
            return this;
        }

        public Builder setBuildVersionSdkInt(int buildVersionSdkInt) {
            this.buildVersionSdkInt = buildVersionSdkInt;
            return this;
        }

        public Builder setBuildVersionRelease(String buildVersionRelease) {
            this.buildVersionRelease = buildVersionRelease;
            return this;
        }

        public UserAgent build() {
            return new UserAgent(this);
        }
    }
}
