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

package net.optile.payment.ui.widget;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.ImageView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.InputElement;
import android.text.InputType;

/**
 * Class for handling text input
 */
public abstract class TextInputWidget extends FormWidget {

    final InputElement element;

    final TextInputEditText input;

    final TextInputLayout layout;

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public TextInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView);
        this.element = element;
        layout = rootView.findViewById(R.id.layout_value);
        input = rootView.findViewById(R.id.input_value);

        layout.setHintAnimationEnabled(false);
        layout.setHint(element.getLabel());
        layout.setHintAnimationEnabled(true);
    }

    public boolean supportsValidation() {
        return true; 
    }

    public void setValidation(int state, String message) {
        super.setValidation(state, message);
        switch (state) {
        case VALIDATION_ERROR:
            layout.setErrorEnabled(true);
            layout.setError(message);
            break;
        default:
            layout.setErrorEnabled(false);
            layout.setError(null);
        }
    }
    
    public void putValue(Charge charge) throws PaymentException {
        String val = input.getText().toString().trim();
        if (!TextUtils.isEmpty(val)) {
            charge.putValue(element.getName(), val);
        }
    }

    public boolean setLastImeOptionsWidget() {
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    void setInputType(int type) {
        input.setInputType(type);
    }
}
