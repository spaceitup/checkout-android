/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to hold the theme settings of the Payment screens in the Android SDK
 */
public final class PaymentTheme implements Parcelable {

    private PaymentTheme() {
    }

    private PaymentTheme(Parcel in) {
    }

    /** 
     * Create a new PaymentThemeBuilder, this builder can be used to build a new PaymentTheme
     * 
     * @return the newly created PaymentThemeBuilder 
     */
    public static PaymentThemeBuilder createPaymentThemeBuilder() {
        return new PaymentThemeBuilder();
    }

    /** 
     * Class PaymentThemeBuilder for building PaymentTheme
     */
    public static class PaymentThemeBuilder {

        private PaymentThemeBuilder() {
        }
        
        public PaymentTheme build() {
            return new PaymentTheme();
        }
    }

    public static final Parcelable.Creator<PaymentTheme> CREATOR = new Parcelable.Creator<PaymentTheme>() {

        public PaymentTheme createFromParcel(Parcel in) {
            return new PaymentTheme(in);
        }

        public PaymentTheme[] newArray(int size) {
            return new PaymentTheme[size];
        }
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
    }
}
