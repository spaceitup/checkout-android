/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.example.checkout;

import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;

/**
 * Class used to create a custom PaymentTheme for skinning the Android SDK Payment page
 */
final class CheckoutTheme {

    /**
     * Construct a new PaymentTheme with the custom dark skin for the Android SDK payment page
     *
     * @return PaymentTheme containing the custom skin
     */
    public static PaymentTheme createCustomTheme() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();


        PageParameters pageParams = PageParameters.createBuilder().
            setPageTheme(R.style.CustomThemePaymentPage).
            setEmptyListLabelStyle(R.style.CustomText_Medium_Light).
            setSectionHeaderLabelStyle(R.style.CustomText_Medium_Bold_Light).
            setNetworkCardTitleStyle(R.style.CustomText_Medium_Bold).
            setAccountCardTitleStyle(R.style.CustomText_Medium_Bold).
            setAccountCardSubtitleStyle(R.style.CustomText_Small).
            setPaymentLogoBackground(R.drawable.custom_logobackground).
            build();
        builder.setPageParameters(pageParams);

        WidgetParameters widgetParams = WidgetParameters.createBuilder().
            setTextInputTheme(R.style.CustomThemeTextInput).
            setButtonTheme(R.style.CustomThemeButton).
            setButtonLabelStyle(R.style.CustomText_Medium_Bold_Light).
            setButtonBackground(R.drawable.custom_buttonbackground).
            setCheckBoxTheme(R.style.CustomThemeCheckBox).
            setCheckBoxLabelCheckedStyle(R.style.CustomText_Medium).
            setCheckBoxLabelUncheckedStyle(R.style.CustomText_Medium_Gray).
            setSelectLabelStyle(R.style.CustomText_Tiny).
            setValidationColorOk(R.color.custom_validationok).
            setValidationColorUnknown(R.color.custom_validationunknown).
            setValidationColorError(R.color.custom_validationerror).
            setDefaultIconMapping().
            setDefaultHintDrawable().
            build();
        builder.setWidgetParameters(widgetParams);

        DialogParameters dialogParams = DialogParameters.createBuilder().
            setDialogTheme(R.style.CustomThemeDialog).
            setDateTitleStyle(R.style.CustomText_Medium_Bold).
            setDateSubtitleStyle(R.style.CustomText_Small_Bold).
            setMessageTitleStyle(R.style.CustomText_Large_Bold).
            setMessageDetailsStyle(R.style.CustomText_Medium).
            setMessageDetailsNoTitleStyle(R.style.CustomText_Medium_Bold).
            setButtonLabelStyle(R.style.CustomText_Small_Bold_Primary).
            setImageLabelStyle(R.style.CustomText_Tiny).
            build();
        builder.setDialogParameters(dialogParams);

        return builder.build();
    }
}
