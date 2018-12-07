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

import static org.junit.Assert.*;
import net.optile.payment.R;
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
        assertNotNull(params.getIconParameters());
        assertNotNull(params.getButtonParameters());
        assertNotNull(params.getCheckBoxParameters());
        assertNotNull(params.getDateParameters());
        assertNotNull(params.getListParameters());
        assertNotNull(params.getMessageParameters());
        assertNotNull(params.getTextInputParameters());
    }
    
    @Test
    public void getPageParameters() {
        PageParameters value = PageParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setPageParameters(value).build();
        assertEquals(params.getPageParameters(), value);
    }

    @Test
    public void getIconParameters() {
        IconParameters value = IconParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setIconParameters(value).build();
        assertEquals(params.getIconParameters(), value);
    }

    @Test
    public void getButtonParameters() {
        ButtonParameters value = ButtonParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setButtonParameters(value).build();
        assertEquals(params.getButtonParameters(), value);
    }

    @Test
    public void getCheckBoxParameters() {
        CheckBoxParameters value = CheckBoxParameters.createDefault();
        PaymentTheme params = PaymentTheme.createBuilder().
            setCheckBoxParameters(value).build();
        assertEquals(params.getCheckBoxParameters(), value);
    }
}
