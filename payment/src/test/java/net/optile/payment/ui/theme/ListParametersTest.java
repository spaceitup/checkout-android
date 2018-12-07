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

public class ListParametersTest {

    @Test
    public void createBuilder() {
        ListParameters.Builder builder = ListParameters.createBuilder();
        assertNotNull(builder);
    }

    @Test
    public void createDefault() {
        ListParameters params = ListParameters.createDefault();
        assertNotNull(params);
        assertEquals(params.getHeaderTextAppearance(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getAccountTitleTextAppearance(), R.style.PaymentText_Medium_Bold);
        assertEquals(params.getAccountSubtitleTextAppearance(), R.style.PaymentText_Tiny);
        assertEquals(params.getNetworkTitleTextAppearance(), R.style.PaymentText_Medium);
        assertEquals(params.getLogoBackgroundResId(), 0);                        
    }
    
    @Test
    public void getHeaderTextAppearance() {
        int value = 101;
        ListParameters params = ListParameters.createBuilder().
            setHeaderTextAppearance(value).build();
        assertEquals(params.getHeaderTextAppearance(), value);
    }

    @Test
    public void getAccountTitleTextAppearance() {
        int value = 102;
        ListParameters params = ListParameters.createBuilder().
            setAccountTitleTextAppearance(value).build();
        assertEquals(params.getAccountTitleTextAppearance(), value);
    }

    @Test
    public void getAccountSubtitleTextAppearance() {
        int value = 103;
        ListParameters params = ListParameters.createBuilder().
            setAccountSubtitleTextAppearance(value).build();
        assertEquals(params.getAccountSubtitleTextAppearance(), value);
    }

    @Test
    public void getNetworkTitleTextAppearance() {
        int value = 104;
        ListParameters params = ListParameters.createBuilder().
            setNetworkTitleTextAppearance(value).build();
        assertEquals(params.getNetworkTitleTextAppearance(), value);
    }

    @Test
    public void getLogoBackgroundResId() {
        int value = 105;
        ListParameters params = ListParameters.createBuilder().
            setLogoBackgroundResId(value).build();
        assertEquals(params.getLogoBackgroundResId(), value);
    }
}
