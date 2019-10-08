
Introduction
============

The Android SDK makes it easy to integrate with optile
and provides a great looking payment experience in your Android app. The
Android SDK comes with a ready made, easy to use Payment Page which takes care
of showing supported payment methods and handling payments. The Android SDK also
provide low-level packages that can be used to build your own custom
payment experience in your app.

Supported Features
==================

Android Version
---------------

Android API versions 19 - 28 (Kitkat 4.4 - Pie 9.0) are supported by the
Android SDK. TLS1.2 is enabled for Android version 19 (Kitkat).

AndroidX
--------

The Android SDK 2.0 is build using the new AndroidX material libraries. If your app continues to use external libraries depending on the older AppCompat libraries then please keep the following two lines in the gradle.properties file:

::
   
    android.enableJetifier=true
    android.useAndroidX=true

Proguard
--------

If you intend to obfuscate your mobile app then please make sure to exclude the Android SDK classes from being obfuscated as well. Excluding the Android SDK from being obfuscated can be done by adding the following to your proguard-rules.pro file:

::

    -keep class net.optile.** { *; }
    
The Android SDK uses the following third-party libraries, please make sure to add the proper rules for these libraries in your proguard-rules.pro file if needed.

::

    implementation "com.google.code.gson:gson:${rootProject.gsonVersion}"
    implementation "com.github.bumptech.glide:glide:${rootProject.bumptechGlideVersion}"
 
Payment Methods
---------------

All “direct” payment methods are supported, this includes Credit, Debit
cards and Sepa. Payments that require “redirects” (external WebView) like
Paypal and Sofort are not supported. The option “presetFirst”
is also supported and provides the option to show a summary page to your users
before finalizing the payment.

Integration Scenario
--------------------

The Android SDK requires payment sessions created using the DISPLAY_NATIVE
integration scenario. Below is a sample list request object that can be
used to create a payment session that is supported by the Android SDK.

Example list request Json body:

.. code-block:: json

    {
        "transactionId": "tr1",
        "integration": "DISPLAY_NATIVE",
        "presetFirst": "false",
        "country": "DE",
        "customer": {
            "number": "1",
            "email": "john.doe@example.com"
        },
        "payment": {
            "amount": 9.99,
            "currency": "EUR",
            "reference": "Shop optile/03-12-2018"
        },
        "style": {
            "language": "en_US"
        },
        "callback": {
            "returnUrl": "https://example.com/shop/success.html",
            "summaryUrl": "https://example.com/shop/summary.html",
            "cancelUrl": "https://example.com/shop/cancel.html",
            "notificationUrl": "https://example.com/shop/notify.html"
        }
    }

Account Registration
------------

Optile offers two account registration types, both of which are supported by the Android SDK: Regular and Recurring.
Depending on the registration settings returned in a LIST response, checkboxes may be displayed for either registration type.
Please see documentation at `optile.io <https://www.optile.io/opg#291077>`_ for more information about account registration types.

Examples
========

The Android SDK repository contains two example apps demonstrating how to integrate the Android SDK into a mobile app. Both examples support lists created with either the presetFirst parameter set to true or false and it supports both registered users with saved accounts and unregistered users.

1. Basic Example
-------------------

This example demonstrates how to initialize and open the payment page provided by the Android SDK. It also shows how to change the theme, set custom payment groups and receive payment results returned by the Android SDK. The sources of the basic example can be found `here <./example-basic>`_. Paste a valid listUrl in the input field and click the button to start this example.

2. Demo Example
---------------

The demo example shows how to use the Android SDK when a summary page is required to finalize the payment. The sources of this app can be found `here <./example-demo>`_. To use this example app paste a valid listUrl in the input field and click the button.

Your first payment
==================

In order to make a successful payment you must complete the following
steps:

1. Install Android SDK in your app
2. Create a payment session and obtain the "self" URL from the list result in your app
3. Initialize and show the Payment Page with the list URL

1 - Install Android SDK
-----------------------

Installing the Android SDK is easy and requires only adding the Android SDK module to your build.gradle file. 

Repository
~~~~~~~~~~~

Add the packagecloud.io repository to the top level build.gradle file.

::

    allprojects {
        repositories {
            maven {
                url "https://packagecloud.io/optile/repo/maven2"
            }
        }
    }

Dependency
~~~~~~~~~~

Add the android-sdk dependency to the dependencies section of the app’s level build.gradle file.

::

    dependencies {
        implementation "com.oscato.mobile:android-sdk:2.0.1"
    }

2 - Create payment session
--------------------------

The documentation at `optile.io <https://optile.io>`_ will guide you through optile’s Open
Payment Gateway (OPG) features for frontend checkout and backend use
cases. It provides important information about integration scenarios,
testing possibilities, and references. Click `here <https://www.optile.io/reference#tag/list>`_ for the API reference documentation describing how to construct a payment session request.

After you have created a payment session you will receive a response containing the list result in Json format.
This list result contains a “self” URL which is used to initialize the Payment Page.

Top part of the list result containing the “self” URL:

.. code-block:: json

    {
        "links": {
            "self": "https://api.integration.oscato.com/pci/v1/5c17b47e7862056fa0755e66lrui4dvavak9ehlvh4n3abcde9",
            "customer": "https://api.integration.oscato.com/api/customers/123456789862053ccf15479eu"
        },
        "timestamp": "2018-12-17T14:36:46.105+0000",
        "operation": "LIST"
    
3 - Show Payment Page
---------------------

The Android SDK provides a class called PaymentUI which is used to initialize and open the Payment Page. There is no need to create an Activity to show the Payment Page since the Android SDK takes care of initializing and creating the Payment Page Activity. The onActivityResult() method must be implemented to receive the result from the Payment Page Activity, this will be explained in the chapter "Payment Result".

Code sample how to initialize and display the Payment Page:

.. code-block:: java

    // Request code to identify the response in onActivityResult()
    int PAYMENT_REQUEST_CODE = 1;

    // list URL obtained from your backend
    String listUrl = "<https://...>";

    // Show the Payment Page
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setListUrl(listUrl);
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Payment Result
==============

Payment results are returned through the onActivityResult() method in your Activity. When the page is closed, the returned PaymentResult class contains information about the performed operation. I.e. it may contain an Interaction and OperationResult object describing the state of the latest Charge operation.

The Interaction and OperationResult objects are never created by the Android SDK but instead come from the Optile Payment API. The PaymentError object inside the PaymentResult class is created by the Android SDK and contains information about an error that happened inside the Android SDK. 

Code sample how to obtain the PaymentResult inside the onActivityResult() method:

.. code-block:: java

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    
        if (requestCode != PAYMENT_REQUEST_CODE || data == null) {
            return;
        }
        PaymentResult result = PaymentResult.fromResultIntent(data);        
        if (result == null) {
            return;
        }
        String resultInfo = result.getResultInfo();

        // Operation request has been made and "result" contains
        // an Interaction and optional OperationResult describing the operation result
        if (resultCode == PaymentUI.RESULT_CODE_OK) {
            Interaction interaction = result.getInteraction();
            OperationResult operationResult = result.getOperationResult();
        } 

        //"result" contains a resultInfo and an optional Interaction and optional OperationResult. 
        //If the Interaction is null then the user closed the page before any request was made.
        if (resultCode == PaymentUI.RESULT_CODE_CANCELED) {
            Interaction interaction = result.getInteraction();
            OperationResult operationResult = result.getOperationResult();
        }
       
        // "result" contains a PaymentError explaining the error that occurred i.e. connection error.
        if (resultCode == PaymentUI.RESULT_CODE_ERROR) {
            PaymentError error = result.getPaymentError();
        }
    }

Successful
----------

The RESULT_CODE_OK code indicates that the operation request was successful, there are two situations when this result is returned:

1. InteractionCode is PROCEED - the PaymentResult contains an OperationResult with detailed information about the operation. 

2. InteractionCode is ABORT and InteractionReason is DUPLICATE_OPERATION, this means that a previous operation on the same list has already been performed. This may happen if there was a network error during the first operation and the Android SDK was unable to receive a proper response from the Payment API.

Canceled
---------

The RESULT_CODE_CANCELED code indicates that the Android SDK did not perform a successful operation. This may happen for different reasons, i.e. the user clicked the back button. The PaymentResult may contain an Interaction and an OperationResult with details about the failed operation. If both Interaction and OperationResult are null then the user closed the page before any request was made. 
    
Error
-----

The RESULT_CODE_ERROR code indicates that an unrecoverable error has occurred, i.e. a SecurityException has been thrown inside the Android SDK. The PaymentResult contains a PaymentError Object with the error details.
    
Summary Page (Delayed Payment Submission)
===========

Showing a summary page before a user makes the final charge (i.e. display the cart contents, final price, selected payment method etc.) can be achieved by implementing the Delayed Payment Submission flow supported by the Android SDK in three simple steps. Please see documentation at `optile.io <https://www.optile.io/opg#292155>`_ for more information about Delayed Payment Submission.

1. Enable presetFirst
---------------------

The first step is to set the presetFirst parameter in the list request body to true as shown in the example below. 

Example list request Json body with presetFirst set to true:

.. code-block:: json

    {
        "transactionId": "tr1",
        "integration": "DISPLAY_NATIVE",
        "presetFirst": "true",
        "country": "DE",

2. Show Payment Page
--------------------

Open the payment page with the listUrl as explained earlier. Notice the buttons for each payment method have changed from "Pay" to "Continue". When the user clicks a button, the Android SDK will preselect the payment method instead of making a direct charge request. Once the user has preselected a payment method, the payment page will be closed and a PaymentResult is returned through the onActivityResult() method. This is the correct point to display a summary page to the user.

3. Charge PresetAccount
-----------------------

When reloading the ListResult from the Payment API, it now contains a PresetAccount. This PresetAccount represents the payment method previously selected by the user in the payment page. The Android SDK can be used to charge this PresetAccount by using the chargePresetAccount() method in the PaymentUI class. After calling this method an Activity will be launched showing the sending progress and it will post the charge request to the Payment API. Once the charge is completed a PaymentResult is returned through the onActivityResult() method.

Code sample how to charge a PresetAccount:

.. code-block:: java

    // Request code to identify the response in onActivityResult()
    int PAYMENT_REQUEST_CODE = 1;

    // get the preset account from the ListResult
    PresetAccount account = listResult.getPresetAccount();
    
    // list URL obtained from your backend
    String listUrl = "<https://...>";

    // Show the charge preset account page
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setListUrl(listUrl);
    paymentUI.chargePresetAccount(this, PAYMENT_REQUEST_CODE, account);

Customize Payment Page
======================

The look & feel of the Payment Page may be customized, i.e. colors, font
style and icons can be changed so that it matches the look & feel of your
mobile app.

Page Orientation
----------------

By default the orientation of the Payment Page will be locked based on
the orientation in which the Payment Page was opened. I.e. if the mobile
app is shown in landscape mode the Payment Page will also be opened in
landscape mode but cannot be changed anymore by rotating the phone.

Code sample how to set the fixed orientation mode:

.. code-block:: java

    //
    // Orientation modes supported by the Payment Page
    // ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    // ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    // ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
    // ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
    //
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Page Theming
------------

Theming of the Android SDK screens and views are done using the PaymentTheme class. In order for theming to take effect, the customized PaymentTheme instance
must be set in the PaymentUI class prior to opening i.e. the Payment Page.

Code sample how to create and set a custom PaymentTheme:

.. code-block:: java

    PaymentTheme.Builder builder = PaymentTheme.createBuilder();
    // Set here the different theme parameters in the builder
    
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setPaymentTheme(builder.build());
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

The PaymentTheme contains a set of parameters defining the customized
theming.

IconMapping
~~~~~~~~~~~

The PaymentTheme allow setting individual drawable resource ids for icons
by using the putInputTypeIcon() method, use the setDefaultIconMapping()
method to use the icons provided by the Android SDK.

Validation colors
~~~~~~~~~~~~~~~~~

The three validation colors (unknown, error and ok) can be set in the PaymentTheme and these colors will
be used for coloring the icons in front of the input fields.

Theming PaymentList screen
~~~~~~~~~~~~~~~~~~~~~~~~~~

The theming of the PaymentList is defined by creating a new theme in your themes.xml and setting custom attributes in this theme. Once the theme has been created in your themes.xml file it can be set in the PaymentTheme class.

Code sample how to create and set a custom PaymentList theme:

.. code-block:: java

    PaymentTheme.Builder builder = PaymentTheme.createBuilder();
    builder.setPaymentListTheme(R.style.CustomPaymentTheme_PaymentList);

The following list describes the attributes you can use to theme the PaymentList.

Table explaining each attribute:

+-----------------------------------+--------------------------------------------+
| Name                              | Purpose                                    |
+===================================+============================================+
| paymentListToolbarTheme           | Theme of the PaymentList Toolbar           |
+-----------------------------------+--------------------------------------------+
| paymentListToolbarTitleStyle      | TextAppearance of the toolbar title        |
+-----------------------------------+--------------------------------------------+
| paymentListEmptyLabelStyle        | TextAppearance of label shown when the     |
|                                   | list of payment methods is empty           |
+-----------------------------------+--------------------------------------------+
| paymentListHeaderLabelStyle       | TextAppearance of section header label in  |
|                                   | the list, i.e. “Saved accounts”            |
+-----------------------------------+--------------------------------------------+
| paymentCardStyle                  | The style for payment cards inside the     |
|                                   | the list                                   |
+-----------------------------------+--------------------------------------------+
| paymentCardLogoBackground         | Background resource ID drawn behind        |
|                                   | payment method images                      |
+-----------------------------------+--------------------------------------------+
| presetCardTitleStyle              | TextAppearance of preset card title,       |
|                                   | i.e. “41 \**\* 1111”                       |
+-----------------------------------+--------------------------------------------+
| presetCardSubtitleStyle           | TextAppearance of preset card subtitle,    |
|                                   | i.e. the expiry date “01 / 2032”           |
+-----------------------------------+--------------------------------------------+
| accountCardTitleStyle             | TextAppearance of account card title,      |
|                                   | i.e. “41 \**\* 1111”                       |
+-----------------------------------+--------------------------------------------+
| accountCardSubtitleStyle          | TextAppearance of account card subtitle,   |
|                                   | i.e. the expiry date “01 / 2032”           |
+-----------------------------------+--------------------------------------------+
| networkCardTitleStyle             | TextAppearance of network card title       |
|                                   | i.e. Visa or GooglePay                     |
+-----------------------------------+--------------------------------------------+
| hintDrawable                      | Drawable resource ID of the hint icon for  |
|                                   | verification codes                         |
+-----------------------------------+--------------------------------------------+
| widgetTextInputStyle              | Style for widget TextInputLayout views     |
+-----------------------------------+--------------------------------------------+
| widgetEditTextStyle               | style for widget TextInputEditText views   |
+-----------------------------------+--------------------------------------------+
| widgetButtonStyle                 | Style for widget action button in payment  |
|                                   | cards                                      |
+-----------------------------------+--------------------------------------------+
| widgetCheckBoxStyle               | Style for widget checkboxes views          |
+-----------------------------------+--------------------------------------------+
| widgetCheckBoxLabelCheckedStyle   | TextAppearance of label when checkBox is   |
|                                   | checked                                    |
+-----------------------------------+--------------------------------------------+
| widgetCheckBoxLabelUncheckedStyle | TextAppearance of label when checkBox is   |
|                                   | unchecked                                  |
+-----------------------------------+--------------------------------------------+
| widgetSelectLabelStyle            | TextAppearance of label shown above        |
|                                   | SelectBox                                  |
+-----------------------------------+--------------------------------------------+
| progressBackground                | Background resource ID of the loading page |
+-----------------------------------+--------------------------------------------+
| progressColor                     | Indeterminate ProgressBar color resource   |
|                                   | ID                                         | 
+-----------------------------------+--------------------------------------------+

Theming ChargePayment screen
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Similar to the theming of the PaymentList, the ChargePayment screen will also be themed using custom attributes and set in the PaymentTheme class.

Code sample how to create and set a custom ChargePayment theme:

.. code-block:: java

    PaymentTheme.Builder builder = PaymentTheme.createBuilder();
    builder.setChargePaymentTheme(R.style.CustomPaymentTheme_ChargePayment);

Table explaining each attribute:

+-----------------------------------+--------------------------------------------+
| Name                              | Purpose                                    |
+===================================+============================================+
| progressBackground                | Background resource ID of the loading page |
+-----------------------------------+--------------------------------------------+
| progressColor                     | Indeterminate ProgressBar color resource   |
|                                   | ID                                         | 
+-----------------------------------+--------------------------------------------+
| progressHeaderStyle               | TextAppearance of the header progress      |
|                                   | label                                      | 
+-----------------------------------+--------------------------------------------+
| progressInfoStyle                 | TextAppearance of the info progress label  |
+-----------------------------------+--------------------------------------------+


Theming dialogs
~~~~~~~~~~~~~~~~

The Android SDK uses two different dialogs.
The date dialog is used to enter the expiration data of credit cards and a
message dialog is used for showing messages and asking questions.
The custom themes of both date and message dialogs can be set in the PaymentTheme class.

Code sample how to create the date and message dialog themes:

.. code-block:: java

    PaymentTheme.Builder builder = PaymentTheme.createBuilder();
    builder.setDateDialogTheme(R.style.CustomDialogTheme_Date);
    builder.setMessageDialogTheme(R.style.CustomDialogTheme_Message);

Table explaining the attributes in the shared PaymentDialogTheme:

+-----------------------------------+--------------------------------------------+
| Name                              | Purpose                                    |
+===================================+============================================+
| themedDialogButtonStyle           | Style for buttons used in both message and |
|                                   | date dialogs                               |
+-----------------------------------+--------------------------------------------+

Table explaining the attributes for the date dialog:

+-----------------------------------+--------------------------------------------+
| Name                              | Purpose                                    |
+===================================+============================================+
| themedDateDialogTitleStyle        | TextAppearance of the title in a date      |
|                                   | dialog                                     |
+-----------------------------------+--------------------------------------------+

Table explaining the attributes for the message dialog:

+----------------------------------------+--------------------------------------------+
| Name                                   | Purpose                                    |
+========================================+============================================+
| themedMessageDialogTitleStyle          | TextAppearance of title in message dialog  |
+----------------------------------------+--------------------------------------------+
| themedMessageDialogDetailsStyle        | TextAppearance of message in message       |
|                                        | dialog                                     |
+----------------------------------------+--------------------------------------------+
| themedMessageDialogDetailsNoTitleStyle | TextAppearance of message in message       |
|                                        | dialog without title                       |
+----------------------------------------+--------------------------------------------+
| themedMessageDialogImageLabelStyle     | TextAppearance of the image prefix &       |
|                                        | suffix labels in messag dialog             |
+----------------------------------------+--------------------------------------------+


Grouping of Payment Methods
===========================

Grouping of payment methods within a card in the payment page is supported. 
By default the Android SDK supports one group which contains the payment methods Visa, 
Mastercard and American Express.
The default grouping of payment methods is defined in `groups.json <./payment/src/main/res/raw/groups.json>`_.

Customize grouping
------------------

Customization which payment methods are grouped together in a card is allowed. 
Customisation is done by setting the resource ID of a grouping Json settings 
file prior to showing the payment page. 
Payment methods can only be grouped together when they
have the same set of InputElements. If InputElements of grouped
Payment Methods differ then each Payment Method will be shown in its own
card in the payment page. The following example shows how to create two
groups, first group contains Mastercard and Amex and the second group
contains Visa and Visa Electron.

Example customgroups.json file:

.. code-block:: json

    [
        {
            "items": [
                {
                    "code": "MASTERCARD",
                    "regex": "^5[0-9]*$"
                },
                {
                    "code": "AMEX",
                    "regex": "^3[47][0-9]*$"
                }
            ]
        },
        {
            "items": [
                {
                    "code": "VISA",
                    "regex": "^4[0-9]*$"
                },
                {
                    "code": "VISAELECTRON",
                    "regex": "^4[0-9]*$"
                }
            ]
        }
    ]

Code sample how to set a customgroups.json file:

.. code-block:: java

    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setGroupResId(R.raw.customgroups);
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Remove default group
----------------

By default the Android SDK groups together payment methods Discover, Mastercard, Diners, Unionpay, AMEX, JCB and VISA into one card. Removing this default group is done by initializing the Android SDK with a group json file containing an empty array.

Example removedefaultgroup.json file:

.. code-block:: json

    []

Code sample how to set the removedefaultgroup.json file:

.. code-block:: java

    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setGroupResId(R.raw.removedefaultgroup);
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Smart Selection
---------------

The choice which payment method in a group is displayed and used for
charge requests is done by “Smart Selection”. Each payment method in a
group contains a Regular Expression that is used to “smart select” this
method based on the partially entered card number. While the
user types the number, the Android SDK will validate the partial number with the
regular expression. When one or more payment methods match the number
input they will be highlighted.

Table containing the rules of Smart Selection:

+-------------------------+--------------------------------------------+
| Name                    | Purpose                                    |
+=========================+============================================+
| No payment method regex | The first payment method in the group is   |
| match the number input  | displayed and is used to validate input    |
| value.                  | values and perform Charge/Preset requests. |
+-------------------------+--------------------------------------------+
| Two or more payment     | The first matching payment method is       |
| method regex match the  | displayed and is used to validate input    |
| number input value      | values and perform Charge/Preset requests. |
+-------------------------+--------------------------------------------+
| One payment method      | This payment method is displayed and is    |
| regex match the number  | used to validate input values and          |
| input value.            | perform Charge/Preset requests.            |
+-------------------------+--------------------------------------------+

Input Validation
================

The Android SDK validates all input values provided by the user before all charge/preset requests. 
The file `validations.json <./payment/src/main/res/raw/validations.json>`_ contains the regular expression
definitions that the Android SDK uses to validate numbers, verificationCodes, bankCodes and holderNames. 
Validations for other input values i.e. expiryMonth and expiryYear are defined by the `Validator.java <./payment/src/main/java/net/optile/payment/validation/Validator.java>`_.

Customize validations
---------------------

Customization of validations applied to certain input types is allowed. 

- Validation for number, bankCode, holderName and verificationCode can be customized with the "regex" parameter.
- Input fields can be hidden by setting the "hide" parameter is true.
- The maximum input length can be set with the "maxLength" parameter.

Customized validations can be set by providing the resource ID of the validation Json file to the
PaymentUI class prior to showing the payment page. The default validation provided by the Android SDK are sufficient in most cases.

Example customvalidations.json file:

.. code-block:: json

    [{
        "code": "VISA",
        "items": [
            {
                "type": "number",
                "regex": "^4(?:[0-9]{12}|[0-9]{15}|[0-9]{18})$"
            },
            {
                "type": "verificationCode",
                "regex": "^[0-9]{3}$",
                "maxLength": 3
            }
        ]
    },
    {
        "code": "SEPADD",
        "items": [
            {
                "type": "bic",
                "hide": true
            }
        ]
    }]

Code sample how to set the customvalidations.json file:

.. code-block:: java

    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setValidationResId(R.raw.customvalidations);
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
