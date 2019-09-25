/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.page;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.optile.payment.R;

/**
 * View managing the different style of progress animations.
 */
class ProgressView {

    public final static int LOAD = 0x00;
    public final static int SEND = 0x01;

    private final static int SEND_MIN = 0;
    private final static int SEND_MAX = 1000;
    private final static int SEND_TIMEOUT = 30000 / SEND_MAX;

    private final View loadLayout;
    private final View sendLayout;
    private final TextView sendHeader;
    private final TextView sendInfo;
    private final ProgressBar loadProgressBar;
    private final ProgressBar sendProgressBar;
    private final View rootView;
    private int style;

    /**
     * Construct a new loading view given the parent view that holds the Views for the loading animations
     *
     * @param rootView the root view containing the progress views and layouts
     */
    ProgressView(View rootView) {
        this.rootView = rootView;

        // setup the ProgressBar for loading
        loadLayout = rootView.findViewById(R.id.layout_load);
        loadProgressBar = loadLayout.findViewById(R.id.progressbar_load);

        // setup the ProgressBar for sending
        sendLayout = rootView.findViewById(R.id.layout_send);
        sendHeader = sendLayout.findViewById(R.id.text_sendheader);
        sendInfo = sendLayout.findViewById(R.id.text_sendinfo);
        sendProgressBar = sendLayout.findViewById(R.id.progressbar_send);
    }

    /**
     * Set the style of progress that this view should show
     *
     * @param style to be shown when made visible
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * Set the labels shown when using the SEND progress type.
     *
     * @param header shown for the SEND type.
     * @param info shown for the SEND type.
     */
    public void setSendLabels(String header, String info) {
        sendHeader.setText(header);
        sendInfo.setText(info);
    }

    /**
     * Show the loading view with the given style
     *
     * @param visible when true, show the loading animation, hide it otherwise
     */
    public void setVisible(boolean visible) {

        if (!visible) {
            loadLayout.setVisibility(View.GONE);
            sendLayout.setVisibility(View.GONE);
            return;
        }
        switch (style) {
            case SEND:
                setSendVisible();
                break;
            default:
                setLoadVisible();
        }
    }

    /**
     * Notify that this Progress view should be stopped
     */
    public void onStop() {
    }

    private void setLoadVisible() {

        if (loadLayout.getVisibility() == View.VISIBLE) {
            return;
        }
        sendLayout.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
    }

    private void setSendVisible() {

        if (sendLayout.getVisibility() == View.VISIBLE) {
            return;
        }
        loadLayout.setVisibility(View.GONE);
        sendLayout.setVisibility(View.VISIBLE);
    }
}
