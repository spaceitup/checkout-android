/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget.input;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Class for filtering alpha numeric inputs
 */
public final class AlphaNumericInputFilter implements InputFilter {

    private final boolean allowSpace;

    public AlphaNumericInputFilter(boolean allowSpace) {
        this.allowSpace = allowSpace;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // Only keep characters that are alphanumeric
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (Character.isLetterOrDigit(c) || (allowSpace && Character.isSpaceChar(c))) {
                builder.append(c);
            }
        }

        // If all characters are valid, return null, otherwise only return the filtered characters
        boolean allCharactersValid = (builder.length() == end - start);
        return allCharactersValid ? null : builder.toString();
    }
}
