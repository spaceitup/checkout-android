/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.list;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import net.optile.example.checkout.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;
import net.optile.payment.util.PaymentUtils;

public class ListService {

    public String createNewListUrl() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.payment_url);
        String auth = context.getString(R.string.payment_authorization);
        String jsonBody = loadJsonBody();
        return getListUrl(url, auth, jsonBody);
    }

    public ListConfig createNewBodyConfig() throws JSONException, IOException {
        String fileContent = PaymentUtils.readRawResource(InstrumentationRegistry.getContext().getResources(),
            net.optile.example.checkout.test.R.raw.preset);
        JSONObject obj = new JSONObject(fileContent);
        return new ListConfig(obj);
    }

    public String createConfigListUrl(ListConfig config) throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.payment_url);
        String auth = context.getString(R.string.payment_authorization);
        return getListUrl(url, auth, config.toJsonString());
    }

    private String getListUrl(String url, String authorization, String listData) throws IOException {
        ListConnection conn = new ListConnection();
        try {
            ListResult result = conn.createPaymentSession(url, authorization, listData);
            URL selfUrl = getSelfUrl(result.getLinks());
            if (selfUrl == null) {
                throw new IOException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            throw new IOException("Error creating payment session", e);
        }
    }

    private URL getSelfUrl(Map<String, URL> links) {
        return links != null ? links.get("self") : null;
    }

    private String loadJsonBody() throws IOException {
        String json = PaymentUtils.readRawResource(InstrumentationRegistry.getContext().getResources(),
            net.optile.example.checkout.test.R.raw.preset);
        return json;
    }
}
