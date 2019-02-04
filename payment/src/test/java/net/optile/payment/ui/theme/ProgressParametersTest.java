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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.optile.payment.R;
import net.optile.payment.core.PaymentInputType;

public class ProgressParametersTest {

    @Test
    public void createBuilder() {
        ProgressParameters.Builder builder = ProgressParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        ProgressParameters params = ProgressParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getLoadBackground(), R.color.pmcolor_list);
        assertEquals(params.getLoadProgressBarColor(), R.color.pmcolor_primary);
        assertEquals(params.getSendBackground(), R.color.pmcolor_list);
        assertEquals(params.getSendProgressBarColorFront(), R.color.pmcolor_primary);
        assertEquals(params.getSendProgressBarColorBack(), R.color.pmvalidation_unknown);
        assertEquals(params.getHeaderStyle(), R.style.PaymentText_XLarge_Bold);
        assertEquals(params.getInfoStyle(), R.style.PaymentText_Medium);
    }

    @Test
    public void getLoadBackground() {
        int value = 101;
        ProgressParameters params = ProgressParameters.createBuilder().
            setLoadBackground(value).build();
        assertEquals(params.getLoadBackground(), value);
    }

    @Test
    public void getLoadProgressBarColor() {
        int value = 102;
        ProgressParameters params = ProgressParameters.createBuilder().
            setLoadProgressBarColor(value).build();
        assertEquals(params.getLoadProgressBarColor(), value);
    }

    @Test
    public void getSendBackground() {
        int value = 103;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendBackground(value).build();
        assertEquals(params.getSendBackground(), value);
    }

    @Test
    public void getSendProgressBarColorFront() {
        int value = 104;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendProgressBarColorFront(value).build();
        assertEquals(params.getSendProgressBarColorFront(), value);
    }

    @Test
    public void getSendProgressBarColorBack() {
        int value = 105;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendProgressBarColorBack(value).build();
        assertEquals(params.getSendProgressBarColorBack(), value);
    }

    @Test
    public void getHeaderStyle() {
        int value = 106;
        ProgressParameters params = ProgressParameters.createBuilder().
            setHeaderStyle(value).build();
        assertEquals(params.getHeaderStyle(), value);
    }

    @Test
    public void getInfoStyle() {
        int value = 107;
        ProgressParameters params = ProgressParameters.createBuilder().
            setInfoStyle(value).build();
        assertEquals(params.getInfoStyle(), value);
    }
}
