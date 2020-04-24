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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import net.optile.payment.model.ErrorInfo;

@RunWith(RobolectricTestRunner.class)
public class PaymentErrorTest {

    @Test
    public void construct_withMessage() {
        PaymentError error = new PaymentError("errorMessage");
        assertEquals("errorMessage", error.getMessage());
    }

    @Test
    public void construct_withStatusCode() {
        PaymentError error = new PaymentError(500, "errorMessage");
        assertEquals(500, error.getStatusCode());
        assertEquals("errorMessage", error.getMessage());
    }

    @Test
    public void construct_withErrorInfo() {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setResultInfo("resultInfo");
        PaymentError error = new PaymentError(404, errorInfo);
        assertEquals(errorInfo.getResultInfo(), error.getMessage());
        assertEquals(404, error.getStatusCode());
        assertEquals(errorInfo, error.getErrorInfo());
    }

    @Test
    public void construct_withCause() {
        Throwable cause = new Exception("cause of error");
        PaymentError error = new PaymentError(cause);
        assertEquals(cause.getMessage(), error.getMessage());
        assertEquals(cause, error.getCause());
    }

    @Test
    public void construct_withMessageAndCause() {
        Throwable cause = new Exception("cause of error");
        PaymentError error = new PaymentError("foo", cause);
        assertEquals("foo", error.getMessage());
        assertEquals(cause, error.getCause());
    }

    @Test
    public void construct_withNetworkFailure() {
        Throwable cause = new Exception("cause of error");
        PaymentError error = new PaymentError(cause, true);
        assertEquals(cause.getMessage(), error.getMessage());
        assertEquals(cause, error.getCause());
        assertTrue(error.isNetworkFailure());
    }

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
