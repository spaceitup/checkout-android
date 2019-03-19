package net.optile.payment;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.optile.payment.tests.CheckoutPageTests;
import net.optile.payment.tests.PaymentPageTests;

//Runs all UI Tests
@RunWith(Suite.class)
@Suite.SuiteClasses({ CheckoutPageTests.class, PaymentPageTests.class })
public class UITestSuite { }
