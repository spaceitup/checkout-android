/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.form;

import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import net.optile.payment.model.BrowserData;

/**
 * Class for building the BrowserData 
 */
public final class BrowserDataBuilder {

	private boolean javaEnabled;
	private String language;
	private int colorDepth;
	private String timezone;
	private int browserScreenHeight;
	private int browserScreenWidth;
    
    /** 
     * Build a new BrowserData
     * 
     * @return the newly build BrowserData 
     */
    public BrowserData build() {
        BrowserData data = new BrowserData();
        data.setJavaEnabled(javaEnabled);
        data.setLanguage(language);
        data.setColorDepth(colorDepth);
        data.setBrowserScreenWidth(browserScreenWidth);
        data.setBrowserScreenHeight(browserScreenHeight);
        return data;
    }

    public static BrowserData createFromContext(Context context) {
        Context appContext = context.getApplicationContext();
        BrowserDataBuilder builder = new BrowserDataBuilder();

        builder.setJavaEnabled(false);
        builder.setLanguage(Locale.getDefault().toLanguageTag());
        builder.setTimezone(TimeZone.getDefault().getID());

        Display display = appContext.getDisplay();
        DisplayMetrics realMetrics = new DisplayMetrics();
        display.getRealMetrics(realMetrics);
        builder.setBrowserScreenHeight(realMetrics.heightPixels);
        builder.setBrowserScreenWidth(realMetrics.widthPixels);
        return builder.build();
    }

    public BrowserDataBuilder setJavaEnabled(boolean javaEnabled) {
        this.javaEnabled = javaEnabled;
        return this;
    }
    
    public BrowserDataBuilder setLanguage(String language) {
        this.language = language;
        return this;
    }

    public BrowserDataBuilder setColorDepth(int colorDepth) {
        this.colorDepth = colorDepth;
        return this;
    }

    public BrowserDataBuilder setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public BrowserDataBuilder setBrowserScreenWidth(int browserScreenWidth) {
        this.browserScreenWidth = browserScreenWidth;
        return this;
    }
    
    public BrowserDataBuilder setBrowserScreenHeight(int browserScreenHeight) {
        this.browserScreenHeight = browserScreenHeight;
        return this;
    }
}
