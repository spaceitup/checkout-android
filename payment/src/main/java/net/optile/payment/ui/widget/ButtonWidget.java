/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import android.view.View;
import android.widget.Button;
import net.optile.payment.R;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.util.PaymentUtils;

/**
 * Widget for showing the Submit (pay) button
 */
public final class ButtonWidget extends FormWidget {

    private final Button button;

    /**
     * Construct a new ButtonWidget
     *
     * @param name the name of this widget
     * @param rootView the root view of this button
     * @param theme PaymentTheme used for this widget
     */
    public ButtonWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        button = rootView.findViewById(R.id.button);
        WidgetParameters params = theme.getWidgetParameters();
        PaymentUtils.setTextAppearance(button, params.getButtonLabelStyle());

        int resId = params.getButtonBackground();
        if (resId != 0) {
            button.setBackgroundResource(resId);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnClick();
            }
        });
    }

    public void setLabel(String label) {
        button.setText(label);
    }

    private void handleOnClick() {
        presenter.onActionClicked();
    }
}
