/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
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
