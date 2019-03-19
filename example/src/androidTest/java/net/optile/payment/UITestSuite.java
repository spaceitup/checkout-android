/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.optile.payment.tests.CheckoutPageTests;
import net.optile.payment.tests.PaymentPageTests;

//Runs all UI Tests
@RunWith(Suite.class)
@Suite.SuiteClasses({ CheckoutPageTests.class, PaymentPageTests.class })
public class UITestSuite { }
