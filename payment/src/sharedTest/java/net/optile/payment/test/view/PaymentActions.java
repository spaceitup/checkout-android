/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.test.view;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;

import org.hamcrest.Matcher;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import net.optile.payment.ui.list.PaymentCardViewHolder;
import net.optile.payment.ui.widget.FormWidget;

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

    private static PerformException createPerformException(String description) {
        return  new PerformException.Builder()
            .withActionDescription(description)
            .build();
    }
}

