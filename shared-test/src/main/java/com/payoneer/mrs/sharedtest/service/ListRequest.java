/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.sharedtest.service;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class holding the request data to create a new list
 */
public final class ListRequest {

    private final String requestBody;

    private ListRequest(Builder builder) throws JSONException {
        JSONObject source = builder.source;

        if (builder.language != null) {
            JSONObject language = source.getJSONObject("style");
            language.put("language", builder.language);
        }
        if (builder.amount != null) {
            JSONObject payment = source.getJSONObject("payment");
            payment.put("amount", builder.amount);
        }
        if (builder.appId != null) {
            JSONObject callback = source.getJSONObject("callback");
            callback.put("appId", builder.appId);
        }
        source.put("presetFirst", builder.presetFirst);
        this.requestBody = source.toString();
    }

    public String getRequestBody() {
        return requestBody;
    }

    public static Builder of(JSONObject source) {
        Objects.requireNonNull(source);
        return new Builder(source);
    }

    public static final class Builder {
        JSONObject source;
        String language;
        String amount;
        String appId;
        boolean presetFirst;


        Builder(JSONObject source) {
            this.source = source;
        }

        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder language(String lang) {
            this.language = language;
            return this;
        }

        public Builder presetFirst(boolean presetFirst) {
            this.presetFirst = presetFirst;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public ListRequest build() throws JSONException {
            return new ListRequest(this);
        }
    }
}
