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

package net.optile.payment.ui.theme;

import net.optile.payment.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals(params.getAccountCardTitleStyle(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getAccountCardSubtitleStyle(), R.style.PaymentText_Tiny);
        assertEquals(params.getNetworkCardTitleStyle(), R.style.PaymentText_Medium);
        assertEquals(params.getPaymentLogoBackground(), 0);
    }

    @Test
    public void getPageTheme() {
        int value = 101;
        PageParameters params = PageParameters.createBuilder().
            setPageTheme(value).build();
        assertEquals(params.getPageTheme(), value);
    }

    @Test
    public void getEmptyListLabelStyle() {
        int value = 102;
        PageParameters params = PageParameters.createBuilder().
            setEmptyListLabelStyle(value).build();
        assertEquals(params.getEmptyListLabelStyle(), value);
    }

    @Test
    public void getSectionHeaderLabelStyle() {
        int value = 101;
        PageParameters params = PageParameters.createBuilder().
            setSectionHeaderLabelStyle(value).build();
        assertEquals(params.getSectionHeaderLabelStyle(), value);
    }

    @Test
    public void getAccountCardTitleStyle() {
        int value = 102;
        PageParameters params = PageParameters.createBuilder().
            setAccountCardTitleStyle(value).build();
        assertEquals(params.getAccountCardTitleStyle(), value);
    }

    @Test
    public void getAccountCardSubtitleStyle() {
        int value = 103;
        PageParameters params = PageParameters.createBuilder().
            setAccountCardSubtitleStyle(value).build();
        assertEquals(params.getAccountCardSubtitleStyle(), value);
    }

    @Test
    public void getNetworkCardTitleStyle() {
        int value = 104;
        PageParameters params = PageParameters.createBuilder().
            setNetworkCardTitleStyle(value).build();
        assertEquals(params.getNetworkCardTitleStyle(), value);
    }

    @Test
    public void getPaymentLogoBackground() {
        int value = 105;
        PageParameters params = PageParameters.createBuilder().
            setPaymentLogoBackground(value).build();
        assertEquals(params.getPaymentLogoBackground(), value);
    }
}
