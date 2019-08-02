/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.model;

import java.net.URL;
import java.util.List;

import net.optile.payment.core.LanguageFile;
import net.optile.payment.model.InputElement;

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
     * Get the paymentMethod of this PaymentCard
     *
     * @return paymentMethod
     */
    String getPaymentMethod();

    /**
     * Get the code of this visible PaymentCard.
     *
     * @return code of the visible network, preset or account
     */
    String getCode();

    /**
     * Get the language file of this PaymentCard
     *
     * @return language file
     */
    LanguageFile getLang();

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
