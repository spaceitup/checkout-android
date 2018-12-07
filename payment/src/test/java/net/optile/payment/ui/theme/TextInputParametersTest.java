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

public class TextInputParametersTest {

    @Test
    public void createBuilder() {
        TextInputParameters.Builder builder = TextInputParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        TextInputParameters params = TextInputParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getThemeResId(), R.style.PaymentThemeTextInput);
    }
    
    @Test
    public void getThemeResId() {
        int value = 101;
        TextInputParameters params = TextInputParameters.createBuilder().
            setThemeResId(value).build();
        assertEquals(params.getThemeResId(), value);
    }
}
