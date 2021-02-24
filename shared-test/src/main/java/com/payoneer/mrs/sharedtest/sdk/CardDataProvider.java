/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.sharedtest.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class for creating test data
 */
public final class CardDataProvider {

    public static Map<String, String> visaCardData() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("number", "4111111111111111");
        values.put("expiryDate", "1245");
        values.put("verificationCode", "123");
        values.put("holderName", "Thomas Smith");
        return values;
    }
}
