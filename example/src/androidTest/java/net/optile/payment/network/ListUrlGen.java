package net.optile.payment.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import net.optile.example.checkout.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;
import net.optile.payment.util.PaymentUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ListUrlGen {
    public String createNewListUrl() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.payment_url);
        String auth = context.getString(R.string.payment_authorization);
        String jsonBody = loadJsonBody();
        return getListUrl(url, auth, jsonBody);
    }

    private String getListUrl(String url, String authorization, String listData) throws IOException {
        ListConnection conn = new ListConnection();
        try {
            ListResult result = conn.createPaymentSession(url, authorization, listData);
            URL selfUrl = getSelfUrl(result.getLinks());
            if (selfUrl == null) {
                throw new IOException("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            throw new IOException("Error creating payment session", e);
        }
    }

    private URL getSelfUrl(Map<String, URL> links) {
        return links != null ? links.get("self") : null;
    }

    private String loadJsonBody() throws IOException {
        String json = PaymentUtils.readRawResource(InstrumentationRegistry.getContext().getResources(),
                net.optile.example.checkout.test.R.raw.preset);
        return json;
    }
}