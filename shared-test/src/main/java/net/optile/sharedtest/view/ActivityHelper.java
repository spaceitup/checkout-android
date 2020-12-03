/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.sharedtest.view;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import java.util.Collection;

import android.app.Activity;
import android.os.Looper;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

/**
 * Activity helper methods for testing Activities
 */
public class ActivityHelper {

    /**
     * Obtain the current Activity that is in RESUMED stage.
     *
     * @return the current Activity
     */
    public static Activity getCurrentActivity() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return getCurrentActivityOnMainThread();
        } else {
            final Activity[] topActivity = new Activity[1];
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    topActivity[0] = getCurrentActivityOnMainThread();
                }
            });
            return topActivity[0];
        }
    }

    private static Activity getCurrentActivityOnMainThread() {
        ActivityLifecycleMonitor registry = ActivityLifecycleMonitorRegistry.getInstance();
        Collection<Activity> activities = registry.getActivitiesInStage(Stage.RESUMED);
        return activities.iterator().hasNext() ? activities.iterator().next() : null;
    }
}
