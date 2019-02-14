/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.theme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.optile.payment.R;

public class PageParametersTest {

    @Test
    public void createBuilder() {
        PageParameters.Builder builder = PageParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        PageParameters params = PageParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getPageTheme(), R.style.PaymentTheme_PaymentPage);
        assertEquals(params.getEmptyListLabelStyle(), R.style.PaymentText_Medium_Gray);
        assertEquals(params.getSectionHeaderLabelStyle(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getPresetCardTitleStyle(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getPresetCardSubtitleStyle(), R.style.PaymentText_Tiny);
        assertEquals(params.getAccountCardTitleStyle(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getAccountCardSubtitleStyle(), R.style.PaymentText_Tiny);
        assertEquals(params.getNetworkCardTitleStyle(), R.style.PaymentText_Medium);
        assertEquals(params.getPaymentLogoBackground(), 0);
    }

    @Test
    public void getPageTheme() {
        int value = R.style.PaymentTheme_PaymentPage;
        PageParameters params = PageParameters.createBuilder().
            setPageTheme(value).build();
        assertEquals(params.getPageTheme(), value);
    }

    @Test
    public void getEmptyListLabelStyle() {
        int value = R.style.PaymentText_Medium_Gray;
        PageParameters params = PageParameters.createBuilder().
            setEmptyListLabelStyle(value).build();
        assertEquals(params.getEmptyListLabelStyle(), value);
    }

    @Test
    public void getSectionHeaderLabelStyle() {
        int value = R.style.PaymentText_Medium_Bold;
        PageParameters params = PageParameters.createBuilder().
            setSectionHeaderLabelStyle(value).build();
        assertEquals(params.getSectionHeaderLabelStyle(), value);
    }

    @Test
    public void getPresetCardTitleStyle() {
        int value = R.style.PaymentText_Medium_Bold;
        PageParameters params = PageParameters.createBuilder().
            setPresetCardTitleStyle(value).build();
        assertEquals(params.getPresetCardTitleStyle(), value);
    }

    @Test
    public void getPresetCardSubtitleStyle() {
        int value = R.style.PaymentText_Tiny;
        PageParameters params = PageParameters.createBuilder().
            setPresetCardSubtitleStyle(value).build();
        assertEquals(params.getPresetCardSubtitleStyle(), value);
    }

    @Test
    public void getAccountCardTitleStyle() {
        int value = R.style.PaymentText_Medium_Bold;
        PageParameters params = PageParameters.createBuilder().
            setAccountCardTitleStyle(value).build();
        assertEquals(params.getAccountCardTitleStyle(), value);
    }

    @Test
    public void getAccountCardSubtitleStyle() {
        int value = R.style.PaymentText_Tiny;
        PageParameters params = PageParameters.createBuilder().
            setAccountCardSubtitleStyle(value).build();
        assertEquals(params.getAccountCardSubtitleStyle(), value);
    }

    @Test
    public void getNetworkCardTitleStyle() {
        int value = R.style.PaymentText_Medium;
        PageParameters params = PageParameters.createBuilder().
            setNetworkCardTitleStyle(value).build();
        assertEquals(params.getNetworkCardTitleStyle(), value);
    }

    @Test
    public void getPaymentLogoBackground() {
        int value = R.color.pmcolor_background;
        PageParameters params = PageParameters.createBuilder().
            setPaymentLogoBackground(value).build();
        assertEquals(params.getPaymentLogoBackground(), value);
    }
}
