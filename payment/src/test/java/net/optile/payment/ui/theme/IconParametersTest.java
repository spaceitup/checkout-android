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

import net.optile.payment.core.PaymentInputType;
import static org.junit.Assert.*;
import net.optile.payment.R;
import org.junit.Test;

public class IconParametersTest {

    @Test
    public void createBuilder() {
        IconParameters.Builder builder = IconParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        IconParameters params = IconParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getOkColorResId(), R.color.pmvalidation_ok);
        assertEquals(params.getUnknownColorResId(), R.color.pmvalidation_unknown);
        assertEquals(params.getErrorColorResId(), R.color.pmvalidation_error);
        
        assertEquals(params.getInputTypeIcon(PaymentInputType.HOLDER_NAME), R.drawable.ic_name);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_DATE), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_MONTH), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.EXPIRY_YEAR), R.drawable.ic_date);
        assertEquals(params.getInputTypeIcon(PaymentInputType.BANK_CODE), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.ACCOUNT_NUMBER), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.IBAN), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.BIC), R.drawable.ic_card);
        assertEquals(params.getInputTypeIcon(PaymentInputType.VERIFICATION_CODE), R.drawable.ic_lock);
        assertEquals(params.getInputTypeIcon("default"), R.drawable.ic_default);                        
    }
    
    @Test
    public void getOkColorResId() {
        int value = 101;
        IconParameters params = IconParameters.createBuilder().
            setOkColorResId(value).build();
        assertEquals(params.getOkColorResId(), value);
    }

    @Test
    public void getUnknownColorResId() {
        int value = 102;
        IconParameters params = IconParameters.createBuilder().
            setUnknownColorResId(value).build();
        assertEquals(params.getUnknownColorResId(), value);
    }

    @Test
    public void getErrorColorResId() {
        int value = 103;
        IconParameters params = IconParameters.createBuilder().
            setErrorColorResId(value).build();
        assertEquals(params.getErrorColorResId(), value);
    }

    @Test
    public void getInputTypeIcon() {
        int value = 104;
        String name = "test";
        IconParameters params = IconParameters.createBuilder().
            putInputTypeIcon(name, value).build();
        assertEquals(params.getInputTypeIcon(name), value);
    }
}
