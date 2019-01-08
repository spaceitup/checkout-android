/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.validation;

import java.util.HashMap;
import java.util.Map;

import net.optile.payment.util.PaymentUtils;

/**
 * Class with helper methods to validate Iban numbers
 */
final class IbanValidator {
    private static final Map<String, Integer> IBAN_ACCOUNT_POSITIONS = new HashMap<>();

    private static final long MAX = 999999999;
    private static final long MODULUS = 97;

    static {
        // Initializes account positions for IBAN numbers from different countries
        // Information based on: http://en.wikipedia.org/wiki/IBAN
        IBAN_ACCOUNT_POSITIONS.put("AD", 12);
        IBAN_ACCOUNT_POSITIONS.put("AE", 7);
        IBAN_ACCOUNT_POSITIONS.put("AL", 12);
        IBAN_ACCOUNT_POSITIONS.put("AT", 9);
        IBAN_ACCOUNT_POSITIONS.put("BA", 10);
        IBAN_ACCOUNT_POSITIONS.put("BE", 7);
        IBAN_ACCOUNT_POSITIONS.put("BG", 12);
        IBAN_ACCOUNT_POSITIONS.put("CH", 9);
        IBAN_ACCOUNT_POSITIONS.put("CY", 12);
        IBAN_ACCOUNT_POSITIONS.put("CZ", 14);
        IBAN_ACCOUNT_POSITIONS.put("DE", 12);
        IBAN_ACCOUNT_POSITIONS.put("DK", 8);
        IBAN_ACCOUNT_POSITIONS.put("DO", 8);
        IBAN_ACCOUNT_POSITIONS.put("EE", 8);
        IBAN_ACCOUNT_POSITIONS.put("ES", 14);
        IBAN_ACCOUNT_POSITIONS.put("FI", 10);
        IBAN_ACCOUNT_POSITIONS.put("FO", 8);
        IBAN_ACCOUNT_POSITIONS.put("FR", 14);
        IBAN_ACCOUNT_POSITIONS.put("GB", 14);
        IBAN_ACCOUNT_POSITIONS.put("GE", 6);
        IBAN_ACCOUNT_POSITIONS.put("GI", 8);
        IBAN_ACCOUNT_POSITIONS.put("GL", 8);
        IBAN_ACCOUNT_POSITIONS.put("GR", 11);
        IBAN_ACCOUNT_POSITIONS.put("HR", 11);
        IBAN_ACCOUNT_POSITIONS.put("HU", 12);
        IBAN_ACCOUNT_POSITIONS.put("IE", 14);
        IBAN_ACCOUNT_POSITIONS.put("IL", 10);
        IBAN_ACCOUNT_POSITIONS.put("IS", 10);
        IBAN_ACCOUNT_POSITIONS.put("IT", 15);
        IBAN_ACCOUNT_POSITIONS.put("KW", 8);
        IBAN_ACCOUNT_POSITIONS.put("KZ", 7);
        IBAN_ACCOUNT_POSITIONS.put("LB", 8);
        IBAN_ACCOUNT_POSITIONS.put("LI", 9);
        IBAN_ACCOUNT_POSITIONS.put("LT", 9);
        IBAN_ACCOUNT_POSITIONS.put("LU", 7);
        IBAN_ACCOUNT_POSITIONS.put("LV", 8);
        IBAN_ACCOUNT_POSITIONS.put("MC", 1);
        IBAN_ACCOUNT_POSITIONS.put("ME", 7);
        IBAN_ACCOUNT_POSITIONS.put("MK", 7);
        IBAN_ACCOUNT_POSITIONS.put("MR", 17);
        IBAN_ACCOUNT_POSITIONS.put("MT", 16);
        IBAN_ACCOUNT_POSITIONS.put("MU", 12);
        IBAN_ACCOUNT_POSITIONS.put("NL", 8);
        IBAN_ACCOUNT_POSITIONS.put("NO", 8);
        IBAN_ACCOUNT_POSITIONS.put("PL", 12);
        IBAN_ACCOUNT_POSITIONS.put("PT", 12);
        IBAN_ACCOUNT_POSITIONS.put("RO", 8);
        IBAN_ACCOUNT_POSITIONS.put("RS", 7);
        IBAN_ACCOUNT_POSITIONS.put("SA", 6);
        IBAN_ACCOUNT_POSITIONS.put("SE", 8);
        IBAN_ACCOUNT_POSITIONS.put("SI", 9);
        IBAN_ACCOUNT_POSITIONS.put("SK", 14);
        IBAN_ACCOUNT_POSITIONS.put("SM", 15);
        IBAN_ACCOUNT_POSITIONS.put("TN", 9);
        IBAN_ACCOUNT_POSITIONS.put("TR", 10);
    }

    /**
     * Returns the position of the account number in the given IBAN.
     *
     * @param iban the IBAN for which to return the position of the account
     * number.
     * @return The position of the account number in the IBAN code for the
     * country with the given <code>iban</code>.
     * @throws IllegalArgumentException if a blank or too short <code>iban</code> is given
     */
    public static Integer getAccountPosition(final String iban) {

        if (PaymentUtils.trimToEmpty(iban).length() < 2) {
            throw new IllegalArgumentException("Invalid IBAN: " + iban);
        }

        final String countryCode = iban.substring(0, 2).toUpperCase();
        if (IBAN_ACCOUNT_POSITIONS.containsKey(countryCode)) {
            return IBAN_ACCOUNT_POSITIONS.get(countryCode);
        } else {
            throw new IllegalArgumentException("Unknown IBAN country code: " + countryCode);
        }
    }

    /**
     * This rountine is based on the ISO 7064 Mod 97,10 check digit caluclation
     * routine.
     * <p/>
     * For further information see
     * <a href="http://en.wikipedia.org/wiki/International_Bank_Account_Number">
     * Wikipedia - IBAN number</a>.
     */
    public static boolean isValidIban(final String code) {
        if ((code == null) || (code.length() < 5)) {
            return false;
        }
        final String reformattedCode = code.substring(4) + code.substring(0, 4);

        long total = 0;
        for (int i = 0; i < reformattedCode.length(); i++) {
            final int charValue = Character.getNumericValue(reformattedCode.charAt(i));
            if (charValue < 0 || charValue > 35) {
                return false;
            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue;
            if (total > MAX) {
                total = (total % MODULUS);
            }
        }

        return ((int) (total % MODULUS) == 1);
    }
}
