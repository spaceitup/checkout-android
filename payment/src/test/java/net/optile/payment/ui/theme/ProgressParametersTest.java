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
        assertEquals(params.getSendBackground(), R.color.pmcolor_background);
        assertEquals(params.getSendProgressBarColorFront(), R.color.pmcolor_primary);
        assertEquals(params.getSendProgressBarColorBack(), R.color.pmvalidation_unknown);
        assertEquals(params.getHeaderStyle(), R.style.PaymentText_XLarge_Bold);
        assertEquals(params.getInfoStyle(), R.style.PaymentText_Medium);
    }

    @Test
    public void getLoadBackground() {
        int value = R.color.pmcolor_list;
        ProgressParameters params = ProgressParameters.createBuilder().
            setLoadBackground(value).build();
        assertEquals(params.getLoadBackground(), value);
    }

    @Test
    public void getLoadProgressBarColor() {
        int value = R.color.pmcolor_primary;
        ProgressParameters params = ProgressParameters.createBuilder().
            setLoadProgressBarColor(value).build();
        assertEquals(params.getLoadProgressBarColor(), value);
    }

    @Test
    public void getSendBackground() {
        int value = R.color.pmcolor_list;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendBackground(value).build();
        assertEquals(params.getSendBackground(), value);
    }

    @Test
    public void getSendProgressBarColorFront() {
        int value = R.color.pmcolor_primary;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendProgressBarColorFront(value).build();
        assertEquals(params.getSendProgressBarColorFront(), value);
    }

    @Test
    public void getSendProgressBarColorBack() {
        int value = R.color.pmvalidation_unknown;
        ProgressParameters params = ProgressParameters.createBuilder().
            setSendProgressBarColorBack(value).build();
        assertEquals(params.getSendProgressBarColorBack(), value);
    }

    @Test
    public void getHeaderStyle() {
        int value = R.style.PaymentText_XLarge_Bold;
        ProgressParameters params = ProgressParameters.createBuilder().
            setHeaderStyle(value).build();
        assertEquals(params.getHeaderStyle(), value);
    }

    @Test
    public void getInfoStyle() {
        int value = R.style.PaymentText_Medium;
        ProgressParameters params = ProgressParameters.createBuilder().
            setInfoStyle(value).build();
        assertEquals(params.getInfoStyle(), value);
    }
}
