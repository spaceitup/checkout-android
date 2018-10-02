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

package net.optile.payment.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Singleton class maintaining a list of Workers processing background tasks
 */
public final class Workers {

    private ExecutorService networkService;

    private Workers() {
        this.networkService = Executors.newCachedThreadPool();
    }

    /**
     * Get the instance of this Workers
     *
     * @return the instance of this Workers
     */
    public static Workers getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Get the WorkerExecutor for network tasks
     *
     * @return the network executor for network tasks
     */
    public ExecutorService forNetworkTasks() {
        return networkService;
    }

    private static class InstanceHolder {
        static final Workers INSTANCE = new Workers();
    }
}
