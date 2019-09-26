/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.dialog;

import static net.optile.payment.localization.LocalizationKey.ACCOUNTHINT_TEXT;
import static net.optile.payment.localization.LocalizationKey.ACCOUNTHINT_TITLE;

import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import net.optile.payment.R;
import net.optile.payment.localization.Localization;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.core.PaymentNetworkCodes;
import net.optile.payment.ui.model.PaymentCard;

/**
 * Class with helper methods for creating themed dialogs and snackbars.
 */
public class DialogHelper {

    /**
     * Create a themed Snackbar given the view and message this Snackbar should show
     *
     * @param view the view this Snackbar is attached to
     * @param message shown in the Snackbar
     * @return the newly created Snackbar
     */
    public static Snackbar createSnackbar(View view, String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG);
    }

    /**
     * Helper method to create a hint dialog for the given paymentcard and input type.
     *
     * @param card PaymentCard from which this request originated
     * @param type input type, i.e. number or verificationCode.
     * @param button label of the back button
     */
    public static MessageDialogFragment createHintDialog(PaymentCard card, String type, String button) {
        String code = card.getCode();
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setTitle(Localization.translateAccountHint(code, type, ACCOUNTHINT_TITLE));
        dialog.setMessage(Localization.translateAccountHint(code, type, ACCOUNTHINT_TEXT));
        dialog.setImageResId(getHintImageResId(card, type));
        dialog.setNeutralButton(button);
        return dialog;
    }

    private static int getHintImageResId(PaymentCard card, String type) {

        if (!PaymentInputType.VERIFICATION_CODE.equals(type)) {
            return 0;
        }
        switch (card.getCode()) {
            case PaymentNetworkCodes.AMEX:
                return R.drawable.img_amex;
            default:
                return R.drawable.img_card;
        }
    }
}
