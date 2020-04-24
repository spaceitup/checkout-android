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
import androidx.test.platform.app.InstrumentationRegistry;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;
import net.optile.payment.util.PaymentUtils;

/**
 * Class for creating a new ListUrl
 */
public class ListService {

    private final String url;
    private final String auth;
    private final ListConnection conn;

    private ListService(String url, String auth) {
        this.url = url;
        this.auth = auth;
        this.conn = new ListConnection();
    }

    /**
     * Create a new instance of the ListService
     *
     * @param url to which this ListService should connect to
     * @param auth authentication token
     * @return the newly created ListService
     */
    public final static ListService createInstance(String url, String auth) {
        return new ListService(url, auth);
    }

    /**
     * Create a new listUrl given the ListConfig
     *
     * @param config configuration describing which listUrl should be created
     * @return the newly created listUrl
     */
    public String createListUrl(ListConfig config) throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
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

    /**
     * Create a new ListConfig given the json configuration file.
     *
     * @param jsonResId resource ID pointing to the json config file
     * @return the newly created ListConfig
     */
    public ListConfig createListConfig(int jsonResId) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String fileContent = PaymentUtils.readRawResource(context.getResources(), jsonResId);
        JSONObject obj = new JSONObject(fileContent);
        return new ListConfig(obj);
    }

    /**
     * Helper method for creating a listUrl given the json resource id and presetFirst flag
     *
     * @param jsonResId resource ID pointing to the json config file
     * @param presetFirst should the ListConfig be initialized with the presetFirst true or false
     * @return the newly created listUrl
     */
    public static String createListUrl(int jsonResId, boolean presetFirst) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);

        ListService service = ListService.createInstance(url, auth);
        ListConfig config = service.createListConfig(jsonResId);
        config.setPresetFirst(presetFirst);
        config.setCallbackAppId(context.getPackageName());
        return service.createListUrl(config);
    }
}
