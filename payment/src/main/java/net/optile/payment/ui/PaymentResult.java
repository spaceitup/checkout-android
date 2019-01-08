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

import com.google.gson.JsonSyntaxException;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.PaymentError;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.OperationResult;
import net.optile.payment.util.GsonHelper;

/**
 * Class for holding the payment interaction and operationresult
 */
public final class PaymentResult implements Parcelable {

    public final static Parcelable.Creator<PaymentResult> CREATOR = new Parcelable.Creator<PaymentResult>() {

        public PaymentResult createFromParcel(Parcel in) {
            return new PaymentResult(in);
        }

        public PaymentResult[] newArray(int size) {
            return new PaymentResult[size];
        }
    };
    private final static String TAG = "pay_PaymentResult";

    private PaymentError error;
    private String resultInfo;
    private Interaction interaction;
    private OperationResult operationResult;

    /**
     * Construct a new PaymentResult with PaymentError.
     *
     * @param resultInfo a string containing a description of the payment result
     * @param error the error describing the details about the error situation
     */
    public PaymentResult(String resultInfo, PaymentError error) {
        this.resultInfo = resultInfo;
        this.error = error;
    }

    /**
     * Construct a new PaymentResult with PaymentError.
     *
     * @param operationResult containing the interaction and resultInfo
     */
    public PaymentResult(OperationResult operationResult) {
        this.resultInfo = operationResult.getResultInfo();
        this.interaction = operationResult.getInteraction();
        this.operationResult = operationResult;
    }

    /**
     * Construct a new PaymentResult with the interaction values and the optional operationResult
     *
     * @param resultInfo a string containing a description of the result
     * @param interaction the mandatory interaction
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
            Log.w(TAG, e);
        }
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
