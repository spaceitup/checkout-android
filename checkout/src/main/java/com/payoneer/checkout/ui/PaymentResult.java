/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui;

import static com.payoneer.checkout.model.InteractionReason.COMMUNICATION_FAILURE;

import com.google.gson.JsonSyntaxException;
import com.payoneer.checkout.model.ErrorInfo;
import com.payoneer.checkout.model.Interaction;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.util.GsonHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * A container for the payment result as obtained from the Payment API
 */
public final class PaymentResult implements Parcelable {

    public final static String EXTRA_PAYMENT_RESULT = "paymentresult";
    public final static Parcelable.Creator<PaymentResult> CREATOR = new Parcelable.Creator<PaymentResult>() {

        public PaymentResult createFromParcel(Parcel in) {
            return new PaymentResult(in);
        }

        public PaymentResult[] newArray(int size) {
            return new PaymentResult[size];
        }
    };

    private OperationResult operationResult;
    private ErrorInfo errorInfo;
    private Throwable cause;

    /**
     * Construct a new PaymentResult with the operationResult
     *
     * @param operationResult containing the result of the operation
     */
    public PaymentResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    /**
     * Constructs a new PaymentResult with the errorInfo
     *
     * @param errorInfo containing the Interaction and resultInfo
     */
    public PaymentResult(ErrorInfo errorInfo) {
        this(errorInfo, null);
    }

    /**
     * Constructs a new PaymentResult with the errorInfo and optional cause
     *
     * @param errorInfo containing the Interaction and resultInfo
     * @param cause the optional Throwable that caused the error
     */
    public PaymentResult(ErrorInfo errorInfo, Throwable cause) {
        this.errorInfo = errorInfo;
        this.cause = cause;
    }

    private PaymentResult(Parcel in) {
        GsonHelper gson = GsonHelper.getInstance();
        try {
            operationResult = gson.fromJson(in.readString(), OperationResult.class);
            errorInfo = gson.fromJson(in.readString(), ErrorInfo.class);
        } catch (JsonSyntaxException e) {
            // this should never happen since we use the same GsonHelper
            // to produce these Json strings
            Log.w("sdk_PaymentResult", e);
            throw new RuntimeException(e);
        }
        cause = (Throwable) in.readSerializable();
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public Throwable getCause() {
        return cause;
    }

    /**
     * Helper method to obtain the resultInfo from either the operationResult or ErrorInfo
     *
     * @return the resultInfo
     */
    public String getResultInfo() {
        return operationResult != null ? operationResult.getResultInfo() : errorInfo.getResultInfo();
    }

    /**
     * Helper method to obtain the Interaction from either the operationResult or ErrorInfo
     *
     * @return the Interaction
     */
    public Interaction getInteraction() {
        return operationResult != null ? operationResult.getInteraction() : errorInfo.getInteraction();
    }

    /**
     * Check if the error stored in this payment result was caused by a network failure
     *
     * @return true when the error is caused by a network failure, false otherwise
     */
    public boolean isNetworkFailure() {
        return COMMUNICATION_FAILURE.equals(getInteraction().getReason());
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
        GsonHelper gson = GsonHelper.getInstance();
        out.writeString(gson.toJson(operationResult));
        out.writeString(gson.toJson(errorInfo));
        out.writeSerializable(cause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Interaction interaction = getInteraction();
        return "PaymentResult[resultInfo: " + getResultInfo() + ", code: " + interaction.getCode() + ", reason: " + interaction.getReason()
            + "]";
    }
}
