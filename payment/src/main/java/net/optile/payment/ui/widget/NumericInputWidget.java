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

import android.text.InputType;
import android.view.View;
import net.optile.payment.model.InputElement;

/**
 * Class for handling the Numeric input type
 */
public final class NumericInputWidget extends TextInputWidget {

    /**
     * Construct a new NumericInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     * @param element the InputElement this widget is displaying
     */
    public NumericInputWidget(String name, View rootView, InputElement element) {
        super(name, rootView, element);
        setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}
