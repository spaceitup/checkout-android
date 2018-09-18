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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import android.util.Log;

/**
 * A WorkerTask executing one task
 */
public final class WorkerTask<V> extends FutureTask<V> {

    private final static String TAG = "pay_WorkerTask";

    /** 
     * The subscriber notified when this task is completed 
     */
    private WorkerSubscriber subscriber;

    private WorkerTask(Callable<V> callable) {
        super(callable);
    }

    /** 
     * Create a new WorkerTask from the Callable
     * 
     * @param callable 
     * @return The newly created WorkerTask 
     */
    public final static <V> WorkerTask<V> fromCallable(final Callable<V> callable) {
        return new WorkerTask<V>(callable);
    }

    public void subscribe(WorkerSubscriber<V> subscriber) {
        this.subscriber = subscriber;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void done() {
        WorkerSubscriber s = this.subscriber;
        if (s == null) {
            return;
        }
        try {
            V result = get();
            s.onSuccess(result);
        } catch (InterruptedException e) {
            s.onError(e);
        } catch (ExecutionException e) {
            s.onError(e.getCause());
        }
    }
}
