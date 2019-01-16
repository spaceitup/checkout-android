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
import net.optile.payment.core.LanguageFile;
import android.util.Log;
import net.optile.payment.R;
import android.support.v7.app.AppCompatActivity;

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
    public static void showHintDialog(AppCompatActivity activity, PaymentCard card, String type) {
        MessageDialogFragment dialog = new MessageDialogFragment();
        LanguageFile lang = card.getLang();

        dialog.setTitle(lang.translateAccountHint(type, LanguageFile.TITLE));
        dialog.setMessage(lang.translateAccountHint(type, LanguageFile.TEXT));
        dialog.setNeutralButton(activity.getString(R.string.pmdialog_close_button));
        dialog.setListener(new MessageDialogFragment.MessageDialogListener() {
            @Override
            public void onNeutralButtonClick() {
            }

            @Override
            public void onCancelled() {
            }
        });
        dialog.show(activity.getSupportFragmentManager(), "hint_dialog");
    }
}
