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

public class ChargeParametersTest {

    @Test
    public void createBuilder() {
        ChargeParameters.Builder builder = ChargeParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        ChargeParameters params = ChargeParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getPageTheme(), R.style.PaymentThemeNoActionBar_ChargePayment);
    }

    @Test
    public void getPageTheme() {
        int value = R.style.PaymentThemeNoActionBar_ChargePayment;
        ChargeParameters params = ChargeParameters.createBuilder().
            setPageTheme(value).build();
        assertEquals(params.getPageTheme(), value);
    }
}
