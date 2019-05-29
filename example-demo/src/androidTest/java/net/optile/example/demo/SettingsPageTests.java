/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import net.optile.example.demo.settings.SettingsActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsPageTests {
    @Rule
    public ActivityTestRule<SettingsActivity> activityRule = new ActivityTestRule<>(
        SettingsActivity.class);

    @Test
    public void settingsPageVisibleTest() {
        onView(withId(R.id.activity_settings)).check(matches(isDisplayed()));
    }
}
