/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabel(String text) {
        label.setText(text);
    }
}
