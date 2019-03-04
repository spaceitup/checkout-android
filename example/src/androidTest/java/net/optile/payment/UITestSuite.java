package net.optile.payment;


import net.optile.payment.tests.HomePageTests;
import net.optile.payment.tests.PaymentPageTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Runs all UI Tests
@RunWith(Suite.class)
@Suite.SuiteClasses({HomePageTests.class, PaymentPageTests.class})
public class UITestSuite {}
