package net.optile.payment.tests;
//package net.optile.payment.network;


import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import net.optile.example.checkout.CheckoutActivity;
import net.optile.example.checkout.R;
import net.optile.payment.network.ListUrlGen;
import net.optile.payment.util.PaymentUtils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.*;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import java.io.FileReader;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class PaymentPageTests {

    String uri = "https://api.sandbox.oscato.com";
    String auth = "Basic U1BBQ0VJVFVQOmFxYTZmZGpyMDgyYzBxbzJmODFndG1xdXU4YW81ZnBiZGpxbjhoam8=";

    @Rule
    public ActivityTestRule<CheckoutActivity> activityRule = new ActivityTestRule<>(
            CheckoutActivity.class);

    @Test
    public void openPaymentPage() throws  IOException {
        ListUrlGen listUrlGen = new ListUrlGen();
        String listUrl = listUrlGen.createNewListUrl();
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());
    }
}

