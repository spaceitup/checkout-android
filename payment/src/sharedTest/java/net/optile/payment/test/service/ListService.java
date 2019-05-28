/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.test.service;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;
import net.optile.payment.util.PaymentUtils;

public class ListService {

    private final String url;
    private final String auth;
    private final ListConnection conn;

    private ListService(String url, String auth) {
        this.url = url;
        this.auth = auth;
        this.conn = new ListConnection();
    }

    public final static ListService createInstance(String url, String auth) {
        return new ListService(url, auth);
    }

    public String createListUrl(ListConfig config) throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();

        try {
            ListResult result = conn.createPaymentSession(url, auth, config.toJsonString());
            Map<String, URL> links = result.getLinks();
            URL selfUrl = links != null ? links.get("self") : null;

            if (selfUrl == null) {
                throw new IOException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            throw new IOException("Error creating payment session", e);
        }
    }

    public ListConfig createListConfig(int jsonResId) throws JSONException, IOException {
        String fileContent = PaymentUtils.readRawResource(InstrumentationRegistry.getContext().getResources(), jsonResId);
        JSONObject obj = new JSONObject(fileContent);
        return new ListConfig(obj);
    }
}
