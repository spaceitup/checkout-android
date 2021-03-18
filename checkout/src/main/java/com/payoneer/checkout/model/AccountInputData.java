/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Input data what could been submitted by payment-page form.
 */
@Getter
@Setter
public class AccountInputData {
    /** Simple API, optional */
    private String holderName;
    /** Simple API, optional */
    private String number;
    /** Simple API, optional */
    private String bankCode;
    /** Simple API, optional */
    private String bankName;
    /** Simple API, optional */
    private String bic;
    /** Simple API, optional */
    private String branch;
    /** Simple API, optional */
    private String city;
    /** Simple API, optional */
    private String expiryMonth;
    /** Simple API, optional */
    private String expiryYear;
    /** Simple API, optional */
    private String iban;
    /** Simple API, optional */
    private String login;
    /** Simple API, optional */
    private Boolean optIn;
    /** Simple API, optional */
    private String password;
    /** Simple API, optional */
    private String verificationCode;

    /** day of customer's birthday */
    private String customerBirthDay;
    /** month of customer's birthday */
    private String customerBirthMonth;
    /** year of customer's birthday */
    private String customerBirthYear;

    /** id of installment plan */
    private String installmentPlanId;
}
