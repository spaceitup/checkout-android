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
