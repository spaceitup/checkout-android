/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;

/**
 * The PaymentUtils class containing helper methods
 */
public final class PaymentUtils {
    
    /**
     * Check if the Boolean object is true, the Boolean object may be null.
     *
     * @param val the value to check
     * @return true when the val is not null and true
     */
    public static boolean isTrue(Boolean val) {
        return val != null && val;
    }

    /**
     * Strips whitespace from the start and end of a String returning an empty String if null input.
     *
     * @param value the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if null input
     */
    public static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    /**
     * Compare String values of two Objects by obtaining the String values using the toString method.
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @return true when the String values of both Objects are equal and not null, false otherwise.
     */
    public static boolean equalsAsString(Object obj1, Object obj2) {
        String str1 = obj1 != null ? obj1.toString() : null;
        String str2 = obj2 != null ? obj2.toString() : null;
        return str1 != null && str2 != null && (str1.equals(str2));
    }

    /**
     * Get the base integer value given the Integer object.
     * If the object is null then return the 0 value.
     *
     * @param value to convert to an integer
     * @return the value as an integer or 0 if the value is null
     */
    public static int toInt(Integer value) {
        return value == null ? 0 : value;
    }

    /**
     * Does the list of InputElements contain both the expiry month and year fields.
     *
     * @param elements list of input elements to check
     * @return true when there are both an expiry month and year
     */
    public static boolean containsExpiryDate(List<InputElement> elements) {
        boolean hasExpiryMonth = false;
        boolean hasExpiryYear = false;

        for (InputElement element : elements) {
            switch (element.getName()) {
                case PaymentInputType.EXPIRY_MONTH:
                    hasExpiryMonth = true;
                    break;
                case PaymentInputType.EXPIRY_YEAR:
                    hasExpiryYear = true;
            }
        }
        return hasExpiryYear && hasExpiryMonth;
    }

    /** 
     * Create a full expiry year from the last part of the expiry year.
     * This will use dynamic windowing of -30 years and +70 year.
     *
     * @param inputYear the year which the user entered, e.g. 1 or 99
     * @return complete expiry year value
     */
    public static int createExpiryYear(int inputYear) {
        if (inputYear < 0 || inputYear >= 100) {
            throw new IllegalArgumentException("Input year must be >= 0 and < 100: " + inputYear);
        }
        final int curYear = Calendar.getInstance().get(Calendar.YEAR);
        final int startYear = curYear - 30;
        final int endYear = curYear + 70;

        int year = inputYear > (startYear % 100) ? startYear : endYear;
        return (year - (year % 100)) + inputYear;
    }
    
    /**
     * Set the TextAppearance in the TextView
     *
     * @param textView for which the textAppearance should be changed
     * @param textAppearanceResId the resource id of the textAppearance to be set
     */
    public static void setTextAppearance(TextView textView, int textAppearanceResId) {
        if (textAppearanceResId != 0) {
            TextViewCompat.setTextAppearance(textView, textAppearanceResId);
        }
    }

    /**
     * Set the background resource for the ImageView
     *
     * @param imageView for which the background resource should be set
     * @param backgroundResId the resource id of the background
     */
    public static void setImageBackground(ImageView imageView, int backgroundResId) {
        if (backgroundResId != 0) {
            imageView.setBackgroundResource(backgroundResId);
        }
    }

    /**
     * Check if the device is in landscape mode.
     *
     * @param context containing the configuration for determining the mode.
     * @return true when in landscape mode, false otherwise
     */
    public static boolean isLandscape(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Read the contents of the raw resource
     *
     * @param res The system Resources
     * @param resId The resource id
     * @return The String or an empty string if something went wrong
     */
    public static String readRawResource(Resources res, int resId) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        try (InputStream is = res.openRawResource(resId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr)) {

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Resources.NotFoundException e) {
            throw new IOException("Resource not found: " + resId);
        }
        return sb.toString();
    }

    /**
     * Set the test Id to the view with the proper formatting understood by the automated UI tests.
     *
     * @param view in which the tag should be set
     * @param type the type of the View, i.e. widget, networkcard.
     * @param name the name of the view i.e. holderName
     */
    public static void setTestId(View view, String type, String name) {
        view.setContentDescription(type + "_" + name);
    }
}
