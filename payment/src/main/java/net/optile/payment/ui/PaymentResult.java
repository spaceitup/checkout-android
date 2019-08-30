/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui;

import com.google.gson.JsonSyntaxException;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ErrorInfo;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.OperationResult;
import net.optile.payment.util.GsonHelper;

/**
 * Class for holding the payment result, this class will always contain a resultInfo and an optional Interaction, OperationResult or PaymentError.
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
    private String resultInfo;
    private Interaction interaction;
    private OperationResult operationResult;
    private PaymentError error;

    /**
     * Construct a new PaymentResult given the cause, the cause can either be a PaymentException or any other exception
     *
     * @param cause of the error
     */
    public final static PaymentResult fromThrowable(Throwable cause) {
        if (cause instanceof PaymentException) {
            return fromPaymentException((PaymentException) cause);
        }
        String resultInfo = cause.toString();
        PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, resultInfo);
        return new PaymentResult(resultInfo, error);
    }

    /** 
     * Construct a new PaymentResult from the given PaymentException
     * 
     * @param exception containing the error details
     * 
     * @return the newly created PaymentResult
     */
    public final static PaymentResult fromPaymentException(PaymentException exception) {
        ErrorInfo info = exception.error.errorInfo;
        if (info != null) {
            return new PaymentResult(info.getResultInfo(), info.getInteraction());
        } else {
            return new PaymentResult(exception.getMessage(), exception.error);
        }
    }
    
    /**
     * Construct a new PaymentResult with only the resultInfo.
     *
     * @param resultInfo a string containing a description of the payment result
     */
    public PaymentResult(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    /**
     * Construct a new PaymentResult with resultInfo and error.
     *
     * @param resultInfo a string containing a description of the payment error
     * @param error the error describing the details about the error situation
     */
    public PaymentResult(String resultInfo, PaymentError error) {
        this.resultInfo = resultInfo;
        this.error = error;
    }

    /**
     * Construct a new PaymentResult with the operationResult.
     *
     * @param operationResult containing the result of the operation
     */
    public PaymentResult(OperationResult operationResult) {
        this.resultInfo = operationResult.getResultInfo();
        this.interaction = operationResult.getInteraction();
        this.operationResult = operationResult;
    }

    /**
     * Construct a new PaymentResult with the resultInfo and interaction
     *
     * @param resultInfo a string containing a description of the interaction
     * @param interaction describing the interaction
     */
    public PaymentResult(String resultInfo, Interaction interaction) {
        this.resultInfo = resultInfo;
        this.interaction = interaction;
    }

    private PaymentResult() {
    }

    private PaymentResult(Parcel in) {
        this.resultInfo = in.readString();
        GsonHelper gson = GsonHelper.getInstance();

        try {
            String json = in.readString();
            if (!TextUtils.isEmpty(json)) {
                this.interaction = gson.fromJson(json, Interaction.class);
            }
            json = in.readString();
            if (!TextUtils.isEmpty(json)) {
                this.operationResult = gson.fromJson(json, OperationResult.class);
            }
            json = in.readString();
            if (!TextUtils.isEmpty(json)) {
                this.error = gson.fromJson(json, PaymentError.class);
            }
        } catch (JsonSyntaxException e) {
            // this should never happen since we use the same GsonHelper
            // to produce these Json strings
            Log.w("pay_PaymentResult", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the PaymentResult from the result intent.
     *
     * @param intent containing the PaymentResult
     * @return PaymentResult or null if not stored in the intent
     */
    public final static PaymentResult fromResultIntent(Intent intent) {
        if (intent != null) {
            return intent.getParcelableExtra(EXTRA_PAYMENT_RESULT);
        }
        return null;
    }

    /**
     * Put this PaymentResult into the provided intent.
     *
     * @param intent into which this PaymentResult should be stored.
     */
    public void putInto(Intent intent) {
        intent.putExtra(EXTRA_PAYMENT_RESULT, this);
    }

    public PaymentError getPaymentError() {
        return error;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public boolean hasError() {
        return error != null;
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
        out.writeString(resultInfo);

        String interactionJson = null;
        if (interaction != null) {
            interactionJson = gson.toJson(this.interaction);
        }
        String operationResultJson = null;
        if (operationResult != null) {
            operationResultJson = gson.toJson(this.operationResult);
        }
        String errorJson = null;
        if (error != null) {
            errorJson = gson.toJson(this.error);
        }
        out.writeString(interactionJson);
        out.writeString(operationResultJson);
        out.writeString(errorJson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PaymentResult[");

        if (this.error != null) {
            sb.append(error.toString());
        } else {
            sb.append("resultInfo: ");
            sb.append(this.resultInfo);

            if (interaction != null) {
                sb.append(", code: ");
                sb.append(interaction.getCode());
                sb.append(", reason: ");
                sb.append(interaction.getReason());
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
