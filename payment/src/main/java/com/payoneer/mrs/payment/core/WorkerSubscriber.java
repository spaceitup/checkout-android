/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.core;

/**
 * WorkerSubscriber listening for a WorkerTask to be completed
 */
public interface WorkerSubscriber<V> {

    /**
     * The WorkerTask has successfully completed
     *
     * @param param the result parameter
     */
    void onSuccess(V param);

    /**
     * An error occurred while executing the WorkerTask
     *
     * @param error the cause of the error
     */
    void onError(Throwable error);
}
