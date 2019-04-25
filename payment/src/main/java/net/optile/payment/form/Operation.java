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

import android.text.TextUtils;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.PaymentInputType;

/**
 * Class holding Operation form values
 */
public class Operation {

    public final static String CHARGE = "CHARGE";
    public final static String PRESET = "PRESET";
    public final static String PAYOUT = "PAYOUT";
    public final static String UPDATE = "UPDATE";
    public final static String ACTIVATE = "ACTIVATE";
    
    private final URL url;
    private final JSONObject form;
    private final JSONObject account;

    public Operation(URL url) {
        this.url = url;
        this.form = new JSONObject();
        this.account = new JSONObject();
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
            PaymentError error = new PaymentError("Operation", PaymentError.INTERNAL_ERROR, msg);
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
