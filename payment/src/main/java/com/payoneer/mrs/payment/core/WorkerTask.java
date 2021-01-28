/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.core;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.os.Handler;
import android.os.Looper;

/**
 * A WorkerTask executing one Callable and notifying the WorkerSubscriber once it is completed.
 * The WorkerSubscriber must be subscribed or unsubscribed on the main UI Thread.
 */
public final class WorkerTask<V> extends FutureTask<V> {

    private WorkerSubscriber<V> subscriber;

    private WorkerTask(Callable<V> callable) {
        super(callable);
    }

    /**
     * Create a new WorkerTask from the Callable
     *
     * @param callable the Callable from which the WorkerTask is created
     * @return newly created WorkerTask
     */
    public static <V> WorkerTask<V> fromCallable(Callable<V> callable) {
        return new WorkerTask<>(callable);
    }

    /**
     * Subscribe the WorkerSubscriber to this task, this subscriber will be notified when the task is successful or has failed
     *
     * @param subscriber the subscriber to assign to this task
     */
    public void subscribe(WorkerSubscriber<V> subscriber) {
        this.subscriber = subscriber;
    }

    /**
     * Unsubscribe from this stask, this subscriber will not be notified anymore
     */
    public void unsubscribe() {
        this.subscriber = null;
    }

    /**
     * Check if a Subscriber has been subscribed to this WorkerTask.
     *
     * @return true when subscribed, false otherwise
     */
    public boolean isSubscribed() {
        return this.subscriber != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void done() {
        try {
            callSuccessOnMainThread(get());
        } catch (InterruptedException e) {
            callErrorOnMainThread(e);
        } catch (ExecutionException e) {
            callErrorOnMainThread(e.getCause());
        }
    }

    private void callSuccessOnMainThread(V result) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (subscriber != null) {
                    subscriber.onSuccess(result);
                }
            }
        });
    }

    private void callErrorOnMainThread(Throwable throwable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (subscriber != null) {
                    subscriber.onError(throwable);
                }
            }
        });
    }
}
