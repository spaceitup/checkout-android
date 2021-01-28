/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.localization.Localization;

import android.view.View;
import android.widget.Button;

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
     */
    public ButtonWidget(String name, View rootView) {
        super(name, rootView);
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
