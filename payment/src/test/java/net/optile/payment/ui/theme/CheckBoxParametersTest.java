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

public class CheckBoxParametersTest {

    @Test
    public void createBuilder() {
        CheckBoxParameters.Builder builder = CheckBoxParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        CheckBoxParameters params = CheckBoxParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getThemeResId(), R.style.PaymentThemeCheckBox);
        assertEquals(params.getCheckedTextAppearance(), R.style.PaymentText_Medium);
        assertEquals(params.getUncheckedTextAppearance(), R.style.PaymentText_Medium_Hint);        
    }
    
    @Test
    public void getThemeResId() {
        int value = 101;
        CheckBoxParameters params = CheckBoxParameters.createBuilder().
            setThemeResId(value).build();
        assertEquals(params.getThemeResId(), value);
    }

    @Test
    public void getCheckedTextAppearance() {
        int value = 102;
        CheckBoxParameters params = CheckBoxParameters.createBuilder().
            setCheckedTextAppearance(value).build();
        assertEquals(params.getCheckedTextAppearance(), value);
    }

    @Test
    public void getUncheckedTextAppearance() {
        int value = 103;
        CheckBoxParameters params = CheckBoxParameters.createBuilder().
            setUncheckedTextAppearance(value).build();
        assertEquals(params.getUncheckedTextAppearance(), value);
    }
}
