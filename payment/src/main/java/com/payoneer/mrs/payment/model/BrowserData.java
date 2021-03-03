/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Customer web browser data.
 */
@Getter
@Setter
public class BrowserData {

    /**
     * Java enabled flag. The ability of the cardholder browser to execute Java.
     * Value is returned from the navigator.javaEnabled property.
     */
    private Boolean javaEnabled;
    /**
     * This value represents the browser language as defined in IETF BCP47. The value is limited to 1-8 characters.
     * Value is returned from navigator.language property.
     */
    private String language;
    /**
     * Color depth. The value represents the bit depth of the color palette for displaying images, in bits per pixel.
     * Obtained from cardholder browser using the screen.colorDepth property.
     */
    private Integer colorDepth;
    /** Timezone */
    private String timezone;
    /** Browser screen height. Total height of the cardholder's screen in pixels. */
    private Integer browserScreenHeight;
    /** Browser screen width. Total width of the cardholder's screen in pixels. */
    private Integer browserScreenWidth;
}
