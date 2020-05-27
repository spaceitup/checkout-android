package net.optile.payment.ui.widget.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.widget.EditText;

/**
 * Class for separating 4 digits with a space
 */
public class GroupingTextWatcher extends EditTextWatcher {

    private static final char DIVIDER = ' ';
    private static final String STRING_DIVIDER = " ";

    private final int groupSize;
    private final int dividerModulo;
    private final String inputRegex;
    private final Pattern inputPattern;

    /**
     * Construct a new GroupingTextWatcher for the given editText
     *
     * @param groupSize the size of grouped characters
     * @param editText for which this class is manipulating the input
     */
    public GroupingTextWatcher(int groupSize, EditText editText) {
        super(editText);

        if (groupSize <= 0) {
            throw new IllegalArgumentException("groupSize must be equal or greater than 1");
        }
        this.groupSize = groupSize;
        this.dividerModulo = groupSize + 1;
        this.inputRegex = "(\\w{" + groupSize + "}" + DIVIDER + ")*\\w{1," + groupSize + "}";
        this.inputPattern = Pattern.compile(inputRegex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Editable s) {
        String afterText = s.toString();

        if (!previousText.equals(afterText) && !isInputCorrect(afterText)) {
            String newString = previousText.substring(0, start);
            int cursor = start;

            if (deleteLength > 0 && s.length() > 0 && (previousText.charAt(start) == DIVIDER || start == s.length())) {
                newString = previousText.substring(0, start - 1);
                --cursor;
            }

            if (insertLength > 0) {
                newString += afterText.substring(start, start + insertLength);
                newString = buildCorrectInput(newString);
                cursor = newString.length();
            }
            newString += previousText.substring(start + deleteLength);
            s.replace(0, s.length(), buildCorrectInput(newString));

            setCursor(cursor);
        }
    }

    /**
     * Check if String has the white spaces in the correct positions, meaning
     * if we have the String "123456789" and there should exist a white space
     * every 4 characters then the correct String should be "1234 5678 9".
     *
     * @param input String to be evaluated
     * @return true if string input is written correctly
     */
    private boolean isInputCorrect(final String input) {
        Matcher matcherDot = inputPattern.matcher(input);
        return matcherDot.matches();
    }

    /**
     * Puts the white spaces in the correct positions,
     * see the example in {@link GroupingTextWatcher#isInputCorrect(String)}
     * to understand the correct positions.
     *
     * @param input String to be corrected.
     * @return String corrected.
     */
    private String buildCorrectInput(final String input) {
        StringBuilder sbs = new StringBuilder(
            input.replaceAll(STRING_DIVIDER, ""));

        // Insert the divider in the correct positions
        for (int i = groupSize; i < sbs.length(); i += dividerModulo) {
            sbs.insert(i, DIVIDER);
        }
        return sbs.toString();
    }
}
