/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.form;

import java.net.URL;
import java.util.Objects;

import com.google.gson.JsonSyntaxException;
import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.PaymentInputType;
import com.payoneer.checkout.model.AccountInputData;
import com.payoneer.checkout.model.BrowserData;
import com.payoneer.checkout.model.OperationData;
import com.payoneer.checkout.util.GsonHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

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
            Log.w("Checkout", e);
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
     * Put a boolean value into this Operation form.
     * Depending on the name of the value it will be added to the correct place in the Operation Json Object.
     *
     * @param name the name of the value
     * @param value containing the value
     */
    public void putBooleanValue(String name, boolean value) throws PaymentException {

        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        AccountInputData account = operationData.getAccount();
        switch (name) {
            case PaymentInputType.OPTIN:
                account.setOptIn(value);
                break;
            case PaymentInputType.ALLOW_RECURRENCE:
                operationData.setAllowRecurrence(value);
                break;
            case PaymentInputType.AUTO_REGISTRATION:
                operationData.setAutoRegistration(value);
                break;
            default:
                String msg = "Operation.putBooleanValue failed for name: " + name;
                throw new PaymentException(msg);
        }
    }

    /**
     * Put a String value into this Operation form.
     * Depending on the name of the value it will be added to the correct place in the Operation Json Object.
     *
     * @param name the name of the value
     * @param value containing the value
     */
    public void putStringValue(String name, String value) throws PaymentException {

        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        AccountInputData account = operationData.getAccount();
        switch (name) {
            case PaymentInputType.HOLDER_NAME:
                account.setHolderName(value);
                break;
            case PaymentInputType.ACCOUNT_NUMBER:
                account.setNumber(value);
                break;
            case PaymentInputType.BANK_CODE:
                account.setBankCode(value);
                break;
            case PaymentInputType.BANK_NAME:
                account.setBankName(value);
                break;
            case PaymentInputType.BIC:
                account.setBic(value);
                break;
            case PaymentInputType.BRANCH:
                account.setBranch(value);
                break;
            case PaymentInputType.CITY:
                account.setCity(value);
                break;
            case PaymentInputType.EXPIRY_MONTH:
                account.setExpiryMonth(value);
                break;
            case PaymentInputType.EXPIRY_YEAR:
                account.setExpiryYear(value);
                break;
            case PaymentInputType.IBAN:
                account.setIban(value);
                break;
            case PaymentInputType.LOGIN:
                account.setLogin(value);
                break;
            case PaymentInputType.PASSWORD:
                account.setPassword(value);
                break;
            case PaymentInputType.VERIFICATION_CODE:
                account.setVerificationCode(value);
                break;
            case PaymentInputType.CUSTOMER_BIRTHDAY:
                account.setCustomerBirthDay(value);
                break;
            case PaymentInputType.CUSTOMER_BIRTHMONTH:
                account.setCustomerBirthMonth(value);
                break;
            case PaymentInputType.CUSTOMER_BIRTHYEAR:
                account.setCustomerBirthYear(value);
                break;
            case PaymentInputType.INSTALLMENT_PLANID:
                account.setInstallmentPlanId(value);
                break;
            default:
                String msg = "Operation.putStringValue failed for name: " + name;
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

    public String toJson() {
        GsonHelper gson = GsonHelper.getInstance();
        return gson.toJson(operationData);
    }

    public URL getURL() {
        return url;
    }
}
