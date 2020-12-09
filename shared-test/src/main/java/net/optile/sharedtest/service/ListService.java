/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.sharedtest.service;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
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
     * @param baseUrl to which this ListService should connect to
     * @param authHeader authentication token
     * @return the newly created ListService
     */
    public final static ListService createInstance(String baseUrl, String authHeader) {
        return new ListService(baseUrl, authHeader);
    }

    /**
     * Create a new listUrl given the ListConfig
     *
     * @param request configuration describing which listUrl should be created
     * @return the newly created listUrl
     */
    public String createListUrl(ListRequest request) throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        try {
            ListResult result = conn.createPaymentSession(url, auth, request.getRequestBody());
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
     * @return the JSONObject containing the template request body
     */
    public JSONObject loadJSONTemplate(int jsonResId) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        String fileContent = PaymentUtils.readRawResource(context.getResources(), jsonResId);
        return new JSONObject(fileContent);
    }

    /**
     * Helper method for creating a listUrl given the json resource id and presetFirst flag
     *
     * @param jsonResId resource ID pointing to the json config file
     * @param presetFirst should the ListConfig be initialized with the presetFirst true or false
     * @param baseUrl pointing to the API for creating new lists
     * @param authHeader content of the authentication header
     * @return the newly created listUrl
     */
    public static String createListUrl(int jsonResId, boolean presetFirst, String baseUrl, String authHeader)
        throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ListService service = ListService.createInstance(baseUrl, authHeader);
        ListRequest request = ListRequest.of(service.loadJSONTemplate(jsonResId))
            .presetFirst(presetFirst)
            .appId(context.getPackageName()).build();
        return service.createListUrl(request);
    }
}
