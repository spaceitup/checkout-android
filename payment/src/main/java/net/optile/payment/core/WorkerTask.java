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

import android.os.Looper;
import android.os.Handler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import android.util.Log;

/**
 * A WorkerTask executing one Callable and notifying the WorkerSubscriber once it is completed
 */
public final class WorkerTask<V> extends FutureTask<V> {

    private final static String TAG = "pay_WorkerTask";

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
    public static <V> WorkerTask<V> fromCallable(final Callable<V> callable) {
        return new WorkerTask<V>(callable);
    }

    /** 
     * Subscribe the WorkerSubscriber to this task, this subscriber will be notified when the task is successfull or has failed
     * 
     * @param subscriber the subscriber to assign to this task
     */
    public void subscribe(WorkerSubscriber<V> subscriber) {
        this.subscriber = subscriber;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void done() {
        WorkerSubscriber subscriber = this.subscriber;
        if (subscriber == null) {
            return;
        }
        try {
            callSuccessOnMainThread(subscriber, get());
        } catch (InterruptedException e) {
            callErrorOnMainThread(subscriber, e);
        } catch (ExecutionException e) {
            callErrorOnMainThread(subscriber, e.getCause());
        }
    }

    private void callSuccessOnMainThread(final WorkerSubscriber subscriber, final V result) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
                public void run() {
                    subscriber.onSuccess(result);
                }
            });
    }

    private void callErrorOnMainThread(final WorkerSubscriber subscriber, final Throwable throwable)  {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
                public void run() {
                    subscriber.onError(throwable);
                }
            });
    }
}
