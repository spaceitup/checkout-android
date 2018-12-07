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

public class MessageParametersTest {

    @Test
    public void createBuilder() {
        MessageParameters.Builder builder = MessageParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        MessageParameters params = MessageParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getTitleTextAppearance(), R.style.PaymentText_Large_Bold);
        assertEquals(params.getMessageTextAppearance(), R.style.PaymentText_Medium_Gray);
        assertEquals(params.getMessageNoTitleTextAppearance(), R.style.PaymentText_Medium_Bold_Gray);
        assertEquals(params.getButtonTextAppearance(), R.style.PaymentText_Small_Bold_Primary);        
    }
    
    @Test
    public void getTitleTextAppearance() {
        int value = 101;
        MessageParameters params = MessageParameters.createBuilder().
            setTitleTextAppearance(value).build();
        assertEquals(params.getTitleTextAppearance(), value);
    }

    @Test
    public void getMessageTextAppearance() {
        int value = 102;
        MessageParameters params = MessageParameters.createBuilder().
            setMessageTextAppearance(value).build();
        assertEquals(params.getMessageTextAppearance(), value);
    }

    @Test
    public void getMessageNoTitleTextAppearance() {
        int value = 103;
        MessageParameters params = MessageParameters.createBuilder().
            setMessageNoTitleTextAppearance(value).build();
        assertEquals(params.getMessageNoTitleTextAppearance(), value);
    }

    @Test
    public void setButtonTextAppearance() {
        int value = 103;
        MessageParameters params = MessageParameters.createBuilder().
            setButtonTextAppearance(value).build();
        assertEquals(params.getButtonTextAppearance(), value);
    }
}
