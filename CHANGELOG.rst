Changelog - Android SDK
-----------------------

** Version android-sdk-3.2.0**

    * [PCX-767] - Apply changes to registration checkboxes/labels
    * [PCX-768] - Show title and text for interactions
    * [PCX-868] - Refer to new names of renamed keys
    * [PCX-922] - Lint is disabled in build      

** Version android-sdk-3.1.1**

    * [PCX-873] - Demo app and Android SDK crash when PresetAccount has no AccountMask

** Version android-sdk-3.1.0**

    * [PCX-849] - Reset minor & patch version part when major is increased
    * [PCX-721] - Support PayPal redirect flow
    * [PCX-780] - Redo expiry date field
    * [PCX-840] - Support all credit and debit cards in Android-SDK

**Version android-sdk-3.0.0**

Note: version 3.0.0 is a major release with a breaking change, the PaymentUI.RESULT_CODE_ERROR is removed.
When Interaction, PaymentError and OperationResult objects are set in the PaymentResult Object has changed as well,
please check the README.rst for more information.

* [PCX-500] - Avoid returning Internet errors to the merchant resulthandler
* [PCX-729] - POST redirects should not be followed
* [PCX-788] - Remove check for activate button label
* [PCX-287] - Run functional tests automatically for each release build
* [PCX-483] - [Android SDK] Optimize inflation of Widgets
* [PCX-484] - [Android SDK] Move Localization handling in own service
* [PCX-717] - Update images for CVV popup (Android)
* [PCX-720] - Reject LISTs with operationType other than CHARGE/PRESET
* [PCX-789] - Update documentation using resources subdomain for redirects

**Version android-sdk-2.1.4**

* [PCX-428] - Support 3DS1 flow on Android SDK
* [PCX-485] - Update external library versions used in the android SDK.

**Version android-sdk-2.1.3**

* [PCX-201] - Write Mastercard[credit cards] UI Test for Android SDK
* [PCX-429] - Account number digit-grouping on Android SDK
* [PCX-491] - Change the default error message to match Payment Page Widget

**Version android-sdk-2.1.2**

* [PCX-492] - Move PaymentTheme class to same level as other public UI classes, this is a breaking change if using the PaymentTheme class.

**Version android-sdk-2.0.1**

* [PCX-479] - Remove gradle properties dependency from build scripts

**Version android-sdk-2.0.0**

Note: version 2.0.0 is a major release with a breaking change switching from the discontinued AppCompat libraries to the new AndroidX material libraries.

* [PCX-452] - Replace support library in the Android-sdk with the new AndroidX library

**Version android-sdk-1.1.18**

* [PCX-388] - Bug, Button labels don't have a default fall-back
* [PCX-407] - Bug, paymentpage.properties loading fails
* [PCX-416] - Bug, Preselected card in ListResult does not open correct card
* [PCX-378] - Separate processing payment from payment page
* [PCX-379] - Create separate services for processing payments
* [PCX-420] - Write automated UI test to load the payment page and open the first payment card 
* [PCX-430] - Write automated UI test to validate payment with presetFirst:true
* [PCX-431] - Write automated UI test to validate payment with presetFirst:false

**Version android-sdk-1.1.17**

* [PCX-321] - Add JCB UNIONPAY DINERS and DISCOVER to default credit card grouping

**Version android-sdk-1.1.16**

* [PCX-288] - Support different test merchants for functional tests

**Version android-sdk-1.1.15**

* [PCX-289] - Launch and show payment page functional tests

**Version android-sdk-1.1.14**
      
* [PCX-259] - Rename Android SDK Example app names

**Version android-sdk-1.1.13**

* [PCX-182] - [Android SDK] Create demo app for Android SDK with summary page
* [PCX-191] - [Android SDK] Example app with summary page

**Version android-sdk-1.1.12**

* [PCX-231] - Fix automated UI tests for Android SDK

**Version android-sdk-1.1.11**

* [PCX-210] - Update model classes Redirect and Installment Plan
* [PCX-213] - Update Android versioning with GoCD
* [PCX-193] - Set Up Test Framework for Android Payment SDK Automated Testing
* [PCX-194] - Make LIST request for different environments
* [PCX-211] - Improve Android SDK Documentation

**Version android-sdk-1.1.5 - 1.1.10**

Versions created during development & testing of GoCD scripts for Android projects.  
These versions do not include changes / updates of the Android SDK.

**Version android-sdk-1.1.4**

* [PCX-173] - Make Android SDK publicly available

**Version android-sdk-1.1.3**

* [PCX-178] - Update build scripts for public hosting of Android SDK artifact

**Version android-sdk-1.1.2**

* [PCX-187] - Provide only the payment status when the page was closed
* [PCX-188] - Example app uses same platform as Android SDK (Compat, Androidx)
* [PCX-185] - Add List URL input field to example app

**Version android-sdk-1.1.1**

* [PCX-172] - Add Android annotations for validating resource parameters
* [PCX-175] - Fix horizontal mode tooltip crashes app
* [PCX-174] - Customize input fields of payment methods

**Version android-sdk-1.1.0**

* [PCX-33] - Add smart selection of credit card forms
* [PCX-34] - Client-side theming of PaymentPage
* [PCX-54] - Setup build environment for Android Payment SDK
* [PCX-56] - CHARGE timeout handling
* [PCX-59] - Apply official optile UI design
* [PCX-161] - Support PRESET Flow
* [PCX-162] - Display preset account
