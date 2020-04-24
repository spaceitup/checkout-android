/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.test.service;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class containing the configuration for creating a new list
 */
public final class ListConfig {

    private final JSONObject source;

    /**
     * Create a new ListConfig from the json source object
     *
     * @param source containing the configuration in json format
     */
    public ListConfig(JSONObject source) {
        this.source = source;
    }

    public void setAmount(String amount) throws JSONException {
        JSONObject payment = source.getJSONObject("payment");
        payment.put("amount", amount);
    }

    public void setLanguage(String lang) throws JSONException {
        JSONObject language = source.getJSONObject("style");
        language.put("language", lang);
    }

    public void setPresetFirst(boolean val) throws JSONException {
        source.put("presetFirst", val);
    }

    public void setCallbackAppId(String appId) throws JSONException {
        JSONObject callback = source.getJSONObject("callback");
        appendAppId(callback, "returnUrl", appId);
        appendAppId(callback, "summaryUrl", appId);
        appendAppId(callback, "cancelUrl", appId);
    }

    public String toJsonString() {
        return source.toString();
    }

    private void appendAppId(JSONObject callback, String urlName, String appId) throws JSONException {
        if (callback.has(urlName)) {
            callback.put(urlName, callback.getString(urlName) + appId);
        }
    }
}
