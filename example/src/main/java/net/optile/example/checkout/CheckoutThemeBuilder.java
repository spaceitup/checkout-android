/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.checkout;

import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.ui.theme.ProgressParameters;

/**
 * Class used to create a custom PaymentTheme for skinning the Android SDK Payment page
 */
final class CheckoutThemeBuilder {

    /** 
     * Create an empty payment theme
     * 
     * @return the empty theme 
     */
    public static PaymentTheme createEmptyTheme() {
        return PaymentTheme.createBuilder().build();
    }

    /** 
     * Create a default payment theme
     * 
     * @return the default theme 
     */
    public static PaymentTheme createDefaultTheme() {
        return PaymentTheme.createDefault();
    }
    
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
            setPresetCardTitleStyle(R.style.CustomText_Medium_Bold).
            setPresetCardSubtitleStyle(R.style.CustomText_Small).
            setAccountCardTitleStyle(R.style.CustomText_Medium_Bold).
            setAccountCardSubtitleStyle(R.style.CustomText_Small).
            setNetworkCardTitleStyle(R.style.CustomText_Medium_Bold).
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
            setInfoLabelStyle(R.style.CustomText_Small).
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
            setSnackbarTextStyle(R.style.CustomText_Small_Light).
            build();
        builder.setDialogParameters(dialogParams);

        ProgressParameters progressParams = ProgressParameters.createBuilder().
            setLoadBackground(R.color.customColorPrimary).
            setLoadProgressBarColor(R.color.customColorAccent).
            setSendBackground(R.color.custom_cardbackground).
            setSendProgressBarColorFront(R.color.customColorPrimary).
            setSendProgressBarColorBack(R.color.custom_validationunknown).
            setHeaderStyle(R.style.CustomText_XLarge_Bold).
            setInfoStyle(R.style.CustomText_Medium).            
            build();
        builder.setProgressParameters(progressParams);        
        
        return builder.build();
    }
}
