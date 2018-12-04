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

import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.Button;
import net.optile.payment.R;
import net.optile.payment.ui.theme.ButtonParameters;
import net.optile.payment.ui.theme.PaymentTheme;

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

    public void setButtonLabel(String label) {
        button.setText(label);
    }

    public void applyTheme(PaymentTheme theme) {
        super.applyTheme(theme);
        ButtonParameters params = theme.getButtonParameters();
        TextViewCompat.setTextAppearance(button, params.getLabelTextAppearance());
    }

    private void handleOnClick() {
        presenter.onActionClicked();
    }
}
