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
import android.widget.Button;
import net.optile.payment.R;

/**
 * Class for showing the submit button
 */
public final class ButtonWidget extends FormWidget {

    private final Button button;

    /**
     * Construct a new ButtonWidget
     *
     * @param name name
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

    private void handleOnClick() {
        presenter.onActionClicked();
    }
}
