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

public class PaymentThemeTest {

    @Test
    public void createBuilder() {
        PaymentTheme.Builder builder = PaymentTheme.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        PaymentTheme params = PaymentTheme.createDefault();
        assertNotNull(params);
        assertNotNull(params.getPageParameters());
        assertNotNull(params.getWidgetParameters());
        assertNotNull(params.getPageParameters());
        assertNotNull(params.getDialogParameters());
    }

    @Test
    public void getPageParameters() {
        PageParameters value = PageParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setPageParameters(value).build();
        assertEquals(params.getPageParameters(), value);
    }

    @Test
    public void getWidgetParameters() {
        WidgetParameters value = WidgetParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setWidgetParameters(value).build();
        assertEquals(params.getWidgetParameters(), value);
    }

    @Test
    public void getDialogParameters() {
        DialogParameters value = DialogParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setDialogParameters(value).build();
        assertEquals(params.getDialogParameters(), value);
    }
}
