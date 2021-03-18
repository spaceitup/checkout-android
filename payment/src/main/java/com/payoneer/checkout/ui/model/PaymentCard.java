/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.List;

import com.payoneer.checkout.model.InputElement;

/**
 * Interface for payment cards like AccountCard and NetworkCard
 */
public interface PaymentCard {

    /**
     * Check if this PaymentCard contains a link with name and value
     *
     * @param name the name of the link
     * @param url to match with the URL in this PaymentCard
     * @return true when this PaymentCard contains the URL, false otherwise
     */
    boolean containsLink(String name, URL url);

    /**
     * Get the operation link, this link can be used to make i.e. a charge request
     *
     * @return the operation link or null if it does not exist
     */
    URL getOperationLink();

    /**
     * Get the operation type, e.g. OperationType.CHARGE or OperationType.PRESET
     *
     * @return the operationType of this payment card
     */
    String getOperationType();

    /**
     * Get the paymentMethod of this PaymentCard
     *
     * @return paymentMethod of this PaymentCard
     */
    String getPaymentMethod();

    /**
     * Get the code of this PaymentCard.
     *
     * @return code of this PaymentCard
     */
    String getCode();

    /**
     * Get the label of this PaymentCard
     *
     * @return label of this PaymentCard
     */
    String getLabel();

    /**
     * Get the action button label
     *
     * @return the action button label or null if not found
     */
    String getButton();

    /**
     * Get the list of input elements supported by this  payment card
     *
     * @return list of InputElements
     */
    List<InputElement> getInputElements();

    /**
     * Get the InputElement given the name
     *
     * @param name of the InputElement to be returned
     * @return the InputElement with the given name or null if not found
     */
    InputElement getInputElement(String name);

    /**
     * Notify that text input has changed for one of the input fields in this PaymentCard.
     *
     * @param type the type of the TextInput field
     * @param text new text of the input field
     * @return true when this PaymentCard has changed its appearance because of the new input, false otherwise
     */
    boolean onTextInputChanged(String type, String text);
}
