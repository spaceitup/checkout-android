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
import net.optile.payment.R;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.util.PaymentUtils;
import android.widget.TextView;

/**
 * View managing the different style of progress animations.
 */
class PaymentProgressView {

    public final static int LOAD = 0x00;
    public final static int SEND = 0x01;

    private final PaymentPageActivity activity;
    private final View loadLayout;
    private final View sendLayout;
    private final TextView sendHeader;
    private final TextView sendInfo;
    private int style;

    
    /** 
     * Construct a new loading view given the parent view that holds the Views for the loading animations
     * 
     * @param activity controls the loading animation
     * @param params contains theming for the loading animations
     */
    PaymentProgressView(PaymentPageActivity activity, PageParameters params) {
        this.activity = activity;
        loadLayout = activity.findViewById(R.id.layout_progress_load);

        sendLayout = activity.findViewById(R.id.layout_progress_send);
        sendHeader = sendLayout.findViewById(R.id.text_sendheader);
        sendInfo = sendLayout.findViewById(R.id.text_sendinfo);

        PaymentUtils.setTextAppearance(sendHeader, params.getProgressSendHeaderStyle());
        PaymentUtils.setTextAppearance(sendInfo, params.getProgressSendInfoStyle());
        
        inflateProgressBar(activity, loadLayout, R.layout.view_progressbar_load, params.getProgressBarLoadTheme());
        inflateProgressBar(activity, sendLayout, R.layout.view_progressbar_send, params.getProgressBarSendTheme());
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
            return;
        }
        switch (style) {
            case SEND:
                setSendProgressVisible();
                break;
            default:
                setLoadProgressVisible();
        }
    }

    private void setSendProgressVisible() {
        loadLayout.setVisibility(View.GONE);
        sendLayout.setVisibility(View.VISIBLE);
        activity.setPageTitle(activity.getString(R.string.pmprogress_sendtitle));
        sendHeader.setText(activity.getString(R.string.pmprogress_sendheader));
        sendInfo.setText(activity.getString(R.string.pmprogress_sendinfo));
    }

    private void setLoadProgressVisible() {
        sendLayout.setVisibility(View.GONE);
        loadLayout.setVisibility(View.VISIBLE);
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
