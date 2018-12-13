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

package net.optile.payment.testapp;

import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;

/**
 * Class used to create a custom PaymentTheme for skinning the Android SDK Payment page
 */
final class TestAppTheme {

    /**
     * Construct a new PaymentTheme with the custom dark skin for the Android SDK payment page
     *
     * @return PaymentTheme containing the custom skin
     */
    public static PaymentTheme createCustomTheme() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();

        PageParameters pageParams = PageParameters.createBuilder().
            setPageTheme(R.style.CustomThemePaymentPage).
            setEmptyListLabelStyle(R.style.CustomText_Medium).
            setSectionHeaderLabelStyle(R.style.CustomText_Medium_Bold).
            setNetworkCardTitleStyle(R.style.CustomText_Medium).
            setAccountCardTitleStyle(R.style.CustomText_Medium_Bold).
            setAccountCardSubtitleStyle(R.style.CustomText_Small).
            setPaymentLogoBackground(R.drawable.logo_background).
            build();
        builder.setPageParameters(pageParams);

        WidgetParameters widgetParams = WidgetParameters.createBuilder().
            setTextInputTheme(R.style.CustomThemeTextInput).
            setButtonTheme(R.style.CustomThemeButton).
            setButtonLabelStyle(R.style.CustomText_Medium_Bold).
            setCheckBoxTheme(R.style.CustomThemeCheckBox).
            setCheckBoxLabelCheckedStyle(R.style.CustomText_Medium).
            setCheckBoxLabelUncheckedStyle(R.style.CustomText_Medium_Middle).
            setSelectLabelStyle(R.style.CustomText_Tiny).
            setDefaultIconMapping().
            setValidationColorOk(R.color.custom_validationok).
            setValidationColorUnknown(R.color.custom_validationunknown).
            setValidationColorError(R.color.custom_validationerror).
            build();
        builder.setWidgetParameters(widgetParams);

        DialogParameters dialogParams = DialogParameters.createBuilder().
            setDateTitleStyle(R.style.CustomText_Medium).
            setDateSubtitleStyle(R.style.CustomText_Small_Bold).
            setMessageTitleStyle(R.style.CustomText_Large_Bold).
            setMessageDetailsStyle(R.style.CustomText_Medium).
            setMessageDetailsNoTitleStyle(R.style.CustomText_Medium_Bold).
            setButtonLabelStyle(R.style.CustomText_Small_Bold).
            build();
        builder.setDialogParameters(dialogParams);

        return builder.build();
    }
}
