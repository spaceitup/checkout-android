/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service.basic;

import android.app.Activity;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.model.OperationResult;
import net.optile.payment.network.PaymentConnection;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.service.PaymentNetworkListener;
import net.optile.payment.ui.service.PaymentNetworkService;

/**
 *
 */
public class BasicNetworkService implements PaymentNetworkService {

    private final PaymentConnection paymentConnection;
    private PaymentNetworkListener listener;
    private WorkerTask<OperationResult> operationTask;

    /**
     * Create a new BasicNetworkService, this service is a basic implementation that simply send an operation to the Payment API.
     */
    public BasicNetworkService() {
        this.paymentConnection = new PaymentConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupportedByDevice() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requiresActivation() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requiresInteraction() {
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setListener(PaymentNetworkListener listener) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performActivation(PaymentNetwork network) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performInteraction(Activity activity, PaymentNetwork network) {
    }
}
