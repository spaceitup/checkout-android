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
 * Class for holding the payment result details
 */
public final class PaymentResult implements Parcelable {

    private String code;

    private String reason;

    private String message;
    
    /** 
     * Construct a new PaymentResult with the interaction values
     * 
     * @param code holding the interaction code
     * @param reason holding the interaction reason
     * @param message a localized message about the code and reason 
     */
    public PaymentResult(String code, String reason, String message) {
        this.code = code;
        this.reason = reason;
        this.message = message;
    }

    private PaymentResult() {
    }

    private PaymentResult(Parcel in) {
        this.code = in.readString();
        this.reason = in.readString();
        this.message = in.readString();
    }
    
    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }
    
    public static final Parcelable.Creator<PaymentResult> CREATOR = new Parcelable.Creator<PaymentResult>() {

        public PaymentResult createFromParcel(Parcel in) {
            return new PaymentResult(in);
        }

        public PaymentResult[] newArray(int size) {
            return new PaymentResult[size];
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
        out.writeString(code);
        out.writeString(reason);
        out.writeString(message);
    }
}
