/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;

/**
 * Class for loading network logo images into an ImageView. 
 * This loader will first check if a locally stored logo image is available, if not, the loader will 
 * download the logo using the provided URL.
 */
public final class NetworkLogoLoader {

    private final static String NETWORKLOGO_FOLDER = "file:///android_asset/networklogos/";
    private final Map<String, String> localNetworkLogos;
    
    private NetworkLogoLoader() {
        localNetworkLogos = new HashMap<>();
    }

    /**
     * Get the instance of this LogoLoader
     *
     * @return the instance of this LogoLoader
     */
    public static NetworkLogoLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Load the network logo given the networkCode and URL and store it into the ImageView.
     *
     * @param view ImageView in which to place the Bitmap
     * @param networkCode code of the payment network
     * @param url pointing to the remote image
     */
    public static void loadNetworkLogo(ImageView view, String networkCode, URL url) {
        getInstance().loadIntoView(view, networkCode, url);
    }

    private void loadIntoView(ImageView view, String networkCode, URL url) {
        if (localNetworkLogos.size() == 0) {
            loadLocalNetworkLogos(view.getContext());
        }
        Glide.with(view.getContext()).asBitmap().load(url.toString()).into(view);
    }
    
    private void loadLocalNetworkLogos(Context context) {
        synchronized (localNetworkLogos) {
            if (localNetworkLogos.size() != 0) {
                return;
            }
            Resources res = context.getResources();
            String[] ts = null;
            String[] ar = res.getStringArray(R.array.networklogos);
            for (String icon : ar) {
                ts = icon.split(",");
                localNetworkLogos.put(ts[0], NETWORKLOGO_FOLDER + ts[1]);
            }
        }
    }

    private static class InstanceHolder {
        static final NetworkLogoLoader INSTANCE = new NetworkLogoLoader();
    }
}
