/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.service;

import java.util.concurrent.Callable;

import com.payoneer.checkout.core.PaymentException;
import com.payoneer.checkout.core.WorkerSubscriber;
import com.payoneer.checkout.core.WorkerTask;
import com.payoneer.checkout.core.Workers;
import com.payoneer.checkout.form.BrowserDataBuilder;
import com.payoneer.checkout.form.Operation;
import com.payoneer.checkout.model.BrowserData;
import com.payoneer.checkout.model.OperationResult;
import com.payoneer.checkout.network.PaymentConnection;

import android.content.Context;

/**
 * The OperationService providing asynchronous posting of the Operation and communication with the Payment API.
 * This service makes callbacks in the operation listener to notify of request completions.
 */
public final class OperationService {
    private final PaymentConnection paymentConnection;
    private final BrowserData browserData;
    private OperationListener listener;
    private WorkerTask<OperationResult> operationTask;

    /**
     * Create a new OperationService, this service is used to load the Operation.
     *
     * @param context context in which this service will run
     */
    public OperationService(Context context) {
        paymentConnection = new PaymentConnection(context);
        browserData = BrowserDataBuilder.createFromContext(context);
    }

    /**
     * Set the operation listener which will be informed about the state of a operation.
     *
     * @param listener to be informed about the operation being posted.
     */
    public void setListener(OperationListener listener) {
        this.listener = listener;
    }

    /**
     * Stop and unsubscribe from the task that is currently active in this service.
     */
    public void stop() {
        if (operationTask != null) {
            operationTask.unsubscribe();
            operationTask = null;
        }
    }

    /**
     * Check if this service is currently active posting an operation to the Payment API
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return operationTask != null && operationTask.isSubscribed();
    }

    /**
     * Post an operation to the Payment API
     *
     * @param operation to be posted to the Payment API
     */
    public void postOperation(final Operation operation) {

        if (isActive()) {
            throw new IllegalStateException("Already posting operation, stop first");
        }
        operationTask = WorkerTask.fromCallable(new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws PaymentException {
                return asyncPostOperation(operation);
            }
        });
        operationTask.subscribe(new WorkerSubscriber<OperationResult>() {
            @Override
            public void onSuccess(OperationResult result) {
                operationTask = null;

                if (listener != null) {
                    listener.onOperationSuccess(result);
                }
            }

            @Override
            public void onError(Throwable cause) {
                operationTask = null;

                if (listener != null) {
                    listener.onOperationError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(operationTask);
    }

    private OperationResult asyncPostOperation(Operation operation) throws PaymentException {
        operation.setBrowserData(browserData);
        return paymentConnection.postOperation(operation);
    }
}
