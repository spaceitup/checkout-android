/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.redirect;

/**
 * Class containing the styling of the Redirect page
 */

public final class RedirectStyles {

    private String titleLabel;

    private String buttonLabel;

    public RedirectStyles() {
    }
    
    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }
}
