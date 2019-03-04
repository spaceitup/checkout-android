package net.optile.payment.tests;
//package net.optile.payment.network;


import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import net.optile.example.checkout.CheckoutActivity;
import net.optile.example.checkout.test.R;
import net.optile.payment.network.ListUrlGen;
import net.optile.payment.util.PaymentUtils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileReader;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class PaymentPageTests {

    String uri = "https://api.sandbox.oscato.com";
    String auth = "Basic U1BBQ0VJVFVQOmFxYTZmZGpyMDgyYzBxbzJmODFndG1xdXU4YW81ZnBiZGpxbjhoam8=";






    @Rule
    public ActivityTestRule<CheckoutActivity> mActivityRule = new ActivityTestRule<>(
            CheckoutActivity.class);

    @Test
    public void checkPaymentPage() throws IOException {
        String jsonBody = loadJsonBody();
        Log.i("unittest", "jsonBody: " + jsonBody);
        ListUrlGen list = new ListUrlGen();
        String listUrl = list.getListUrl(uri,auth,jsonBody);
    }

    private String loadJsonBody() throws IOException {
        String json = PaymentUtils.readRawResource(InstrumentationRegistry.getContext().getResources(), R.raw.preset);
        Log.i("pay", "in: " + json);


    }

}

