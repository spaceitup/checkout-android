/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.form;

import java.net.URL;
import java.util.Objects;

import org.json.JSONException;

import com.google.gson.JsonSyntaxException;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.AccountInputData;
import net.optile.payment.model.BrowserData;
import net.optile.payment.model.OperationData;
import net.optile.payment.util.GsonHelper;

/**
 * Class holding Operation form values
 */
public class Operation implements Parcelable {

    public final static Parcelable.Creator<Operation> CREATOR = new Parcelable.Creator<Operation>() {
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };
    private final String networkCode;
    private final String paymentMethod;
    private final String operationType;
    private final URL url;
    private final OperationData operationData;

    public Operation(String networkCode, String paymentMethod, String operationType, URL url) {
        this.networkCode = networkCode;
        this.paymentMethod = paymentMethod;
        this.operationType = operationType;
        this.url = url;

        operationData = new OperationData();
        operationData.setAccount(new AccountInputData());
    }

    public void setBrowserData(BrowserData browserData) {
        operationData.setBrowserData(browserData);
    }

    private Operation(Parcel in) {
        this.networkCode = in.readString();
        this.paymentMethod = in.readString();
        this.operationType = in.readString();
        this.url = (URL) in.readSerializable();

        try {
            GsonHelper gson = GsonHelper.getInstance();
            operationData = gson.fromJson(in.readString(), OperationData.class);
        } catch (JsonSyntaxException e) {
            // this should never happen since we use the same GsonHelper
            // to produce these Json strings
            Log.w("android-sdk", e);
            throw new RuntimeException(e);
        }
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
        out.writeString(networkCode);
        out.writeString(paymentMethod);
        out.writeString(operationType);
        out.writeSerializable(url);

        GsonHelper gson = GsonHelper.getInstance();
        out.writeString(gson.toJson(operationData));
    }

    /**
     * Put a value into this Operation form.
     * Depending on the name of the value it will be added to the correct place in the Operation Json Object.
     *
     * @param name the name of the value
     * @param value containing the value
     */
    public void putValue(String name, Object value) throws PaymentException {

        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        AccountInputData account = operationData.getAccount();
        String strValue = value != null ? value.toString() : null;

        switch (name) {
            case PaymentInputType.ACCOUNT_NUMBER:
                account.setNumber(strValue);
                break;
            case PaymentInputType.HOLDER_NAME:
                account.setHolderName(strValue);
                break;
            case PaymentInputType.EXPIRY_MONTH:
                account.setExpiryMonth(strValue);
                break;
            case PaymentInputType.EXPIRY_YEAR:
                account.setExpiryYear(strValue);
                break;
            case PaymentInputType.VERIFICATION_CODE:
                account.setVerificationCode(strValue);
                break;
            case PaymentInputType.BANK_CODE:
                account.setBankCode(strValue);
                break;
            case PaymentInputType.IBAN:
                account.setIban(strValue);
                break;
            case PaymentInputType.BIC:
                account.setBic(strValue);
                break;
            case PaymentInputType.ALLOW_RECURRENCE:
                operationData.setAllowRecurrence(Boolean.parseBoolean(strValue));
                break;
            case PaymentInputType.AUTO_REGISTRATION:
                operationData.setAutoRegistration(Boolean.parseBoolean(strValue));
                break;
            default:
                String msg = "Operation.putValue failed for name: " + name;
                throw new PaymentException(msg);
        }
    }

    /**
     * Get the type of this operation, this will either be PRESET, CHARGE, UPDATE, ACTIVATION or PAYOUT.
     * If the type cannot be determined from the URl then null will be returned.
     *
     * @return the type of the operation or null if it cannot be determined.
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Check if the type of this operation matches the given type.
     *
     * @param operationType to match with the type of this operation.
     * @return true when the types matches, false otherwise.
     */
    public boolean isOperationType(String operationType) {
        return Objects.equals(operationType, getOperationType());
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public OperationData getOperationData() {
        return operationData;
    }

    public String toJson() throws JSONException {
        GsonHelper gson = GsonHelper.getInstance();
        return gson.toJson(operationData);
    }

    public URL getURL() {
        return url;
    }
}
