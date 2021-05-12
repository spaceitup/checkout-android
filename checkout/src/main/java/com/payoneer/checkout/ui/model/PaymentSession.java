/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payoneer.checkout.model.ListResult;
import com.payoneer.checkout.validation.Validator;

/**
 * Class for storing the ListResult and the payment sections. The following sections
 * are supported: preset accounts, saved accounts and payment networks.
 */
public final class PaymentSession {
    private final ListResult listResult;
    private final List<PaymentSection> paymentSections;
    private final Validator validator;

    /**
     * Construct a new PaymentSession object
     *
     * @param listResult Object holding the current list session data
     * @param paymentSections the list of sections containing PaymentCards
     * @param validator used to validate input values for this payment session
     */
    public PaymentSession(ListResult listResult, List<PaymentSection> paymentSections, Validator validator) {
        this.listResult = listResult;
        this.paymentSections = paymentSections;
        this.validator = validator;
    }

    public ListResult getListResult() {
        return listResult;
    }

    public List<PaymentSection> getPaymentSections() {
        return paymentSections;
    }

    public Validator getValidator() {
        return validator;
    }

    public URL getLink(String name) {
        Map<String, URL> links = listResult.getLinks();
        return links != null ? links.get(name) : null;
    }

    public String getListUrl() {
        URL url = getLink("self");
        return url != null ? url.toString() : null;
    }

    public boolean isListUrl(String listUrl) {
        URL url = getLink("self");
        return url != null && url.toString().equals(listUrl);
    }

    public boolean isEmpty() {
        return paymentSections.size() == 0;
    }

    public boolean containsOperationLink(URL url) {
        for (PaymentSection section : paymentSections) {
            if (section.containsLink("operation", url)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, URL> getLanguageLinks() {
        Map<String, URL> links = new HashMap<>();
        for (PaymentSection section : paymentSections) {
            section.putLanguageLinks(links);
        }
        return links;
    }
}
