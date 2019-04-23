/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.content.Context;
import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The PresetAccountView interface is the View part of the MVP, this is implemented by the PresetAccountActivity
 */
interface PresetAccountView {

    /**
     * Is the view currently active
     *
     * @return true when active, false otherwise
     */
    boolean isActive();

    /**
     * Get the String value given the resource id
     *
     * @return the string value or null if not found
     */
    String getStringRes(int resId);

    /**
     * Show or hide the progress animation, progress animations are shown when lists are loaded or operation requests are performed.
     *
     * @param show if true show the progress animation, hide otherwise
     */
    void showProgress(boolean show);

    /**
     * Show a snackbar message to the user
     *
     * @param message The message to be shown
     */
    void showSnackbar(String message);

    /**
     * Close the payment page
     */
    void closePage();

    /**
     * Show the Themed Dialog
     *
     * @param dialog to be shown
     */
    void showDialog(ThemedDialogFragment dialog);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_CANCELED, PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);

    /**
     * Get the context from this view
     *
     * @return the context of this view
     */
    Context getContext();
}
