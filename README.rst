Introduction
============

The optile Android Payment SDK makes it easy to integrate with optile
and provides a great looking payment experience in your Android app. The
SDK comes with a ready made, easy to use Payment Page which takes care
of showing supported payment methods and handling payments. The SDK also
provide low-level packages that can be used to build your own custom
payment experience in your app.

Supported Features
==================

Android Version
---------------

Android API versions 19 - 28 (Kitkat 4.4 - Pie 9.0) are supported by the
Android Payment SDK. TLS1.2 is enabled for Android version 19 (Kitkat).

Payment Methods
---------------

All “direct” payment methods are supported, this includes Credit, Debit
cards, Sepa. Payments that require “redirects” (external WebView) like
Paypal, Sofort are not supported by this SDK. The option “presetFirst”
is also supported and gives the opportunity to show a summary page
before the actual Charge.

Integration Scenario
--------------------

The SDK requires payment sessions created using the DISPLAY_NATIVE
integration scenario. Below is a sample list request object that can be
used to create a payment session that is supported by the Android
Payment SDK.

Example list request Json body:

::

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

Registration
------------

There are two kind of registrations: Regular and Recurring. Both types
are supported and depending on registration settings in the List Result
a checkbox may show for either type of registration. Please see
documentation at optile.io for more information about the registration
types.

Make your first payment
=======================

In order to make a successful payment you must complete the following
steps:

1. Create a payment session on your server and retrieve the list URL in
   your app
2. Install Payment SDK in your app
3. Initialise and show the Payment Page with the list URL

1 - Create payment session
--------------------------

The documentation at optile.io will guide you through optile’s Open
Payment Gateway (OPG) features for frontend checkout and backend use
cases. It provides important information about integration scenarios,
testing possibilities, and references. The documentation will help you
create a payment session that can be used by the Android Payment SDK.

After you have created a payment session on your server you will receive
a response containing the List Result in Json format. This List Result
contains a “self” URL which is used to initialise the Payment Page.

Part of the list result containing the “self” URL:

::

   {
     "links": {
       "self": "https://api.integration.oscato.com/pci/v1/5c17b47e7862056fa0755e66lrui4dvavak9ehlvh4n3abcde9",
       "customer": "https://api.integration.oscato.com/api/customers/123456789862053ccf15479eu"
     },
     "timestamp": "2018-12-17T14:36:46.105+0000",
     "operation": "LIST",
     ...

2 - Install Payment SDK
-----------------------

Installing the Payment SDK is easy and requires only adding the optile
Payment SDK module to your build.gradle file. Note: the Android SDK is
currently only available through optile internal Nexus repository.

::

   implementation "com.oscato.mobile:android-payment-sdk:1.1.0"

3 - Show Payment Page
---------------------

The Payment SDK provides a class called PaymentUI which is used to
initialise and launch the Payment Page.

Code sample how to initialise and display the Payment Page:

::

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

Payment results are returned through the onActivityResult() method in
your Activity.

Code sample how to obtain the PaymentResult inside the
onActivityResult() method:

::

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {

       if (requestCode != PAYMENT_REQUEST_CODE) {
           return;
       }
       PaymentResult result = null;

       if (data != null && data.hasExtra(PaymentUI.EXTRA_PAYMENT_RESULT)) {
           result = data.getParcelableExtra(PaymentUI.EXTRA_PAYMENT_RESULT);
       }
       if (resultCode == PaymentUI.RESULT_CODE_OK) {
           // Charge request has been made and "result" contains
           // an Interaction and optional OperationResult describing the Charge result
       } 
       if (resultCode == PaymentUI.RESULT_CODE_CANCELLED) {
           // 1. "result" is null if user closed the payment page without making a charge request. 
           // 2. "result" contains an Interaction and optional OperationResult. 
       }
       if (resultCode == PaymentUI.RESULT_CODE_ERROR) {
           // "result" contains a PaymentError explaining the error that occurred i.e. connection error.
       }
   }

Customise Payment Page
======================

The look & feel of the Payment Page may be customised, i.e. colors, font
style and icons can be changed so that it matches the look & feel of the
mobile app.

Page Orientation
----------------

By default the orientation of the Payment Page will be locked based on
the orientation in which the Payment Page was opened. I.e. if the mobile
app is shown in landscape mode the Payment Page will also be opened in
landscape mode but cannot be changed anymore by rotating the phone.

Code sample how to set the fixed orientation mode:

::

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
order for theming to take effect, the customised PaymentTheme instance
must be set in the PaymentUI class prior to opening the Payment Page.

Code sample how to create and set a custom PaymentTheme:

::

   PaymentTheme.Builder builder = PaymentTheme.createBuilder();
   ...  
   PaymentUI paymentUI = PaymentUI.getInstance();
   paymentUI.setPaymentTheme(builder.build());
   paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

The PaymentTheme contains a set of parameters defining the customised
theming. When a parameter name ends with Style, the parameter holds a
TextAppearance style resource id used for TextView elements. If the
parameter name ends with Theme then the parameter holds a theme resource
id and is applied during inflation of the UI element.

PageParameters
--------------

The PageParameters class contains a collection of parameters used to
theme the page and list.

Code sample how to set the PageParameters in the PaymentTheme:

::

   PageParameters pageParams = PageParameters.createBuilder().
   setPageTheme(R.style.CustomThemePaymentPage).
   ...
   build();
   builder.setPageParameters(pageParams);

Table explaining each page parameter:

+-------------------------+--------------------------------------------+
| Name                    | Purpose                                    |
+=========================+============================================+
| pageTheme               | Main theme of the Payment Page Activity.   |
+-------------------------+--------------------------------------------+
| emptyListLabelStyle     | TextAppearance for label shown when the    |
|                         | list of payment methods is empty           |
+-------------------------+--------------------------------------------+
| sectionHeaderLabelStyle | TextAppearance for section header label in |
|                         | the list, i.e. “Saved accounts”            |
+-------------------------+--------------------------------------------+
| networkCardTitleStyle   | TextAppearance for network card title,     |
|                         | i.e. “Visa”                                |
+-------------------------+--------------------------------------------+
| accountCardTitleStyle   | TextAppearance for account card title,     |
|                         | i.e. “41 \**\* 1111”                       |
+-------------------------+--------------------------------------------+
| accountCardSubtitleStyl | TextAppearance for account card subtitle,  |
| e                       | i.e. the expiry date “01 / 2032”           |
+-------------------------+--------------------------------------------+
| paymentLogoBackground   | Background resource ID drawn behind        |
|                         | payment method images                      |
+-------------------------+--------------------------------------------+

WidgetParameters
----------------

The WidgetParameters contains a collection of parameters used to theme
widgets. Widgets are UI elements handling user input, i.e. TextInput,
CheckBoxes and buttons. Below is a table explaining each parameter.

The WidgetParameters class allow setting individual drawable resource
ids for icons by using the putInputTypeIcon() method, use the
setDefaultIconMapping() method to use the icons provided by the Payment
SDK.

Code sample how to set the WidgetParameters in the PaymentTheme:

::

   WidgetParameters widgetParams = WidgetParameters.createBuilder().
   setTextInputTheme(R.style.CustomThemeTextInput).
   ...
   build();
   builder.setWidgetParameters(widgetParams);

Table explaining each widget parameter:

+-------------------------+--------------------------------------------+
| Name                    | Purpose                                    |
+=========================+============================================+
| textInputTheme          | Theme for TextInputLayout elements         |
+-------------------------+--------------------------------------------+
| buttonTheme             | Theme for action button in each payment    |
|                         | card                                       |
+-------------------------+--------------------------------------------+
| buttonLabelStyle        | TextAppearance of label inside the action  |
|                         | button                                     |
+-------------------------+--------------------------------------------+
| buttonBackground        | Background resource ID of action button    |
+-------------------------+--------------------------------------------+
| checkBoxTheme           | Theme for checkBox UI element              |
+-------------------------+--------------------------------------------+
| checkBoxLabelCheckedSty | TextAppearance of label when checkBox is   |
| le                      | checked                                    |
+-------------------------+--------------------------------------------+
| checkBoxLabelUncheckedS | TextAppearance of label when checkBox is   |
| tyle                    | unchecked                                  |
+-------------------------+--------------------------------------------+
| selectLabelStyle        | TextAppearance of label shown above        |
|                         | SelectBox                                  |
+-------------------------+--------------------------------------------+
| validationColorOk       | Color resource ID indicating successful    |
|                         | validation state                           |
+-------------------------+--------------------------------------------+
| validationColorUnknown  | Color resource ID indicating unknown       |
|                         | validation state                           |
+-------------------------+--------------------------------------------+
| validationColorError    | Color resource ID indicating error         |
|                         | validation state                           |
+-------------------------+--------------------------------------------+
| hintDrawable            | Drawable resource ID of the hint icon for  |
|                         | verification codes                         |
+-------------------------+--------------------------------------------+

DialogParameters
----------------

The DialogParameters in the PaymentTheme holds parameters to theme popup
dialog windows. The Payment SDK contain two different dialogs, the
DateDialog for setting expiry dates and MessageDialog to show warning
and errors.

Code sample how to set the DialogParameters in the PaymentTheme:

::

   DialogParameters dialogParams = DialogParameters.createBuilder().
   setDateTitleStyle(R.style.CustomText_Medium).
   ...
   build();
   builder.setDialogParameters(dialogParams);

Table explaining each dialog parameter:

+-------------------------+--------------------------------------------+
| Name                    | Purpose                                    |
+=========================+============================================+
| dialogTheme             | Theme for Dialogs, i.e. message and date   |
|                         | dialogs                                    |
+-------------------------+--------------------------------------------+
| dateTitleStyle          | TextAppearance of title in DateDialog      |
+-------------------------+--------------------------------------------+
| dateSubtitleStyle       | TextAppearance of subtitle in DateDialog   |
+-------------------------+--------------------------------------------+
| messageTitleStyle       | TextAppearance of title in MessageDialog   |
+-------------------------+--------------------------------------------+
| messageDetailsStyle     | TextAppearance of message in MessageDialog |
+-------------------------+--------------------------------------------+
| messageDetailsNoTitleSt | TextAppearance of message MessageDialog    |
| yle                     | without a title                            |
+-------------------------+--------------------------------------------+
| buttonLabelStyle        | TextAppearance of action button for Date   |
|                         | and MessageDialogs                         |
+-------------------------+--------------------------------------------+
| imageLabelStyle         | TextAppearance of the image prefix &       |
|                         | suffix labels in MessageDialog             |
+-------------------------+--------------------------------------------+
| snackbarTextStyle       | TextAppearance of the text label inside a  |
|                         | Snackbar                                   |
+-------------------------+--------------------------------------------+

ProgressParameters
------------------

The ProgressParameters in the PaymentTheme hold parameters to theme
progress animations shown when loading lists or sending charge requests
to the Payment API.

Code sample how to set the ProgressParameters in the PaymentTheme:

::

   ProgressParameters progressParams = ProgressParameters.createBuilder().
   setLoadProgressBarColor(R.color.customColorPrimary).
   ...
   build();
   builder.setProgressParameters(progressParams);

Table explaining each progress parameter:

========================= ====================================================
Name                      Purpose
========================= ====================================================
loadBackground            Background resource ID of the loading page
loadProgressBarColor      Indeterminate ProgressBar color resource ID
sendBackground            Background resource ID of the loading page
sendProgressBarColorFront Determinate ProgressBar front color resource ID
sendProgressBarColorBack  Determinate ProgressBar back color resource ID
headerStyle               TextAppearance of header in the send progress screen
infoStyle                 TextAppearance of info in the send progress screen
========================= ====================================================

Grouping of Payment Methods
===========================

The Android Payment SDK supports grouping of payment methods within a
card in the payment page. By default the SDK supports one group which
contains the payment methods Visa, Mastercard and American Express.

Customise grouping
------------------

The SDK allow customisation of which payment methods are grouped
together in a card. Customisation is done by setting the resource ID of
a grouping Json settings file in the SDK prior to showing the payment
page. Payment methods can only be grouped together in a card when they
contain the same set of InputElements. If InputElements of grouped
Payment Methods differ then each Payment Method will be shown in its own
card in the payment page. The following example shows how to create two
groups, first group contains Mastercard and Amex and the second group
contains Visa and Visa Electron.

Example customgroups.json file:

::

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

::

   PaymentUI paymentUI = PaymentUI.getInstance();
   paymentUI.setGroupResId(R.raw.customgroups);
   paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Disable grouping
----------------

If all payment methods should be shown in their own cards then this can
be achieved by providing a grouping Json settings file with an empty
array.

Example disablegroups.json file:

::

   []

Code sample how to set the disabledgroups.json file:

::

   PaymentUI paymentUI = PaymentUI.getInstance();
   paymentUI.setGroupResId(R.raw.disablegroups);
   paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);

Smart Selection
===============

The choice which payment method in a group is displayed and used for
charge requests is done by “Smart Selection”. Each payment method in a
group contains a Regular Expression that is used to “smart select” this
method based on the partially entered credit/debit number. While the
user types the number, the SDK will validate the partial number with the
regular expressions. When one or more payment methods match the number
input they will be highlighted and displayed.

Table containing the rules of Smart Selection:

+-------------------------+--------------------------------------------+
| Name                    | Purpose                                    |
+=========================+============================================+
| No payment method regex | The first payment method in the group is   |
| match the number input. | displayed and is used to validate other    |
|                         | input values and perform Charge requests.  |
+-------------------------+--------------------------------------------+
| Two or more payment     | The first matching payment method is       |
| method regex match the  | displayed and is used to validate other    |
| number input.           | input values and perform Charge requests.  |
+-------------------------+--------------------------------------------+
| One payment method      | This payment method is displayed and is    |
| regex match the number  | used to validate other input values and    |
| input.                  | perform Charge requests.                   |
+-------------------------+--------------------------------------------+

Input Validation
================

Input Type Validations
----------------------

Before a charge request is made, each input value provided by the user
is validated.

Table containing the validations used for each input type:

+-----------------------------------+-----------------------------------+
| Input Type                        | Validation                        |
+===================================+===================================+
| holderName                        | Valid if not empty                |
+-----------------------------------+-----------------------------------+
| accountNumber                     | **Debit & Credit cards** .        |
|                                   | 1. Custom validation (see table   |
|                                   | below), if not set then default   |
|                                   | regex is used.2. Luhn algorithm   |
|                                   | is applied\ **Default**\ Regex:   |
|                                   | ``^[0-9]+$``                      |
+-----------------------------------+-----------------------------------+
| verificationCode                  | **Debit & Credit cards**\ Custom  |
|                                   | validation (see table below), if  |
|                                   | not set then default regex is     |
|                                   | used.\ **Default**\ Regex:        |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+
| expiryMonth                       | Regex: ``(^0[1-9]\|1[0-2]$)``     |
+-----------------------------------+-----------------------------------+
| expiryYear                        | Regex: ``^(20)\\d{2}$``           |
+-----------------------------------+-----------------------------------+
| expiryDate                        | Month regex:                      |
|                                   | ``(^0[1-9]\|1[0-2]$)``\ Year      |
|                                   | regex: ``^(20)\\d{2}$``\ Month    |
|                                   | and Year combined must be same or |
|                                   | later than current date.          |
+-----------------------------------+-----------------------------------+
| bankCode                          | Valid if not empty                |
+-----------------------------------+-----------------------------------+
| iban                              | Standard Iban validation          |
+-----------------------------------+-----------------------------------+
| bic                               | Regex:                            |
|                                   | ``([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0 |
|                                   | -9]{2}([a-zA-Z0-9]{3})?)``        |
+-----------------------------------+-----------------------------------+

Debit & Credit card validations
-------------------------------

The Payment SDK uses customised validations for certain Credit & Debit
cards.

Table containing the list of validations used for each card type:

+-----------------------------------+-----------------------------------+
| Card Code                         | Regex                             |
+===================================+===================================+
| AMEX                              | number: ^3[47][0-9]{13}$          | 
|                                   |                                   |
|                                   | verificationCode:: ^[0-9]{4}$     |
+-----------------------------------+-----------------------------------+
| CASTORAMA                         | number: ``[1-9]{1}[0-9]{15,18}$`` |
|                                   | verificationCode:``^[0-9]{4}$``   |
+-----------------------------------+-----------------------------------+
| DINERS                            | number:                           |
|                                   | ``^3(?:0[0-5]\|[689][0-9])[0-9]{1 |
|                                   | 1}$``\ verificationCode:          |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| DISCOVER                          | number:                           |
|                                   | ``^(?:6011\|622[1-9]\|64[4-9][0-9 |
|                                   | ]\|65[0-9]{2})[0-9]{12}$``\ verif |
|                                   | icationCode:                      |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| MASTERCARD                        | number:                           |
|                                   | ``^5[1-5][0-9]{14}\|(222[1-9]\|22 |
|                                   | [3-9][0-9]\|2[3-6][0-9]{2}\|27[01 |
|                                   | ][0-9]\|2720)[0-9]{12}$``\ verifi |
|                                   | cationCode:                       |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| UNIONPAY                          | number:                           |
|                                   | ``^62[0-5][0-9]{13,16}$``\ verifi |
|                                   | cationCode:                       |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| VISA                              | number:                           |
|                                   | ``^4(?:[0-9]{12}\|[0-9]{15}\|[0-9 |
|                                   | ]{18})$``\ verificationCode:      |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| VISA_DANKORT                      | number:                           |
|                                   | ``^4(?:[0-9]{12}\|[0-9]{15})$``\  |
|                                   | verificationCode:                 |
|                                   | ``^[0-9]{3}$``                    |
+-----------------------------------+-----------------------------------+
| VISAELECTRON                      | number: ``^4[0-9]{15}$``          |
+-----------------------------------+-----------------------------------+
| CARTEBANCAIRE                     | number:                           |
|                                   | ``^(2\|[4-6])[0-9]{10,16}``\ veri |
|                                   | ficationCode:                     |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+
| CARTEBLEUE                        | number:                           |
|                                   | ``^(50\|59\|6[0-9])[0-9]{10,17}`` |
|                                   | \ verificationCode:               |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+
| MAESTRO                           | number:                           |
|                                   | ``^(50\|59\|6[0-9])[0-9]{10,17}`` |
|                                   | \ verificationCode:               |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+
| MAESTROUK                         | number:                           |
|                                   | ``^(50\|59\|6[0-9])[0-9]{10,17}`` |
|                                   | \ verificationCode:               |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+
| POSTEPAY                          | number:                           |
|                                   | ``^(50\|59\|6[0-9])[0-9]{10,17}`` |
|                                   | \ verificationCode:               |
|                                   | ``^[0-9]*$``                      |
+-----------------------------------+-----------------------------------+

Customise validations
---------------------

The Payment SDK allow limited customisation of validations applied to
input values. The validation for debit and credit card numbers and
verificationCodes can only be customised. Customized validation is
enabled by providing the resource ID of the validation Json file to the
PaymentUI class prior to showing the payment page. The default
validation provided by the Android Payment SDK are sufficient in most
cases.

Example customvalidations.json file:

::

   [{
       "code": "VISA",
       "items": [
           {
               "type": "number",
               "regex": "^4(?:[0-9]{12}|[0-9]{15}|[0-9]{18})$"
           },
           {
               "type": "verificationCode",
               "regex": "^[0-9]{3}$"
           }
       ]
   },
   ...
   ]

Code sample how to set the customvalidations.json file:

::

   PaymentUI paymentUI = PaymentUI.getInstance();
   paymentUI.setValidationResId(R.raw.customvalidations);
   paymentUI.showPaymentPage(this, PAYMENT_REQUEST_CODE);
