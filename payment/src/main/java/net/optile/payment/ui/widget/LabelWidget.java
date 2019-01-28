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

import android.view.View;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.util.PaymentUtils;

/**
 * Widget for showing an info label in the PaymentCard
 */
public class LabelWidget extends FormWidget {

    private final TextView label;

    /**
     * Construct a new LabelWidget
     *
     * @param name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to apply
     */
    public LabelWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        label = rootView.findViewById(R.id.text_label);

        WidgetParameters params = theme.getWidgetParameters();
        PaymentUtils.setTextAppearance(label, params.getInfoLabelStyle());
    }

    public void setLabel(String text) {
        label.setText(text);
    }
}
