package net.optile.payment.ui.widget.input;

import static net.optile.payment.ui.widget.input.ExpiryDateInputMode.DIVIDER;
import static net.optile.payment.ui.widget.input.ExpiryDateInputMode.MAXLENGTH;

import android.text.Editable;
import android.widget.EditText;

public class ExpiryDateTextWatcher extends EditTextWatcher {

    /** The size of the month field which is always 2 (e.g. 01, 12) */
    private final static int MONTHLENGTH = 2;

    public ExpiryDateTextWatcher(final EditText editText) {
        super(editText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Editable s) {
        String afterNormalized = normalize(s.toString());
        if (afterNormalized.length() == 0) {
            updateEditable(s, afterNormalized);
            return;
        }
        String beforeNormalized = normalize(beforeText);
        int beforeLength = beforeNormalized.length();
        int cursor = -1;

        StringBuilder sb = new StringBuilder(afterNormalized);
        formatExpiryDate(sb);
        int afterLength = sb.length();

        // text has been deleted by the user
        if (beforeCount > beforeAfter) {
            // Special case to keep divider when only the month is left after deleting the year
            if (beforeStart > MONTHLENGTH && afterLength == MONTHLENGTH && beforeLength > MONTHLENGTH) {
                sb.insert(MONTHLENGTH, DIVIDER);
            } else if (afterLength > 2) {
                sb.insert(MONTHLENGTH, DIVIDER);
            }
        }
        // text has been added by the user
        else if (afterLength >= MONTHLENGTH) {
            sb.insert(MONTHLENGTH, DIVIDER);
            cursor = sb.length();
        }
        updateEditable(s, sb.toString());
        setCursor(cursor);
    }

    private void updateEditable(final Editable editable, final String value) {
        String newValue = value.substring(0, Math.min(MAXLENGTH, value.length()));
        editable.replace(0, editable.length(), newValue);
    }

    private void formatExpiryDate(final StringBuilder sb) {
        char c = sb.charAt(0);
        if (c == '0') {
            // Cannot recover from 2 zeros, replace all with one zero
            if (sb.length() > 1 && sb.charAt(1) == '0') {
                sb.replace(0, sb.length(), "0");
            }
            return;
        }
        if (c == '1') {
            if (sb.length() == 1 || sb.charAt(1) <= '2') {
                return;
            }
        }
        sb.insert(0, "0");
    }

    private String normalize(final String value) {
        return value.replaceAll("[\\s,/]", "");
    }
}
