package net.optile.payment.network;

import android.util.Log;

import net.optile.payment.core.PaymentException;
import net.optile.payment.model.ListResult;

import java.net.URL;
import java.util.Map;

public class ListUrlGen {

    public String getListUrl(String url, String authorization, String listData) throws Exception {
        ListConnection conn = new ListConnection();
        try {
            ListResult result = conn.createPaymentSession(url, authorization, listData);
            URL selfUrl = getSelfUrl(result.getLinks());
            if (selfUrl == null) {
                throw new Exception("Error creating payment session, missing self url");
            }
            return selfUrl.toString();
        } catch (PaymentException e) {
            throw new Exception("Error creating payment session", e);
        }
    }


    private URL getSelfUrl(Map<String, URL> links) {
        return links != null ? links.get("self") : null;
    }
}