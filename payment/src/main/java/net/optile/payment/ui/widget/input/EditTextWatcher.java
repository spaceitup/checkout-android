/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.input;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Base class for EditText watchers
 */
public class EditTextWatcher implements TextWatcher {

    final EditText editText;
    String beforeText;
    int beforeStart;
    int beforeCount;
    int beforeAfter;
    boolean internalLock;
    
    public EditTextWatcher(EditText editText) {
        this.editText = editText;
        editText.addTextChangedListener(this);
    }

    /** 
     * Reset this EditTextWatcher
     */
    public void reset() {
        editText.removeTextChangedListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        this.beforeText = s.toString();
        this.beforeStart = start;
        this.beforeCount = count;
        this.beforeAfter = after;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterTextChanged(final Editable editable) {
        if (internalLock) {
            return;
        }
        internalLock = true;
        validate(editable);
        internalLock = false;
    }

    /** 
     * Validate and change the input if required by the custom TextWatcher
     * 
     * @param s editable to be changed
     */
    public void validate(Editable s) {
    }

    /** 
     * Set the cursor in the edit text
     * 
     * @param cursor new cursor position to be set 
     */
    public void setCursor(int cursor) {
        if (cursor >= 0 && cursor <= editText.length()) {
            editText.setSelection(cursor);
        }
    }
}
