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

package net.optile.payment.form;

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
    
    private final JSONObject form;
    private final JSONObject account;

    public Operation() {
        form = new JSONObject();
        account = new JSONObject();
    }

    /**
     * Put a value for a Operation request into this Operation object.
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

    public String toJson() throws JSONException {
        form.put("account", account);
        return form.toString();
    }
}
