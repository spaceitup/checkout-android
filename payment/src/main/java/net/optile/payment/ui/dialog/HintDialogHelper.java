/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.dialog;

import android.app.Activity;
import net.optile.payment.ui.model.PaymentCard;
import android.util.Log;

/**
 * Class with helper methods for showing a hint dialog.
 */
public class HintDialogHelper {

    /** 
     * Show a hint dialog to the user for the given input type.
     *
     * @param activity to which the dialog will be attached to 
     * @param card PaymentCard from which this request originated
     * @param type input type, i.e. number or verificationCode.
     */
    public static void showHintDialog(Activity activity, PaymentCard card, String type) {
        Log.i("pay_Hint", "Hint dialog clicked: " + type);
    }
}
