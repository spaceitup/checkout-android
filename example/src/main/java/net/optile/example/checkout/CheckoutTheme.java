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

import net.optile.example.R;
import net.optile.payment.ui.theme.ButtonParameters;
import net.optile.payment.ui.theme.CheckBoxParameters;
import net.optile.payment.ui.theme.DateParameters;
import net.optile.payment.ui.theme.IconParameters;
import net.optile.payment.ui.theme.ListParameters;
import net.optile.payment.ui.theme.MessageParameters;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.TextInputParameters;

/**
 * Class used to create a custom PaymentTheme for skinning the Android SDK Payment page
 */
public final class CheckoutTheme {

    /** 
     * Construct a new PaymentTheme with the custom dark skin for the Android SDK payment page
     * 
     * @return PaymentTheme containing the custom skin 
     */
    public static PaymentTheme createCustomTheme() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();

        PageParameters pageParams = PageParameters.createBuilder().
            setThemeResId(R.style.CustomThemePaymentPage).
            setEmptyTextAppearance(R.style.CustomText_Medium).
            build();
        builder.setPageParameters(pageParams);

        IconParameters iconParams = IconParameters.createBuilder().
            setDefaultIconMapping().
            setOkColorResId(R.color.custom_validationok).
            setUnknownColorResId(R.color.custom_validationunknown).
            setErrorColorResId(R.color.custom_validationerror).
            build();
        builder.setIconParameters(iconParams);

        ButtonParameters buttonParams = ButtonParameters.createBuilder().
            setThemeResId(R.style.CustomThemeButton).
            setLabelTextAppearance(R.style.CustomText_Medium_Bold).
            build();
        builder.setButtonParameters(buttonParams);

        CheckBoxParameters checkBoxParams = CheckBoxParameters.createBuilder().
            setThemeResId(R.style.CustomThemeCheckBox).
            setCheckedTextAppearance(R.style.CustomText_Medium).
            setUncheckedTextAppearance(R.style.CustomText_Medium_Middle).
            build();
        builder.setCheckBoxParameters(checkBoxParams);

        DateParameters dateParams = DateParameters.createBuilder().
            setDialogTitleTextAppearance(R.style.CustomText_Medium).
            setDialogButtonTextAppearance(R.style.CustomText_Small_Bold).
            build();
        builder.setDateParameters(dateParams);

        ListParameters listParams = ListParameters.createBuilder().
            setHeaderTextAppearance(R.style.CustomText_Medium_Bold).
            setNetworkTitleTextAppearance(R.style.CustomText_Medium).
            setAccountTitleTextAppearance(R.style.CustomText_Medium_Bold).
            setAccountSubtitleTextAppearance(R.style.CustomText_Small).
            setLogoBackgroundResId(R.drawable.logo_background).
            build();
        builder.setListParameters(listParams);

        MessageParameters messageParams = MessageParameters.createBuilder().
            setTitleTextAppearance(R.style.CustomText_Large_Bold).
            setMessageTextAppearance(R.style.CustomText_Medium).
            setMessageNoTitleTextAppearance(R.style.CustomText_Medium_Bold).
            setButtonTextAppearance(R.style.CustomText_Small_Bold).
            build();
        builder.setMessageParameters(messageParams);

        TextInputParameters textInputParams = TextInputParameters.createBuilder().
            setThemeResId(R.style.CustomThemeTextInput).
            build();
        builder.setTextInputParameters(textInputParams);
        
        return builder.build();
    }
}
