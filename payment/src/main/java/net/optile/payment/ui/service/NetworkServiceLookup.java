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

import android.util.Log;
import net.optile.payment.model.ApplicableNetwork;

/**
 * Class for looking up a NetworkService given the ApplicableNetwork.
 * This will later be implemented by a ServiceLoader.
 */
public class NetworkServiceLookup {

    private static final List<NetworkServiceFactory> factories = new CopyOnWriteArrayList<>();

    /**
     * Is the ApplicableNetwork supported by any of the NetworkServices provided in this Android SDK.
     *
     * @param network check if this network is supported
     * @return true when supported, false otherwise
     */
    public static boolean isNetworkSupported(ApplicableNetwork network) {
        NetworkServiceFactory factory = getFactory(network.getCode());
        return factory != null && factory.isNetworkSupported(network);
    }

    /**
     * Lookup a NetworkService for the provided applicable network
     *
     * @param code the applicable network code
     * @return the NetworkService that can handle the network or null if none found
     */
    public static NetworkService getService(String code) {
        NetworkServiceFactory factory = getFactory(code);
        return factory != null ? factory.createService() : null;
    }

    private static NetworkServiceFactory getFactory(String code) {
        if (factories.size() == 0) {
            initFactories();
        }
        for (NetworkServiceFactory factory : factories) {
            if (factory.isCodeSupported(code)) {
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
