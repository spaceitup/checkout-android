/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.util;

import java.net.URL;
import java.util.concurrent.Callable;

import com.bumptech.glide.Glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;

/**
 * Class for loading images into an ImageView by using Glide.
 */
public final class ImageHelper {

    /**
     * Load the image from the URL and store it into the view once loaded.
     *
     * @param view ImageView in which to place the Bitmap
     * @param url pointing to the remote image
     */
    public static void loadImage(ImageView view, URL url) {
        Glide.with(view.getContext()).asBitmap().load(url.toString()).into(view);
    }
}
