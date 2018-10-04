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

import net.optile.payment.core.PaymentException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class holding the Charge form values
 */
public class Charge {

    final JSONObject account;

    public Charge() {
        account = new JSONObject();
    }

    public void putValue(String name, Object value) throws PaymentException {
        try {
            account.put(name, value);
        } catch (JSONException e) {
            throw new PaymentException("Charge[putValue]", e);
        }
    }

    public String toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        return obj.toString();
    }
}
