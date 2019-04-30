
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

The Android SDK is build with the AppCompat Design library. In order to use the Android SDK with an AndroidX app the following two lines must be added to the gradle.properties file:

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

1. Example-Checkout
-------------------

This example demonstrates how to initialize and open the payment page provided by the Android SDK. It also shows how to change the theme, set custom payment groups and receive payment results returned by the Android SDK. The sources of the checkout example can be found `here <./example-checkout>`_. Paste a valid listUrl in the input field and click the button to start this example.

2. Example-Demo
---------------

The demo example app shows how to use the Android SDK when a summary page (Delayed Payment Submission) is required to finalize the payment. The sources of this app can be found `here <./example-demo>`_. To use this example app paste a valid listUrl in the input field and click the button.

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
        implementation "com.oscato.mobile:android-sdk:1.1.12"
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
        PaymentResult result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
        
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

    // list URL obtained from your backend
    String listUrl = "<https://...>";

    // Show the charge preset account page
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setListUrl(listUrl);
    paymentUI.chargePresetAccount(this, PAYMENT_REQUEST_CODE);

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

Theming of the Payment Page is done using the PaymentTheme class and in
order for theming to take effect, the customized PaymentTheme instance
must be set in the PaymentUI class prior to opening the Payment Page.

Code sample how to create and set a custom PaymentTheme:

.. code-block:: java

    PaymentTheme.Builder builder = PaymentTheme.createBuilder();
    // Set here the different theme parameters in the builder
    
    PaymentUI paymentUI = PaymentUI.getInstance();
    paymentUI.setPaymentTheme(builder.build());
    paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

The PaymentTheme contains a set of parameters defining the customized
theming. When a parameter name ends with Style, the parameter holds a
TextAppearance style resource id used for TextView elements. If the
parameter name ends with Theme then the parameter holds a theme resource
id and is applied during inflation of the UI element.

PageParameters
~~~~~~~~~~~~~~

The PageParameters class contains a collection of parameters used to
theme the page and list.

Code sample how to set the PageParameters in the PaymentTheme:

.. code-block:: java

    PageParameters pageParams = PageParameters.createBuilder().
    setPageTheme(R.style.CustomThemePaymentPage).
    build();
    builder.setPageParameters(pageParams);

Table explaining each page parameter:

+--------------------------+--------------------------------------------+
| Name                     | Purpose                                    |
+==========================+============================================+
| pageTheme                | Main theme of the Payment Page Activity.   |
+--------------------------+--------------------------------------------+
| emptyListLabelStyle      | TextAppearance of label shown when the     |
|                          | list of payment methods is empty           |
+--------------------------+--------------------------------------------+
| sectionHeaderLabelStyle  | TextAppearance of section header label in  |
|                          | the list, i.e. “Saved accounts”            |
+--------------------------+--------------------------------------------+
| networkCardTitleStyle    | TextAppearance of network card title,      |
|                          | i.e. “Visa”                                |
+--------------------------+--------------------------------------------+
| accountCardTitleStyle    | TextAppearance of account card title,      |
|                          | i.e. “41 \**\* 1111”                       |
+--------------------------+--------------------------------------------+
| accountCardSubtitleStyle | TextAppearance of account card subtitle,   |
|                          | i.e. the expiry date “01 / 2032”           |
+--------------------------+--------------------------------------------+
| paymentLogoBackground    | Background resource ID drawn behind        |
|                          | payment method images                      |
+--------------------------+--------------------------------------------+

WidgetParameters
~~~~~~~~~~~~~~~~

The WidgetParameters contains a collection of parameters used to theme
widgets. Widgets are UI elements handling user input, i.e. TextInput,
CheckBoxes Select options. Below is a table explaining each parameter.

The WidgetParameters class allow setting individual drawable resource
ids for icons by using the putInputTypeIcon() method, use the
setDefaultIconMapping() method to use the icons provided by the Android SDK.

Code sample how to set the WidgetParameters in the PaymentTheme:

.. code-block:: java

    WidgetParameters widgetParams = WidgetParameters.createBuilder().
    setTextInputTheme(R.style.CustomThemeTextInput).
    build();
    builder.setWidgetParameters(widgetParams);

Table explaining each widget parameter:

+-----------------------------+--------------------------------------------+
| Name                        | Purpose                                    |
+=============================+============================================+
| textInputTheme              | Theme for TextInputLayout elements         |
+-----------------------------+--------------------------------------------+
| buttonTheme                 | Theme for action button in each payment    |
|                             | card                                       |
+-----------------------------+--------------------------------------------+
| buttonLabelStyle            | TextAppearance of label inside the action  |
|                             | button                                     |
+-----------------------------+--------------------------------------------+
| buttonBackground            | Background resource ID of action button    |
+-----------------------------+--------------------------------------------+
| checkBoxTheme               | Theme for checkBox UI element              |
+-----------------------------+--------------------------------------------+
| checkBoxLabelCheckedStyle   | TextAppearance of label when checkBox is   |
|                             | checked                                    |
+-----------------------------+--------------------------------------------+
| checkBoxLabelUncheckedStyle | TextAppearance of label when checkBox is   |
|                             | unchecked                                  |
+-----------------------------+--------------------------------------------+
| selectLabelStyle            | TextAppearance of label shown above        |
|                             | SelectBox                                  |
+-----------------------------+--------------------------------------------+
| validationColorOk           | Color resource ID indicating successful    |
|                             | validation state                           |
+-----------------------------+--------------------------------------------+
| validationColorUnknown      | Color resource ID indicating unknown       |
|                             | validation state                           |
+-----------------------------+--------------------------------------------+
| validationColorError        | Color resource ID indicating error         |
|                             | validation state                           |
+-----------------------------+--------------------------------------------+
| hintDrawable                | Drawable resource ID of the hint icon for  |
|                             | verification codes                         |
+-----------------------------+--------------------------------------------+

DialogParameters
~~~~~~~~~~~~~~~~

The DialogParameters in the PaymentTheme holds parameters to theme popup
dialog windows. The Android SDK contain two different dialogs, the
DateDialog for setting expiry dates and MessageDialog to show warnings
and errors.

Code sample how to set the DialogParameters in the PaymentTheme:

.. code-block:: java

    DialogParameters dialogParams = DialogParameters.createBuilder().
    setDateTitleStyle(R.style.CustomText_Medium).
    build();
    builder.setDialogParameters(dialogParams);

Table explaining each dialog parameter:

+-----------------------------+--------------------------------------------+
| Name                        | Purpose                                    |
+=============================+============================================+
| dialogTheme                 | Theme for Dialogs, i.e. message and date   |
|                             | dialogs                                    |
+-----------------------------+--------------------------------------------+
| dateTitleStyle              | TextAppearance of title in DateDialog      |
+-----------------------------+--------------------------------------------+
| dateSubtitleStyle           | TextAppearance of subtitle in DateDialog   |
+-----------------------------+--------------------------------------------+
| messageTitleStyle           | TextAppearance of title in MessageDialog   |
+-----------------------------+--------------------------------------------+
| messageDetailsStyle         | TextAppearance of message in MessageDialog |
+-----------------------------+--------------------------------------------+
| messageDetailsNoTitleStyle  | TextAppearance of message in MessageDialog |
|                             | without title                              |
+-----------------------------+--------------------------------------------+
| buttonLabelStyle            | TextAppearance of action button for Date   |
|                             | and MessageDialogs                         |
+-----------------------------+--------------------------------------------+
| imageLabelStyle             | TextAppearance of the image prefix &       |
|                             | suffix labels in MessageDialog             |
+-----------------------------+--------------------------------------------+
| snackbarTextStyle           | TextAppearance of the text label inside a  |
|                             | Snackbar                                   |
+-----------------------------+--------------------------------------------+

ProgressParameters
~~~~~~~~~~~~~~~~~~

The ProgressParameters in the PaymentTheme hold parameters to theme
progress animations shown when loading lists or sending charge/preset requests
to the Payment API.

Code sample how to set the ProgressParameters in the PaymentTheme:

.. code-block:: java

    ProgressParameters progressParams = ProgressParameters.createBuilder().
    setLoadProgressBarColor(R.color.customColorPrimary).
    build();
    builder.setProgressParameters(progressParams);

Table explaining each progress parameter:

+---------------------------+--------------------------------------------+
| Name                      | Purpose                                    |   
+===========================+============================================+
| loadBackground            | Background resource ID of the loading page |
+---------------------------+--------------------------------------------+
| loadProgressBarColor      | Indeterminate ProgressBar color resource   |
|                           | ID                                         | 
+---------------------------+--------------------------------------------+
| sendBackground            | Background resource ID of the loading page |
+---------------------------+--------------------------------------------+
| sendProgressBarColorFront | Determinate ProgressBar front color        |
|                           | resource ID                                | 
+---------------------------+--------------------------------------------+
| sendProgressBarColorBack  | Determinate ProgressBar back color         |
|                           | resource ID                                | 
+---------------------------+--------------------------------------------+
| headerStyle               | TextAppearance of header in the send       |
|                           | progress screen                            | 
+---------------------------+--------------------------------------------+
| infoStyle                 | TextAppearance of info in the send         |
|                           | progress screen                            | 
+---------------------------+--------------------------------------------+

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

By default the Android SDK groups together payment methods VISA, Mastercard and AMEX into one card. Removing this default group is done by initializing the Android SDK with a group json file containing an empty array.

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
