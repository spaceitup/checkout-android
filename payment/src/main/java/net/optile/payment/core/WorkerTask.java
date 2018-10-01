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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.os.Handler;
import android.os.Looper;

/**
 * A WorkerTask executing one Callable and notifying the WorkerSubscriber once it is completed
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
     * {@inheritDoc}
     */
    @Override
    protected void done() {
        WorkerSubscriber<V> subscriber = this.subscriber;
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

    private void callSuccessOnMainThread(WorkerSubscriber<V> subscriber, V result) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                subscriber.onSuccess(result);
            }
        });
    }

    private void callErrorOnMainThread(WorkerSubscriber<V> subscriber, Throwable throwable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                subscriber.onError(throwable);
            }
        });
    }
}
