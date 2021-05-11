/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;

import com.payoneer.checkout.localization.Localization;

/**
 * Base class for payment list sections
 */
public abstract class ListSection {

    private final String labelKey;

    /**
     * Construct a new ListSection with the label localization key
     *
     * @param labelKey localization key for the list section header label
     */
    public ListSection(String labelKey) {
        this.labelKey = labelKey;
    }

    /**
     * Get the localized header label for this list section
     *
     * @return localized header label for this list section
     */
    public String getLabel() {
        return Localization.translate(labelKey);
    }

    /**
     * Check if this section contains a link with the provided name
     *
     * @param name of the link
     * @param url that should match
     * @return true when it contains the link, false otherwise
     */
    public abstract boolean containsLink(String name, URL url);
}
