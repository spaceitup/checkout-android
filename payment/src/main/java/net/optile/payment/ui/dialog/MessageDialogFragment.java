/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;

/**
 * Message Dialog Fragment for showing a message to the user with an action button
 */
public final class MessageDialogFragment extends ThemedDialogFragment {

    private String title;
    private String message;
    private String imagePrefix;
    private String imageSuffix;
    private int imageResId;

    /**
     * Set the title in this message dialog
     *
     * @param title shown in the top of this message dialog
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTheme() {
        int theme = PaymentUI.getInstance().getPaymentTheme().getMessageDialogTheme();
        return theme == 0 ? super.getTheme() : theme;
    }

    /**
     * Set the message in this message dialog that should be shown to the user
     *
     * @param message shown in the middle of the message dialog
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setImageLabels(String imagePrefix, String imageSuffix) {
        this.imagePrefix = imagePrefix;
        this.imageSuffix = imageSuffix;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_message, container, false);
        initTitle(v);
        initMessage(v);
        initImage(v);
        initButtons(v);
        return v;
    }

    private void initImage(View rootView) {

        View layout = rootView.findViewById(R.id.layout_image);
        if (imageResId == 0) {
            layout.setVisibility(View.GONE);
            return;
        }
        layout.setVisibility(View.VISIBLE);
        initImageLabel(rootView.findViewById(R.id.text_imageprefix), imagePrefix);
        initImageLabel(rootView.findViewById(R.id.text_imagesuffix), imageSuffix);
        ImageView view = rootView.findViewById(R.id.image_logo);
        view.setVisibility(View.VISIBLE);
        view.setImageResource(imageResId);
    }

    private void initImageLabel(TextView tv, String label) {
        if (TextUtils.isEmpty(label)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(label);
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
        TextView tvTitle = rootView.findViewById(R.id.text_message_title);
        TextView tvNoTitle = rootView.findViewById(R.id.text_message_notitle);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
            initMessage(tvNoTitle);
        } else {
            tvNoTitle.setVisibility(View.GONE);
            initMessage(tvTitle);
        }
    }

    private void initMessage(TextView textView) {
        if (TextUtils.isEmpty(message)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
    }
}
