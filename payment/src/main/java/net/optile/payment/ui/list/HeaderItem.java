/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

/**
 * Class representing a header in the PaymentList
 */
final class HeaderItem extends ListItem {

    final String title;

    HeaderItem(int viewType, String title) {
        super(viewType);
        this.title = title;
    }
}
