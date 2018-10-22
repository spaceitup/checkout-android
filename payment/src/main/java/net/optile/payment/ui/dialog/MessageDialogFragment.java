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
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import net.optile.payment.R;

/**
 * Message Dialog Fragment for showing a message to the user with an action button
 */
public final class MessageDialogFragment extends DialogFragment {

    private String title;

    private String message;

    private String buttonLabel;

    private String buttonAction;

    private MessageDialogListener listener;

    /**
     * Set the title in this message dialog
     *
     * @param title shown in the top of this message dialog
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the message in this message dialog that should be shown to the user
     *
     * @param message shown in the middle of the message dialog
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set the button label and action
     *
     * @param label the Label of the button
     * @param action the action of the button
     */
    public void setButton(String label, String action) {
        this.buttonLabel = label;
        this.buttonAction = action;
    }

    /**
     * Set the listener to this MessageDialogFragment
     *
     * @param listener to inform of an action button click
     */
    public void setListener(MessageDialogListener listener) {
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_message, container, false);

        initTitle(v);
        initMessage(v);
        initButton(v);
        return v;
    }

    private void initTitle(View rootView) {
        TextView tv = rootView.findViewById(R.id.text_title);
        if (TextUtils.isEmpty(title)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(title);
    }

    private void initMessage(View rootView) {
        TextView tv = rootView.findViewById(R.id.text_message);
        if (TextUtils.isEmpty(message)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(message);

        if (TextUtils.isEmpty(title)) {
            tv.setTextAppearance(R.style.PaymentText_Medium_Bold_Gray);
        } else {
            tv.setTextAppearance(R.style.PaymentText_Medium_Gray);
        }
    }

    private void initButton(View rootView) {
        View layout = rootView.findViewById(R.id.layout_button);
        layout.setVisibility(View.VISIBLE);
        TextView tv = rootView.findViewById(R.id.text_button);
        tv.setText(buttonLabel);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void handleButtonClick() {
        if (this.listener != null) {
            listener.onMessageDialogClicked(this.buttonAction);
        }
        dismiss();
    }

    private void setTextView(final View rootView, final int resId, final String value) {
        TextView tv = rootView.findViewById(resId);
        if (TextUtils.isEmpty(value)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(value);
    }

    public interface MessageDialogListener {
        void onMessageDialogClicked(String action);
    }
}
