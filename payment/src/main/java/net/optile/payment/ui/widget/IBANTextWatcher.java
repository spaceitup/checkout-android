package net.optile.payment.ui.widget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class IBANTextWatcher implements TextWatcher {

    // means divider position is every 5th symbol
    private static final int DIVIDER_MODULO = 5;
    private static final int GROUP_SIZE = DIVIDER_MODULO - 1;
    private static final char DIVIDER = ' ';
    private static final String STRING_DIVIDER = " ";
    private String previousText = "";

    private int deleteLength;
    private int insertLength;
    private int start;
    private final EditText editText;

    private String regexIBAN = "(\\w{" + GROUP_SIZE + "}" + DIVIDER + ")*\\w{1," + GROUP_SIZE + "}";
    private Pattern patternIBAN = Pattern.compile(regexIBAN);

    public IBANTextWatcher(EditText editText) {
        this.editText = editText;
    }
    
    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        this.previousText = s.toString();
        this.deleteLength = count;
        this.insertLength = after;
        this.start = start;
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        String originalString = s.toString();

        if (!previousText.equals(originalString) &&
                !isInputCorrect(originalString)) {
            String newString = previousText.substring(0, start);
            int cursor = start;

            if (deleteLength > 0 && s.length() > 0 &&
                    (previousText.charAt(start) == DIVIDER ||
                            start == s.length())) {
                newString = previousText.substring(0, start - 1);
                --cursor;
            }

            if (insertLength > 0) {
                newString += originalString.substring(start, start + insertLength);
                newString = buildCorrectInput(newString);
                cursor = newString.length();
            }

            newString += previousText.substring(start + deleteLength);
            s.replace(0, s.length(), buildCorrectInput(newString));

            editText.setSelection(cursor);
        }
    }

    /**
     * Check if String has the white spaces in the correct positions, meaning
     * if we have the String "123456789" and there should exist a white space
     * every 4 characters then the correct String should be "1234 5678 9".
     *
     * @param s String to be evaluated
     * @return true if string s is written correctly
     */
    private boolean isInputCorrect(String s) {
        Matcher matcherDot = patternIBAN.matcher(s);
        return matcherDot.matches();
    }

    /**
     * Puts the white spaces in the correct positions,
     * see the example in {@link IBANTextWatcher#isInputCorrect(String)}
     * to understand the correct positions.
     *
     * @param s String to be corrected.
     * @return String corrected.
     */
    private String buildCorrectInput(String s) {
        StringBuilder sbs = new StringBuilder(
                s.replaceAll(STRING_DIVIDER, ""));

        // Insert the divider in the correct positions
        for (int i = GROUP_SIZE; i < sbs.length(); i += DIVIDER_MODULO) {
            sbs.insert(i, DIVIDER);
        }

        return sbs.toString();
    }
}
