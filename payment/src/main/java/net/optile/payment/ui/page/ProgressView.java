/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import net.optile.payment.R;

/**
 * Class managing showing a ProgressBar with optional labels.
 */
class ProgressView {

    private final TextView textHeader;
    private final TextView textInfo;
    private final ProgressBar progressBar;
    private final View view;

    /**
     * Construct a new loading view given the parent view that holds the Views for the loading animations
     *
     * @param view the root view containing the progress views and layouts
     */
    ProgressView(View view) {
        this.view = view;
        progressBar = view.findViewById(R.id.progressbar);
        textHeader = view.findViewById(R.id.text_header);
        textInfo = view.findViewById(R.id.text_info);
        styleProgressBar();
    }

    /**
     * Set the labels shown under the progressbar.
     *
     * @param header label
     * @param info label
     */
    public void setLabels(String header, String info) {
        if (!TextUtils.isEmpty(header)) {
            textHeader.setText(header);
            textHeader.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(header)) {
            textInfo.setText(info);
            textInfo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show the progress bar with optional title and info labels
     *
     * @param visible when true, show the loading animation, hide it otherwise
     */
    public void setVisible(boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * The ProgressBar is styled programmatically since the Android SDK must support
     * the Android version 19 and indeterminateTint is not supported for older devices.
     */
    private void styleProgressBar() {
        TypedValue typedValue = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.progressColor, typedValue, true);

        Drawable drawable = progressBar.getIndeterminateDrawable();
        if (drawable == null || typedValue.resourceId == 0) {
            return;
        }
        setColorFilter(drawable, ContextCompat.getColor(view.getContext(), typedValue.resourceId));
    }

    @SuppressWarnings("deprecation")
    private void setColorFilter(Drawable drawable, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
