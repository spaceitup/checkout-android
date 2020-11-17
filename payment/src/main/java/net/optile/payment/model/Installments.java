/*
 * Copyright (c) 2020 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.util.List;

/**
 * Installments information.
 */
public class Installments {
    /** payment amount of original payment */
    private PaymentAmount originalPayment;
    /** installments plans */
    private List<InstallmentsPlan> plans;

    /**
     * Gets amount data of original payment.
     *
     * @return Payment amount data.
     */
    public PaymentAmount getOriginalPayment() {
        return originalPayment;
    }

    /**
     * Sets amount data of original payment.
     *
     * @param originalPayment Payment amount data.
     */
    public void setOriginalPayment(final PaymentAmount originalPayment) {
        this.originalPayment = originalPayment;
    }

    /**
     * Gets installments plans.
     *
     * @return Installments plans.
     */
    public List<InstallmentsPlan> getPlans() {
        return plans;
    }

    /**
     * Sets installments plans.
     *
     * @param plans Installments plans.
     */
    public void setPlans(final List<InstallmentsPlan> plans) {
        this.plans = plans;
    }
}
