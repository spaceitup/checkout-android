package net.optile.payment.list;

import android.support.test.InstrumentationRegistry;

import net.optile.payment.util.PaymentUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ListConfig {

    JSONObject source;

    public ListConfig(JSONObject source) {
        this.source = source;
    }

    public void setPrice (String price) throws JSONException {
        JSONObject payment = source.getJSONObject("payment");
        payment.put("amount", price);
    }

    public void setLanguage (String lang) throws JSONException{
        JSONObject language = source.getJSONObject("style");
        language.put("language", lang);
    }

    public String toJsonString() {
        return source.toString();

    }
}
