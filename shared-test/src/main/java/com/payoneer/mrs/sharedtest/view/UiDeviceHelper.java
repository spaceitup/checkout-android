/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.sharedtest.view;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.containsString;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

/**
 * Class containing helper functions to check and click UiDevice Objects
 */
public final class UiDeviceHelper {

    public final static long TIMEOUT = 20000;

    public static void checkUiObjectContainsText(UiDevice uiDevice, String text) {
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.text(text)), TIMEOUT);
        assertThat(uiObject.getText(), containsString(text));
    }

    public static void clickUiObjectByResourceName(UiDevice uiDevice, String resourceName) {
        UiObject2 uiObject = uiDevice.wait(Until.findObject(By.res(resourceName)), TIMEOUT);
        uiObject.wait(Until.enabled(true), TIMEOUT);
        uiObject.click();
    }

    public static void waitUiObjectHasPackage(UiDevice uiDevice, String packageName) {
        uiDevice.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT);
    }
}
