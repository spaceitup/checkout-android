/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.form;

import java.net.URL;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;

/**
 * Class holding Operation form values
 */
public class Operation implements Parcelable {

    public final static String CHARGE = "CHARGE";
    public final static String PRESET = "PRESET";
    public final static String PAYOUT = "PAYOUT";
    public final static String UPDATE = "UPDATE";
    public final static String ACTIVATE = "ACTIVATE";
    public final static Parcelable.Creator<Operation> CREATOR = new Parcelable.Creator<Operation>() {

        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };
    private final URL url;
    private final JSONObject form;
    private final JSONObject account;

    public Operation(URL url) {
        this.url = url;
        this.form = new JSONObject();
        this.account = new JSONObject();
    }

    private Operation(Parcel in) {
        this.url = (URL) in.readSerializable();
        try {
            this.form = new JSONObject(in.readString());
            this.account = new JSONObject(in.readString());
        } catch (JSONException e) {
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
        out.writeSerializable(this.url);
        out.writeString(form.toString());
        out.writeString(account.toString());
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
        try {
            switch (name) {
                case PaymentInputType.ACCOUNT_NUMBER:
                case PaymentInputType.HOLDER_NAME:
                case PaymentInputType.EXPIRY_MONTH:
                case PaymentInputType.EXPIRY_YEAR:
                case PaymentInputType.VERIFICATION_CODE:
                case PaymentInputType.BANK_CODE:
                case PaymentInputType.IBAN:
                case PaymentInputType.BIC:
                    account.put(name, value);
                    break;
                case PaymentInputType.ALLOW_RECURRENCE:
                case PaymentInputType.AUTO_REGISTRATION:
                    form.put(name, value);
            }
        } catch (JSONException e) {
            String msg = "Operation.putValue failed for name: " + name;
            PaymentError error = new PaymentError(PaymentError.INTERNAL_ERROR, msg);
            throw new PaymentException(error, msg, e);
        }
    }

    /**
     * Get the type of this operation, this will either be PRESET, CHARGE, UPDATE or PAYOUT.
     * If the type cannot be determined from the URl then null will be returned.
     *
     * @return the type of the operation or null if it cannot be determined.
     */
    public String getType() {
        String path = this.url.getPath();

        if (path.endsWith("preset")) {
            return PRESET;
        } else if (path.endsWith("charge")) {
            return CHARGE;
        } else if (path.endsWith("update")) {
            return UPDATE;
        } else if (path.endsWith("payout")) {
            return PAYOUT;
        } else if (path.endsWith("activate")) {
            return ACTIVATE;
        }
        return null;
    }

    /**
     * Check if the type of this operation matches the given type.
     *
     * @param type to match with the type of this operation.
     * @return true when the types matches, false otherwise.
     */
    public boolean isType(String type) {
        return Objects.equals(type, getType());
    }

    public String toJson() throws JSONException {
        form.put("account", account);
        return form.toString();
    }

    public URL getURL() {
        return url;
    }
}
