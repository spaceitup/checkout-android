/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.test.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class with utility functions for unit tests
 */
public final class TestUtils {

    /**
     * Create a test URL with the provided url
     *
     * @return the newly created default URL
     * @throws IllegalStateException when the url could not be created
     */
    public final static URL createDefaultURL() {
        return createTestURL("http://localhost");
    }

    /**
     * Create a test URL with the provided url
     *
     * @param url to be used to create the URL Object
     * @return the newly created URL
     * @throws IllegalStateException when the url could not be created
     */
    public final static URL createTestURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
