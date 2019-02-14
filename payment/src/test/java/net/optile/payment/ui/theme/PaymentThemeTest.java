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
        assertNotNull(params.getDialogParameters());
        assertNotNull(params.getProgressParameters());
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

    @Test
    public void getProgressParameters() {
        ProgressParameters value = ProgressParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setProgressParameters(value).build();
        assertEquals(params.getProgressParameters(), value);
    }
}
