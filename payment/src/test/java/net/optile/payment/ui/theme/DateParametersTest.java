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

public class DateParametersTest {

    @Test
    public void createBuilder() {
        DateParameters.Builder builder = DateParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        DateParameters params = DateParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getDialogTitleTextAppearance(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getDialogButtonTextAppearance(), R.style.PaymentText_Small_Bold_Primary);
    }
    
    @Test
    public void getDialogTitleTextAppearance() {
        int value = 101;
        DateParameters params = DateParameters.createBuilder().
            setDialogTitleTextAppearance(value).build();
        assertEquals(params.getDialogTitleTextAppearance(), value);
    }

    @Test
    public void getDialogButtonTextAppearance() {
        int value = 102;
        DateParameters params = DateParameters.createBuilder().
            setDialogButtonTextAppearance(value).build();
        assertEquals(params.getDialogButtonTextAppearance(), value);
    }
}
