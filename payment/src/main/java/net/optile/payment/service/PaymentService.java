/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.service;

import java.util.concurrent.Callable;

import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.form.Operation;
import net.optile.payment.model.OperationResult;
import net.optile.payment.network.PaymentConnection;

/**
 * The PaymentService providing asynchronize communication with the Payment API to execute operation requests.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class PaymentService {

    private final PaymentConnection paymentConnection;
    private PaymentListener listener;
    private WorkerTask<OperationResult> task;

    /**
     * Create a new PaymentService for executing operation requests
     */
    public PaymentService() {
        this.paymentConnection = new PaymentConnection();
    }

    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {

        if (task != null) {
            task.unsubscribe();
            task = null;
        }
    }

    /**
     * Set the listener in this payment service.
     *
     * @param listener to be notified when the request is completed or has failed.
     */
    public void setListener(PaymentListener listener) {
        this.listener = listener;
    }

    /**
     * Is this payment service active
     *
     * @return true when active and false otherwise
     */
    public boolean isActive() {
        return task != null;
    }

    /**
     * Post an operation to the Payment API
     *
     * @param operation to be posted to the Payment API
     */
    public void postOperation(final Operation operation) {

        if (task != null) {
            throw new IllegalStateException("Already posting operation, stop first");
        }
        task = WorkerTask.fromCallable(new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws PaymentException {
                return asyncPostOperation(operation);
            }
        });
        task.subscribe(new WorkerSubscriber<OperationResult>() {
            @Override
            public void onSuccess(OperationResult result) {
                task = null;

                if (listener != null) {
                    listener.onOperationSuccess(result);
                }
            }

            @Override
            public void onError(Throwable cause) {
                task = null;

                if (listener != null) {
                    listener.onOperationError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(task);
    }

    /**
     * Post an Operation to the Payment API
     *
     * @param operation the object containing the operation details
     * @return operation result containing information about the operation request
     */
    private OperationResult asyncPostOperation(Operation operation) throws PaymentException {
        return paymentConnection.postOperation(operation);
    }

    /**
     * PaymentService listener interface
     */
    public static interface PaymentListener {

        /**
         * Called when the operation was successfully executed
         *
         * @param result the result of the operation
         */
        public void onOperationSuccess(OperationResult result);

        /**
         * Called when an error occured while posting the operation
         *
         * @param cause the reason why it failed
         */
        public void onOperationError(Throwable cause);
    }
}
