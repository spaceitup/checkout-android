/*
 * Copyright(c) 2012-2020 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.core;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;

@RunWith(RobolectricTestRunner.class)
public class PaymentErrorTest {

    @Test
    public void writeToParcel() {
        PaymentError writeError = new PaymentError("foo", new Exception("cause"));

        Parcel parcel = Parcel.obtain();
        writeError.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);
        PaymentError readError = PaymentError.CREATOR.createFromParcel(parcel);

        assertEquals(readError.toString(), writeError.toString());
    }
}
