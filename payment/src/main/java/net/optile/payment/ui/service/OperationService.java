/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.util.Log;
import android.content.Context;
import android.text.TextUtils;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentError;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.form.Operation;
import net.optile.payment.model.AccountRegistration;
import net.optile.payment.model.ApplicableNetwork;
import net.optile.payment.model.ListResult;
import net.optile.payment.model.Networks;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.PresetAccount;
import net.optile.payment.network.ListConnection;
import net.optile.payment.network.PaymentConnection;
import net.optile.payment.resource.PaymentGroup;
import net.optile.payment.resource.ResourceLoader;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.NetworkCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;
import net.optile.payment.ui.model.PresetCard;
import net.optile.payment.validation.Validator;

/**
 * The OperationService providing asynchronize posting of the Operation and communication with the Payment API.
 * This service makes callbacks in the operation listener to notify of request completions.
 */
public final class OperationService {
    private final PaymentConnection paymentConnection;
    private OperationListener listener;
    private WorkerTask<OperationResult> operationTask;

    /**
     * Create a new OperationService, this service is used to load the Operation.
     */
    public OperationService() {
        this.paymentConnection = new PaymentConnection();
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
        return paymentConnection.postOperation(operation);
    }
}
