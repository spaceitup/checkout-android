/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Objects;

import android.util.Log;

/**
 * Class for looking up a NetworkService given the code and payment method.
 * This will later be implemented by a ServiceLoader.
 */
public class NetworkServiceLookup {

    private static final List<NetworkServiceFactory> factories = new CopyOnWriteArrayList<>();

    /**
     * Check if there is a NetworkService that supports the network code and payment method 
     *
     * @param code to be checked if it is supported
     * @param method to be checked if it is supported
     * @return true when supported, false otherwise
     */
    public static boolean isSupported(String code, String method) {
        NetworkServiceFactory factory = getFactory(code, method);
        return factory != null;
    }

    /**
     * Lookup a NetworkService for the network code and payment method
     *
     * @param code to be used to lookup a NetworkService
     * @param method to be used to lookup a NetworkService
     * @return the NetworkService that can handle the network or null if none found
     */
    public static NetworkService getService(String code, String method) {
        NetworkServiceFactory factory = getFactory(code, method);
        return factory != null ? factory.createService() : null;
    }

    private static NetworkServiceFactory getFactory(String code, String method) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(method);

        if (factories.size() == 0) {
            initFactories();
        }
        for (NetworkServiceFactory factory : factories) {
            if (factory.isSupported(code, method)) {
                return factory;
            }
        }
        return null;
    }

    private static void initFactories() {
        synchronized (factories) {
            if (factories.size() == 0) {
                loadFactory(factories, "net.optile.network.basic.BasicNetworkServiceFactory");
            }
        }
    }

    private static void loadFactory(List<NetworkServiceFactory> factories, String className) {
        try {
            NetworkServiceFactory factory = (NetworkServiceFactory) Class.forName(className).newInstance();
            factories.add(factory);
        } catch (Exception e) {
            Log.w("sdk_NetworkService", e);
        }
    }
}
