/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.model;

import java.util.Date;

/**
 * Customer data what should be used to pre-fill payment form.
 */
public class CustomerFormData {
    /** optional */
    private Date birthday;

    /**
     * Gets customer birthday.
     *
     * @return Customer birthday.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets customer birthday.
     *
     * @param birthday Customer's birthday.
     */
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
}
