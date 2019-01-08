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

package net.optile.payment.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Class with helper methods for Gson
 */
public final class GsonHelper {

    private final Gson gson;

    private GsonHelper() {
        this.gson = new GsonBuilder().create();
    }

    /**
     * Get the instance of this GsonHelper
     *
     * @return the instance of this GsonHelper
     */
    public static GsonHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * This method serializes the specified object into its equivalent Json representation.
     *
     * @return Json representation of src
     */
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }

    private static class InstanceHolder {
        static final GsonHelper INSTANCE = new GsonHelper();
    }
}
