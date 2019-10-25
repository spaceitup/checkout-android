/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.mode;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Class for filtering alpha numeric inputs
 */
public final class AlphaNumericInputFilter implements InputFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // Only keep characters that are alphanumeric
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) {
                builder.append(c);
            }
        }

        // If all characters are valid, return null, otherwise only return the filtered characters
        boolean allCharactersValid = (builder.length() == end - start);
        return allCharactersValid ? null : builder.toString();
    }
}
