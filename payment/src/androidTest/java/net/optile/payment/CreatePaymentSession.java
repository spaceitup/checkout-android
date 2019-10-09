package net.optile.payment;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;


/**
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
public class CreatePaymentSession {

    private String createListUrl(boolean presetFirst) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);
        ListService service = ListService.createInstance(url, auth);
        ListConfig config = service.createListConfig(net.optile.example.demo.test.R.raw.listtemplate);
        config.setPresetFirst(presetFirst);
        return service.createListUrl(config);
    }

}
