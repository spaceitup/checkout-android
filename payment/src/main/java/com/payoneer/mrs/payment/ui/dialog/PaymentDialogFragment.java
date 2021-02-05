/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.dialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.payoneer.mrs.payment.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Payment Dialog Fragment used to create Alert Dialog
 */
public class PaymentDialogFragment extends DialogFragment {

    private String title;
    private String message;
    private String negativeButton;
    private String positiveButton;
    private String tag;
    private int imageResId;
    private PaymentDialogListener listener;

    /**
     * Set the title in this payment dialog
     *
     * @param title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the message in this payment dialog
     *
     * @param message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set the Negative button label
     *
     * @param negativeButton label to be shown
     */
    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    /**
     * Set the positive button label
     *
     * @param positiveButton label
     */
    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    /**
     * Set the image resource id
     *
     * @param imageResId to be set and shown in the dialog
     */
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    /**
     * Set the tag of this dialog fragment
     *
     * @param tag of the dialog fragment
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Set the dialog listener
     *
     * @param listener to be notified of events in this dialog
     */
    public void setListener(PaymentDialogListener listener) {
        this.listener = listener;
    }

    /**
     * Show this payment dialog fragment using the manager
     *
     * @param manager to be used to show this dialog
     */
    public void show(FragmentManager manager) {
        show(manager, tag);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (listener != null) {
            listener.onDismissed();
        }
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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        addTexts(builder);
        addButtons(builder);
        addImageView(activity, builder);
        return builder.create();
    }

    private void addTexts(MaterialAlertDialogBuilder builder) {
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
    }

    private void addButtons(MaterialAlertDialogBuilder builder) {
        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    handleNegativeButtonClicked();
                }
            });
        }
        if (!TextUtils.isEmpty(positiveButton)) {
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    handlePositiveButtonClicked();
                }
            });
        }
    }

    private void addImageView(Activity activity, MaterialAlertDialogBuilder builder) {
        if (imageResId == 0) {
            return;
        }
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_imageview, null);
        ImageView imageView = view.findViewById(R.id.image_hint);
        imageView.setImageResource(imageResId);
        builder.setView(view);
    }

    private void handleNegativeButtonClicked() {
        if (listener != null) {
            listener.onNegativeButtonClicked();
        }
    }

    private void handlePositiveButtonClicked() {
        if (listener != null) {
            listener.onPositiveButtonClicked();
        }
    }

    public interface PaymentDialogListener {
        void onNegativeButtonClicked();

        void onPositiveButtonClicked();

        void onDismissed();
    }
}
