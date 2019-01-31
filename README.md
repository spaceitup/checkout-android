# Introduction

The optile Android Payment SDK makes it easy to integrate with optile and provides a great looking payment experience in your Android app. The SDK comes with a ready made, easy to use Payment Page which takes care of showing supported payment methods and handling payments. The SDK also provide low-level packages that can be used to build your own custom payment experience in your app.

# Supported Features
Android Version
Android API versions 19 - 28 (Kitkat 4.4 - Pie 9.0) are supported by the Android Payment SDK. TLS1.2 is enabled for Android version 19 (Kitkat).

## Payment Methods
All "direct" payment methods are supported, this includes Credit, Debit cards, Sepa. Payments that require "redirects" (external WebView) like Paypal, Sofort are not supported by this SDK. The option "presetFirst" is also not supported and will be added later.

Integration Scenario
The SDK requires payment sessions created using the DISPLAY_NATIVE integration scenario. Below is a sample list request object that can be used to create a payment session that is supported by the Android Payment SDK. 

```
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
```

## Registration
There are 2 kinds of registration: Regular and Recurring. Both types are supported and depending on registration settings in the List Result a checkbox may be shown for each type of registration. Please see documentation at  optile.io for more information about both registration types.

