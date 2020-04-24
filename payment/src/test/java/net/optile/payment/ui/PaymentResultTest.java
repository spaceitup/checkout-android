package net.optile.payment.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import net.optile.payment.core.PaymentError;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.InteractionReason;
import net.optile.payment.model.OperationResult;

@RunWith(RobolectricTestRunner.class)
public class PaymentResultTest {

    @Test
    public void construct_withResultInfo() {
        PaymentResult result = new PaymentResult("resultInfo");
        assertEquals("resultInfo", result.getResultInfo());
    }

    @Test
    public void construct_withPaymentError() {
        Interaction interaction = new Interaction(InteractionCode.ABORT, InteractionReason.CLIENTSIDE_ERROR);
        PaymentError error = new PaymentError("paymentError");
        PaymentResult result = new PaymentResult(interaction, error);
        assertEquals(interaction, result.getInteraction());
        assertEquals(error, result.getPaymentError());
    }

    @Test
    public void construct_withOperationResult() {
        OperationResult operationResult = new OperationResult();
        PaymentResult result = new PaymentResult(operationResult);
        assertEquals(operationResult, result.getOperationResult());
    }

    @Test
    public void construct_withInteraction() {
        Interaction interaction = new Interaction(InteractionCode.ABORT, InteractionReason.CLIENTSIDE_ERROR);
        PaymentResult result = new PaymentResult("resultInfo", interaction);
        assertEquals("resultInfo", result.getResultInfo());
        assertEquals(interaction, result.getInteraction());
    }

    @Test
    public void writeToParcel() {
        Interaction interaction = new Interaction(InteractionCode.ABORT, InteractionReason.CLIENTSIDE_ERROR);
        PaymentError error = new PaymentError("error");
        PaymentResult writeResult = new PaymentResult(interaction, error);

        Parcel parcel = Parcel.obtain();
        writeResult.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);
        PaymentResult readResult = PaymentResult.CREATOR.createFromParcel(parcel);
        assertEquals(readResult.toString(), writeResult.toString());
    }
}
