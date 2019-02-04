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

package net.optile.payment.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.util.PaymentUtils;

/**
 * Themed Dialog Fragment with two optional buttons, neutral and positive.
 */
public abstract class ThemedDialogFragment extends DialogFragment {

    public final static int BUTTON_NEUTRAL = 0x01;
    public final static int BUTTON_POSITIVE = 0x02;

    private String neutralLabel;
    private String positiveLabel;
    private ThemedDialogListener listener;
    private boolean closedByButton;

    public void setNeutralButton(String neutralLabel) {
        this.neutralLabel = neutralLabel;
    }

    public void setPositiveButton(String positiveLabel) {
        this.positiveLabel = positiveLabel;
    }

    public void setListener(ThemedDialogListener listener) {
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (!(listener == null || closedByButton)) {
            listener.onDismissed(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTheme() {
        DialogParameters params = PaymentUI.getInstance().getPaymentTheme().getDialogParameters();
        int theme = params.getDialogTheme();
        return theme == 0 ? super.getTheme() : theme;
    }

    void initButtons(View rootView, DialogParameters params) {
        int buttonLabelStyle = params.getButtonLabelStyle();

        TextView tv = rootView.findViewById(R.id.text_button_neutral);
        initButton(tv, BUTTON_NEUTRAL, neutralLabel, buttonLabelStyle);

        tv = rootView.findViewById(R.id.text_button_positive);
        initButton(tv, BUTTON_POSITIVE, positiveLabel, buttonLabelStyle);
    }

    private void initButton(final TextView tv, final int which, final String label, final int style) {

        if (TextUtils.isEmpty(label)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(label);
        PaymentUtils.setTextAppearance(tv, style);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(which);
            }
        });
    }

    void onButtonClicked(int which) {

        if (listener != null) {
            listener.onButtonClicked(this, which);
        }
        closedByButton = true;
        dismiss();
    }

    private void handleOnBackPressed() {
        dismiss();
    }

    public interface ThemedDialogListener {
        void onButtonClicked(ThemedDialogFragment dialog, int which);

        void onDismissed(ThemedDialogFragment dialog);
    }
}
