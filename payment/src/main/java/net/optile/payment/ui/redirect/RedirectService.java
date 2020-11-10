/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

import static net.optile.payment.model.HttpMethod.GET;
import static net.optile.payment.model.HttpMethod.POST;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import net.optile.payment.core.PaymentException;
import net.optile.payment.model.Interaction;
import net.optile.payment.model.OperationResult;
import net.optile.payment.model.Parameter;
import net.optile.payment.model.Redirect;

/**
 * RedirectService class to handle redirect payments, currently redirect networks are only supported
 * through ChromeCustomTabs.
 */
public final class RedirectService {

    public final static String INTERACTION_CODE = "interactionCode";
    public final static String INTERACTION_REASON = "interactionReason";

    /**
     * Check if the request is supported and can be handled by this redirect service
     *
     * @param context The context in which this tabs is used
     * @param request containing the redirect data
     * @return true if the request can be handled, false otherwise
     */
    public static boolean supports(Context context, RedirectRequest request) {
        String method = request.getRedirectMethod();
        return (POST.equals(method) || GET.equals(method)) && ChromeCustomTabs.isSupported(context);
    }

    /**
     * Redirect to the location that is provided in the request
     *
     * @param context in which the redirect should be started
     * @param request containing the type and location of the redirect
     */
    public static void redirect(Context context, RedirectRequest request) throws PaymentException {
        if (!supports(context, request)) {
            throw new PaymentException("The redirect request can not be handled by this service");
        }
        PaymentRedirectActivity.clearResultUri();
        Uri uri = null;
        if (POST.equals(request.getRedirectMethod())) {
            uri = RedirectUriBuilder.fromURL(request.getLink());
        } else {
            uri = RedirectUriBuilder.fromRedirect(request.getRedirect());
        }
        ChromeCustomTabs.open(context, uri);
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
        String val = resultUri.getQueryParameter(INTERACTION_CODE);
        if (TextUtils.isEmpty(val)) {
            return null;
        }
        interaction.setCode(val);
        val = resultUri.getQueryParameter(INTERACTION_REASON);
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
