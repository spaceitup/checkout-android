/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.test.action;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;

import org.hamcrest.Matcher;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class CustomScrollTo implements ViewAction {

    @Override
    public Matcher<View> getConstraints() {
        return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            isDescendantOfA(anyOf(isAssignableFrom(ScrollView.class),
                isAssignableFrom(HorizontalScrollView.class),
                isAssignableFrom(NestedScrollView.class))));
    }

    @Override
    public String getDescription() {
        return "CustomScrollTo for Scroll elements";
    }

    @Override
    public void perform(UiController uiController, View view) {
        new ScrollToAction().perform(uiController, view);
    }
};
