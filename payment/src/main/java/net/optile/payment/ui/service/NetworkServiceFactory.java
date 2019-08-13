/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import net.optile.payment.model.ApplicableNetwork;

/**
 * Interface for all payment network factories. A payment network factory is capable of creating a NetworkService instance for a specific PaymentNetwork type.
 */
public interface NetworkServiceFactory {

    /** 
     * Check if the ApplicableNetwork is supported by this factory.
     * 
     * @param network to be checked if it is supported
     * @return true when supported, false otherwise
     */
    boolean isNetworkSupported(ApplicableNetwork network);

    /**
     * Check if the network code is supported by this factory.
     * 
     * @param code to be checked if it is supported by this factory.
     * @return true when supported, false otherwise
     */
    boolean isCodeSupported(String code);
    
    /** 
     * Create a service for this specific payment network
     * 
     * @return the newly created service
     */
    NetworkService createService();
}
