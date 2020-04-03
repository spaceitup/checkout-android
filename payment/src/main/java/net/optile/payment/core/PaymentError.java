/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.core;

import com.google.gson.JsonSyntaxException;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.util.GsonHelper;

/**
 * A class representing the details about the error
 */
public final class PaymentError implements Parcelable {

    public final static Parcelable.Creator<PaymentError> CREATOR = new Parcelable.Creator<PaymentError>() {

        public PaymentError createFromParcel(Parcel in) {
            return new PaymentError(in);
        }

        public PaymentError[] newArray(int size) {
            return new PaymentError[size];
        }
    };

    /**
     * The optional network status code like 404 or 500
     */
    private int statusCode;
    /**
     * The optional message containing details about the error
     */
    private String message;
    /**
     * The optional ErrorInfo received from the payment Api
     */
    private ErrorInfo errorInfo;
    /**
     * The optional cause of the internal error
     */
    private Throwable cause;
    /**
     * This optional flag to indicate that this error was caused by a network failure
     */
    private boolean networkFailure;

    /**
     * Construct a new PaymentError containing the message what went wrong
     *
     * @param message the error message
     */
    public PaymentError(final String message) {
        this.message = message;
    }

    /**
     * Construct a new PaymentError containing the network failure information
     *
     * @param statusCode the status code
     * @param message the error message
     */
    public PaymentError(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Construct a new PaymentError containing the network failure information
     *
     * @param statusCode the status code
     * @param errorInfo the error info
     */
    public PaymentError(final int statusCode, final ErrorInfo errorInfo) {
        this.statusCode = statusCode;
        this.message = errorInfo.getResultInfo();
        this.errorInfo = errorInfo;
    }

    /**
     * Construct a new PaymentError containing the cause of the internal error
     *
     * @param cause the cause of the internal error
     */
    public PaymentError(final Throwable cause) {
        this.message = cause.getMessage();
        this.cause = cause;
    }

    /**
     * Construct a new PaymentError containing information what caused the error
     *
     * @param message the error message
     * @param cause the cause of the internal error
     */
    public PaymentError(final String message, final Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    /**
     * Construct a new PaymentError containing information what caused the error
     * A network failure may be caused by an IOException.
     *
     * @param cause the cause of the internal error
     * @param networkFailure indicating if this error was caused by a network failure
     */
    public PaymentError(final Throwable cause, final boolean networkFailure) {
        this.message = cause.getMessage();
        this.cause = cause;
        this.networkFailure = networkFailure;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public Throwable getCause() {
        return cause;
    }

    public boolean getNetworkFailure() {
        return networkFailure;
    }

    private PaymentError() {
    }

    private PaymentError(Parcel in) {
        this.statusCode = in.readInt();
        this.message = in.readString();

        GsonHelper gson = GsonHelper.getInstance();
        try {
            String json = in.readString();
            if (!TextUtils.isEmpty(json)) {
                this.errorInfo = gson.fromJson(json, ErrorInfo.class);
            }
        } catch (JsonSyntaxException e) {
            // this should never happen since we use the same GsonHelper
            // to produce these Json strings
            Log.w("pay", e);
            throw new RuntimeException(e);
        }
        this.cause = (Throwable) in.readSerializable();
        this.networkFailure = in.readInt() == 1;
    }


    /**
     * Helper class to construct a new InternalError from the given Throwable
     *
     * @param cause of the error
     * @return the InternalError containing detailed information about the error
     */
    public static PaymentError fromThrowable(Throwable cause) {
        if (cause instanceof PaymentException) {
            return ((PaymentException) cause).error;
        }
        return new PaymentError(cause);
    }

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
        out.writeInt(statusCode);
        out.writeString(message);

        GsonHelper gson = GsonHelper.getInstance();
        String errorInfoJson = null;
        if (errorInfo != null) {
            errorInfoJson = gson.toJson(errorInfo);
        }
        out.writeString(errorInfoJson);
        out.writeSerializable(cause);
        out.writeInt(networkFailure ? 1 : 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("PaymentError[");

        if (statusCode != 0) {
            sb.append(" statusCode: ");
            sb.append(statusCode);
        }
        if (message != null) {
            sb.append(" message: ");
            sb.append(message);
        }
        if (cause != null) {
            sb.append(" cause: ");
            sb.append(cause.toString());
        }
        sb.append(" networkFailure: ");
        sb.append(networkFailure);
        sb.append("]");
        return sb.toString();
    }
}
