/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import android.app.Activity;
import net.optile.payment.ui.model.PaymentNetwork;

/**
 * Interface for PaymentNetworkServices, a PaymentNetworkService is responsible for activating and 
 * finalizing a payment through the supported network.
 */
public interface PaymentNetworkService {

    /** 
     * Is the PaymentNetwork handled by this PaymentNetworkService supported by this device. 
     * 
     * @return true when supported by this device, false otherwise 
     */
    boolean isSupportedByDevice();

    /** 
     * Does the PaymentNetwork handled by this PaymentNetworkService require activation.
     * 
     * @return true when activation is required for this PaymentNetwork, false otherwise
     */
    boolean requiresActivation();

    /** 
     * To continue with the selected PaymentNetworkService after activation, does it require user interaction to provide details.  
     * 
     * @return true when this PaymentNetworkService require interaction, false otherwise 
     */
    boolean requiresInteraction();
    
    /**
     * Set the listener in this PaymentNetworkService
     * 
     * @param listener the listener to be set
     */
    void setListener(PaymentNetworkListener listener);

    /** 
     * Activate the PaymentNetwork
     */
    void performActivation(PaymentNetwork network);

    /** 
     * Perform the Interaction with the user, the result will be returned through the onActivityResult method.
     *
     * @param requestCode
     * @param activity
     * @param network 
     */
    void performInteraction(int requestCode, Activity activity, PaymentNetwork network);

    /** 
     * Perform the final operation, the result will be returned through the onActivityResult method.
     * 
     * @param requestCode
     * @param activity 
     * @param operation
     */
    void performOperation(int requestCode, Activity activity, Operation operation);

}
