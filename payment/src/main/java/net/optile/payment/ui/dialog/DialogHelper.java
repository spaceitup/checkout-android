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

import android.view.View;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.core.PaymentMethodCodes;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.ui.PaymentUI;

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
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        PaymentTheme theme = PaymentUI.getInstance().getPaymentTheme();
        DialogParameters params = theme.getDialogParameters();
        View snackbarView = snackbar.getView();
        TextView textView = (TextView)snackbarView.findViewById(snackbarTextId);

        if (textView != null) {
            PaymentUtils.setTextAppearance(textView, params.getSnackbarTextStyle());
        }
        return snackbar;
    }
    
    /**
     * Helper method to create a hint dialog for the given paymentcard and input type.
     *
     * @param card PaymentCard from which this request originated
     * @param type input type, i.e. number or verificationCode.
     * @param button label of the back button
     */
    public static MessageDialogFragment createHintDialog(PaymentCard card, String type, String button) {
        LanguageFile lang = card.getLang();
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setTitle(lang.translateAccountHint(type, LanguageFile.TITLE));
        dialog.setMessage(lang.translateAccountHint(type, LanguageFile.TEXT));
        dialog.setImageResId(getHintImageResId(card, type));
        dialog.setNeutralButton(button);
        return dialog;
    }
    
    private static int getHintImageResId(PaymentCard card, String type) {

        if (!PaymentInputType.VERIFICATION_CODE.equals(type)) {
            return 0;
        }
        switch (card.getCode()) {
            case PaymentMethodCodes.AMEX:
                return R.drawable.img_amex;
            default:
                return R.drawable.img_card;
        }
    }
}
