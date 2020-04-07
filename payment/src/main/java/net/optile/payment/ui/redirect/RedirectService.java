/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.HttpMethod;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Parameter;
import net.optile.payment.model.Redirect;

/**
 * RedirectService class to handle redirect payments, currently redirect networks are only supported
 * through ChromeCustomTabs.
 */
public final class RedirectService {

    private final static String KEY_INTERACTION_CODE = "interactionCode";
    private final static String KEY_INTERACTION_REASON = "interactionReason";

    /**
     * Check if payment redirects are supported for this device.
     *
     * @param context The context in which this tabs is used
     * @param redirect containing the address to which to redirect to
     * @return true if payment redirects are supported, false otherwise
     */
    public static boolean isSupported(Context context, Redirect redirect) {
        return ChromeCustomTabs.isSupported(context) && HttpMethod.GET.equals(redirect.getMethod());
    }

    /**
     * Open the Redirect for the given Context.
     *
     * @param context in which the redirect should be started
     * @param redirect containing the address to which to redirect to
     */
    public static void open(Context context, Redirect redirect) throws PaymentException {
        if (!isSupported(context, redirect)) {
            throw new PaymentException("Redirect payment is not supported by the Android-SDK");
        }
        PaymentRedirectActivity.clearResultUri();
        ChromeCustomTabs.open(context, RedirectUriBuilder.createUri(redirect));
    }

    /**
     * Get the Redirect result if any has been received through i.e.
     * a deep link callback.
     *
     * @return the redirect OperationResult or null if not available
     */
    public static OperationResult getRedirectResult() {
        Uri resultUri = PaymentRedirectActivity.getResultUri();
        if (resultUri == null) {
            return null;
        }
        // Create the interaction object
        Interaction interaction = new Interaction();
        String val = resultUri.getQueryParameter(KEY_INTERACTION_CODE);
        if (TextUtils.isEmpty(val)) {
            return null;
        }
        interaction.setCode(val);
        val = resultUri.getQueryParameter(KEY_INTERACTION_REASON);
        if (TextUtils.isEmpty(val)) {
            return null;
        }
        interaction.setReason(val);

        // Create the Redirect object
        Redirect redirect = new Redirect();
        List<Parameter> params = new ArrayList<>();
        for (String key : resultUri.getQueryParameterNames()) {
            Parameter param = new Parameter();
            param.setName(key);
            param.setValue(resultUri.getQueryParameter(key));
            params.add(param);
        }
        redirect.setParameters(params);

        OperationResult result = new OperationResult();
        result.setResultInfo("OperationResult received from the mobile-redirect webapp");
        result.setRedirect(redirect);
        result.setInteraction(interaction);
        return result;
    }
}
