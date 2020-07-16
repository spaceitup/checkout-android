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
import net.optile.payment.localization.Localization;
import net.optile.payment.ui.PaymentTheme;

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnClick();
            }
        });
    }

    public void onBind(String code, String buttonKey) {
        String label = Localization.translate(code, buttonKey);
        button.setText(label);
    }

    private void handleOnClick() {
        presenter.onActionClicked();
    }
}
