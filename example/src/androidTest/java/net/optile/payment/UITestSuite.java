package net.optile.payment;


import net.optile.payment.tests.CheckoutPageTests;
import net.optile.payment.tests.PaymentPageTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Runs all UI Tests
@RunWith(Suite.class)
@Suite.SuiteClasses({CheckoutPageTests.class, PaymentPageTests.class})
public class UITestSuite {}
