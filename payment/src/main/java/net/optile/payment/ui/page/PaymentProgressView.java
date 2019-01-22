/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.page;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.R;
import android.support.design.card.MaterialCardView;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import net.optile.payment.R;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.ProgressParameters;
import net.optile.payment.util.PaymentUtils;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;

/**
 * View managing the different style of progress animations.
 */
class PaymentProgressView {

    public final static int LOAD = 0x00;
    public final static int SEND = 0x01;
    
    private final static int SEND_MIN = 0;
    private final static int SEND_MAX = 1000;
    private final static int SEND_TIMEOUT = 30000 / SEND_MAX;

    private final PaymentPageActivity activity;
    private final View loadLayout;
    private final View sendLayout;
    private final TextView sendHeader;
    private final TextView sendInfo;
    private final Handler sendHandler;
    private final ProgressBar sendProgressBar;
    private int style;
    
    /** 
     * Construct a new loading view given the parent view that holds the Views for the loading animations
     * 
     * @param activity controls the loading animation
     * @param theme contains the ProgressParameters for theming the progress views
     */
    PaymentProgressView(PaymentPageActivity activity, PaymentTheme theme) {
        this.activity = activity;
        ProgressParameters params = theme.getProgressParameters();

        // setup the ProgressBar for loading
        loadLayout = activity.findViewById(R.id.layout_load);
        loadLayout.setBackgroundResource(params.getLoadBackground());
        inflateProgressBar(activity, loadLayout, R.layout.view_progressbar_load, params.getLoadProgressBarTheme());

        // setup the ProgressBar for sending
        sendLayout = activity.findViewById(R.id.layout_send);
        sendLayout.setBackgroundResource(params.getSendBackground());
        sendHeader = sendLayout.findViewById(R.id.text_sendheader);
        sendInfo = sendLayout.findViewById(R.id.text_sendinfo);
        PaymentUtils.setTextAppearance(sendHeader, params.getHeaderStyle());
        PaymentUtils.setTextAppearance(sendInfo, params.getInfoStyle());
 
        inflateProgressBar(activity, sendLayout, R.layout.view_progressbar_send, params.getSendProgressBarTheme());
        sendProgressBar = activity.findViewById(R.id.view_progressbar_send);
        sendProgressBar.setMax(SEND_MAX);

        sendHandler = new Handler(Looper.getMainLooper());        
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
     * Show the loading view with the given style 
     * 
     * @param visible when true, show the loading animation, hide it otherwise
     */
    public void setVisible(boolean visible) {

        if (!visible) {
            loadLayout.setVisibility(View.GONE);
            sendLayout.setVisibility(View.GONE);
            stopSendProgress();
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
        stopSendProgress();
    }

    private void setLoadVisible() {
        sendLayout.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
    }

    private void setSendVisible() {
        loadLayout.setVisibility(View.GONE);
        sendLayout.setVisibility(View.VISIBLE);
        activity.setPageTitle(activity.getString(R.string.pmprogress_sendtitle));
        sendHeader.setText(activity.getString(R.string.pmprogress_sendheader));
        sendInfo.setText(activity.getString(R.string.pmprogress_sendinfo));
        startSendProgress();
    }

    private void stopSendProgress() {
        sendHandler.removeCallbacksAndMessages(null);
    }

    private void startSendProgress() {
        sendProgressBar.setProgress(SEND_MIN);
        sendHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int progress = sendProgressBar.getProgress() + 1;
                    if (progress > SEND_MAX) {
                        progress = SEND_MIN;
                    }
                    sendProgressBar.setProgress(progress);
                    sendHandler.postDelayed(this, SEND_TIMEOUT);
                }
            }, SEND_TIMEOUT);
    }

    private void inflateProgressBar(Context context, View parent, int progressBarResId, int themeResId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup group = parent.findViewById(R.id.layout_viewholder);

        if (themeResId != 0) {
            inflater = LayoutInflater.from(new ContextThemeWrapper(context, themeResId));
        }
        inflater.inflate(progressBarResId, group, true);
    }
}
