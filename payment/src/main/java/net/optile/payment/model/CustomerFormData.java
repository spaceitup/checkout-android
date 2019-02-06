/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
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
