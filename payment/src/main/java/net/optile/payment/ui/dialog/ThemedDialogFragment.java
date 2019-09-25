/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import net.optile.payment.R;

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
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new Dialog(activity, getTheme()) {
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

    void initButtons(View rootView) {
        Button button = rootView.findViewById(R.id.dialogbutton_neutral);
        initButton(button, BUTTON_NEUTRAL, neutralLabel);

        button = rootView.findViewById(R.id.dialogbutton_positive);
        initButton(button, BUTTON_POSITIVE, positiveLabel);
    }

    private void initButton(final Button button, final int which, final String label) {
        if (TextUtils.isEmpty(label)) {
            button.setVisibility(View.GONE);
            return;
        }
        button.setVisibility(View.VISIBLE);
        button.setText(label);
        button.setOnClickListener(new View.OnClickListener() {
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

    public interface ThemedDialogListener {
        void onButtonClicked(ThemedDialogFragment dialog, int which);

        void onDismissed(ThemedDialogFragment dialog);
    }
}
