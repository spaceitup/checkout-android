package net.optile.payment.ui;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Bundle;
import android.os.Parcel;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.InteractionCode;
import net.optile.payment.model.InteractionReason;
import net.optile.payment.core.InternalError;

@RunWith(RobolectricTestRunner.class)
public class PaymentResultTest {

    @Test
    public void writeToParcel() {
        Interaction interaction = new Interaction(InteractionCode.ABORT, InteractionReason.CLIENTSIDE_ERROR);
        InternalError error = new InternalError("error");
        PaymentResult writeResult = new PaymentResult(interaction, error);

        Parcel parcel = Parcel.obtain();
        writeResult.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);
        PaymentResult readResult = PaymentResult.CREATOR.createFromParcel(parcel);
        assertEquals(readResult.toString(), writeResult.toString());
    }
}
