/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.util;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import java.net.URL;
import java.util.concurrent.Callable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

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
import net.optile.payment.network.ImageConnection;

/**
 * Class for loading images into an ImageView either by using Glide or own network library.
 * The decision to either use Glide or own network library depends on the Android version.
 * If the Android version is Kitkat then the own network libraries are used since these support
 * TLS1.2 for secure connections.
 */
public final class ImageHelper {
    private final ImageConnection imageConnection;

    private ImageHelper() {
        this.imageConnection = new ImageConnection();
    }

    /**
     * Get the instance of this ImageHelper
     *
     * @return the instance of this ImageHelper
     */
    public static ImageHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Load the image from the URL and store it into the view once loaded.
     *
     * @param view ImageView in which to place the Bitmap
     * @param url pointing to the remote image
     */
    public void loadImage(ImageView view, URL url) {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            loadImageWithNetwork(view, url);
        } else {
            loadImageWithGlide(view, url);
        }
    }

    private void loadImageWithGlide(final ImageView view, final URL url) {
        Glide.with(view.getContext()).
            load(url.toString()).
            into(view);
    }

    private void loadImageWithNetwork(final ImageView view, final URL url) {

        if (hasImage(view)) {
            return;
        }
        WorkerTask<Bitmap> task = WorkerTask.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws PaymentException {
                return imageConnection.loadBitmap(url);
            }
        });
        task.subscribe(new WorkerSubscriber<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callbackLoadBitmapSuccess(view, bitmap);
            }

            @Override
            public void onError(Throwable cause) {
                Log.w("sdk_ImageHelper", cause);
                // we ignore image loading failures
            }
        });
        Workers.getInstance().forImageTasks().execute(task);
    }

    private boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    private void callbackLoadBitmapSuccess(ImageView view, Bitmap bitmap) {

        try {
            view.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.w("sdk_ImageHelper", e);
            // we ignore image loading failures
        }
    }

    private static class InstanceHolder {
        static final ImageHelper INSTANCE = new ImageHelper();
    }
}
