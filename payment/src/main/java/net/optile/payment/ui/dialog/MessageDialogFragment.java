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
     * Set the message in this message dialog that should be shown to the user
     *
     * @param message shown in the middle of the message dialog
     */
    public void setMessage(String message) {
        this.message = message;
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
        initTexts(v);
        initImage(v);
        initButtons(v);
        return v;
    }

    private void initImage(View rootView) {
        ImageView imageView = rootView.findViewById(R.id.image_logo);
        if (imageResId == 0) {
            imageView.setVisibility(View.GONE);
            return;
        }
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imageResId);
    }

    private void initTexts(View rootView) {
        initTextView(rootView.findViewById(R.id.text_title), title);
        initTextView(rootView.findViewById(R.id.text_message), message);
    }

    private void initTextView(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

}
