/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.dialog;

import static com.payoneer.mrs.payment.localization.LocalizationKey.BUTTON_CANCEL;
import static com.payoneer.mrs.payment.localization.LocalizationKey.BUTTON_OK;
import static com.payoneer.mrs.payment.localization.LocalizationKey.BUTTON_RETRY;
import static com.payoneer.mrs.payment.localization.LocalizationKey.ERROR_CONNECTION_TEXT;
import static com.payoneer.mrs.payment.localization.LocalizationKey.ERROR_CONNECTION_TITLE;
import static com.payoneer.mrs.payment.localization.LocalizationKey.ERROR_DEFAULT_TEXT;
import static com.payoneer.mrs.payment.localization.LocalizationKey.ERROR_DEFAULT_TITLE;
import static com.payoneer.mrs.payment.localization.LocalizationKey.LABEL_TEXT;
import static com.payoneer.mrs.payment.localization.LocalizationKey.LABEL_TITLE;

import com.google.android.material.snackbar.Snackbar;
import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.core.PaymentInputType;
import com.payoneer.mrs.payment.core.PaymentNetworkCodes;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.model.Interaction;

import android.view.View;

/**
 * Class with helper methods for creating themed dialogs and snackbars.
 */
public class PaymentDialogHelper {

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

    public static PaymentDialogFragment createHintDialog(String networkCode, String type,
        PaymentDialogFragment.PaymentDialogListener listener) {
        PaymentDialogFragment dialog = new PaymentDialogFragment();
        dialog.setTitle(Localization.translateAccountHint(networkCode, type, LABEL_TITLE));
        dialog.setMessage(Localization.translateAccountHint(networkCode, type, LABEL_TEXT));
        dialog.setImageResId(getHintImageResId(networkCode, type));
        dialog.setTag("dialog_hint");
        dialog.setPositiveButton(Localization.translate(BUTTON_OK));
        return dialog;
    }

    public static PaymentDialogFragment createMessageDialog(String title, String message, String tag,
        PaymentDialogFragment.PaymentDialogListener listener) {
        PaymentDialogFragment dialog = new PaymentDialogFragment();
        dialog.setListener(listener);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setTag(tag);
        dialog.setPositiveButton(Localization.translate(BUTTON_OK));
        return dialog;
    }

    public static PaymentDialogFragment createDefaultErrorDialog(PaymentDialogFragment.PaymentDialogListener listener) {
        String title = Localization.translate(ERROR_DEFAULT_TITLE);
        String message = Localization.translate(ERROR_DEFAULT_TEXT);
        return createMessageDialog(title, message, "dialog_defaulterror", listener);
    }

    public static PaymentDialogFragment createInteractionDialog(Interaction interaction,
        PaymentDialogFragment.PaymentDialogListener listener) {
        String title = Localization.translateInteraction(interaction, LABEL_TITLE);
        String message = Localization.translateInteraction(interaction, LABEL_TEXT);
        return createMessageDialog(title, message, "dialog_interaction", listener);
    }

    public static PaymentDialogFragment createConnectionErrorDialog(PaymentDialogFragment.PaymentDialogListener listener) {
        PaymentDialogFragment dialog = new PaymentDialogFragment();
        dialog.setListener(listener);
        dialog.setTitle(Localization.translate(ERROR_CONNECTION_TITLE));
        dialog.setMessage(Localization.translate(ERROR_CONNECTION_TEXT));
        dialog.setNegativeButton(Localization.translate(BUTTON_CANCEL));
        dialog.setPositiveButton(Localization.translate(BUTTON_RETRY));
        dialog.setTag("dialog_connectionerror");
        return dialog;
    }

    private static int getHintImageResId(String networkCode, String type) {

        if (!PaymentInputType.VERIFICATION_CODE.equals(type)) {
            return 0;
        }
        switch (networkCode) {
            case PaymentNetworkCodes.AMEX:
                return R.drawable.img_amex;
            default:
                return R.drawable.img_card;
        }
    }
}
