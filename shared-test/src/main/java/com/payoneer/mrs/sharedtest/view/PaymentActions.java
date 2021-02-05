/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.sharedtest.view;

import static androidx.test.espresso.intent.Checks.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;

import org.hamcrest.Matcher;

import com.payoneer.mrs.payment.ui.list.PaymentCardViewHolder;
import com.payoneer.mrs.payment.ui.widget.FormWidget;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.matcher.ViewMatchers;

/**
 * Class providing helper methods for performing actions on the PaymentList.
 */
public final class PaymentActions {

    /**
     * Perform the action on a View inside a card widget at the given position.
     *
     * @param position of the card inside the RecyclerView
     * @param action to be performed on the View
     * @param widgetName name of the widget inside the card, i.e. holderName or number
     * @param viewResId resource ID of the View
     * @return the newly created ViewAction
     */
    public static ViewAction actionOnViewInWidget(int position, final ViewAction action, final String widgetName, final int viewResId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return String.format("perform action on view in widget %s at position %d", widgetName, position);
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView.ViewHolder viewHolder = ((RecyclerView) view).findViewHolderForAdapterPosition(position);
                checkNotNull(viewHolder);

                if (!(viewHolder instanceof PaymentCardViewHolder)) {
                    throw createPerformException("ViewHolder is not of type PaymentCardViewHolder");
                }
                FormWidget widget = ((PaymentCardViewHolder) viewHolder).getFormWidget(widgetName);
                if (widget == null) {
                    throw createPerformException(String.format("Widget %s could not be found inside card", widgetName));
                }
                View formView = widget.getRootView().findViewById(viewResId);
                if (formView == null) {
                    throw createPerformException("Could not find the View inside the Widget: " + widgetName);
                }
                action.perform(uiController, formView);
            }
        };
    }

    /**
     * Scroll to the view action
     *
     * @return the newly created ViewAction
     */
    public static ViewAction scrollToView() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                    isDescendantOfA(anyOf(isAssignableFrom(ScrollView.class),
                        isAssignableFrom(HorizontalScrollView.class),
                        isAssignableFrom(NestedScrollView.class))));
            }

            @Override
            public String getDescription() {
                return "scroll to view action";
            }

            @Override
            public void perform(UiController uiController, View view) {
                new ScrollToAction().perform(uiController, view);
            }
        };
    }

    /**
     * Scroll to the view action
     *
     * @param value
     * @return the newly created ViewAction
     */
    public static ViewAction setValueInNumberPicker(final int value) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }

            @Override
            public String getDescription() {
                return String.format("Set the value %d of a NumberPicker", value);
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((NumberPicker) view).setValue(value);
            }
        };
    }

    private static PerformException createPerformException(String description) {
        return new PerformException.Builder()
            .withActionDescription(description)
            .build();
    }
}

