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

public class ButtonParametersTest {

    @Test
    public void createBuilder() {
        ButtonParameters.Builder builder = ButtonParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        ButtonParameters params = ButtonParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getThemeResId(), R.style.PaymentThemeButton);
        assertEquals(params.getLabelTextAppearance(), R.style.PaymentText_Medium_Bold_Light);
    }
    
    @Test
    public void getThemeResId() {
        int value = 101;
        ButtonParameters params = ButtonParameters.createBuilder().
            setThemeResId(value).build();
        assertEquals(params.getThemeResId(), value);
    }

    @Test
    public void getLabelTextAppearance() {
        int value = 102;
        ButtonParameters params = ButtonParameters.createBuilder().
            setLabelTextAppearance(value).build();
        assertEquals(params.getLabelTextAppearance(), value);
    }
}
