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

public class PageParametersTest {

    @Test
    public void createBuilder() {
        PageParameters.Builder builder = PageParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        PageParameters params = PageParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getThemeResId(), R.style.PaymentTheme_PaymentPage);
        assertEquals(params.getEmptyTextAppearance(), R.style.PaymentText_Medium_Gray);
    }
    
    @Test
    public void getThemeResId() {
        int value = 101;
        PageParameters params = PageParameters.createBuilder().
            setThemeResId(value).build();
        assertEquals(params.getThemeResId(), value);
    }

    @Test
    public void getEmptyTextAppearance() {
        int value = 102;
        PageParameters params = PageParameters.createBuilder().
            setEmptyTextAppearance(value).build();
        assertEquals(params.getEmptyTextAppearance(), value);
    }
}
