package net.optile.payment.tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.optile.example.checkout.CheckoutActivity;
import net.optile.example.checkout.R;
import net.optile.payment.list.ListConfig;
import net.optile.payment.list.ListService;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.*;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class PaymentPageTests {
    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule = new ActivityTestRule<>(
        CheckoutActivity.class);

    @Test
    public void openPaymentPageTest() throws IOException {
        ListService listService = new ListService();
        String listUrl = listService.createNewListUrl();
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());
    }

    @Test
    public void configBodyTests() throws JSONException, IOException {
        ListService listService = new ListService();
        ListConfig config = listService.createNewBodyConfig();
        config.setPrice("1.0");
        String listUrl = listService.createConfigListUrl(config);
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());
    }
}

