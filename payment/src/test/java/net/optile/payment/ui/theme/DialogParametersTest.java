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

public class DialogParametersTest {

    @Test
    public void createBuilder() {
        DialogParameters.Builder builder = DialogParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        DialogParameters params = DialogParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getDateTitleStyle(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getDateSubtitleStyle(), R.style.PaymentText_Small_Bold);
        assertEquals(params.getMessageTitleStyle(), R.style.PaymentText_Large_Bold);
        assertEquals(params.getMessageDetailsStyle(), R.style.PaymentText_Medium_Gray);
        assertEquals(params.getMessageDetailsNoTitleStyle(), R.style.PaymentText_Medium_Bold_Gray);
        assertEquals(params.getButtonLabelStyle(), R.style.PaymentText_Small_Bold_Primary);
    }

    @Test
    public void getDateTitleStyle() {
        int value = 101;
        DialogParameters params = DialogParameters.createBuilder().
            setDateTitleStyle(value).build();
        assertEquals(params.getDateTitleStyle(), value);
    }

    @Test
    public void getDateSubtitleStyle() {
        int value = 102;
        DialogParameters params = DialogParameters.createBuilder().
            setDateSubtitleStyle(value).build();
        assertEquals(params.getDateSubtitleStyle(), value);
    }

    @Test
    public void getMessageTitleStyle() {
        int value = 103;
        DialogParameters params = DialogParameters.createBuilder().
            setMessageTitleStyle(value).build();
        assertEquals(params.getMessageTitleStyle(), value);
    }

    @Test
    public void getMessageDetailsStyle() {
        int value = 104;
        DialogParameters params = DialogParameters.createBuilder().
            setMessageDetailsStyle(value).build();
        assertEquals(params.getMessageDetailsStyle(), value);
    }

    @Test
    public void getMessageDetailsNoTitleStyle() {
        int value = 105;
        DialogParameters params = DialogParameters.createBuilder().
            setMessageDetailsNoTitleStyle(value).build();
        assertEquals(params.getMessageDetailsNoTitleStyle(), value);
    }

    @Test
    public void getButtonLabelStyle() {
        int value = 106;
        DialogParameters params = DialogParameters.createBuilder().
            setButtonLabelStyle(value).build();
        assertEquals(params.getButtonLabelStyle(), value);
    }
}
