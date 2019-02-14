/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.util.Date;
import java.util.List;

/**
 * This class is designed to hold list of applicable and registered payment network descriptions.
 */
public class Networks {
    /** Simple API, always present */
    private List<ApplicableNetwork> applicable;
    /** Simple API, always present */
    private Date resourcesLastUpdate;

    /**
     * Gets value of applicable.
     *
     * @return the applicable.
     */
    public List<ApplicableNetwork> getApplicable() {
        return applicable;
    }

    /**
     * Sets value of applicable.
     *
     * @param applicable the applicable to set.
     */
    public void setApplicable(List<ApplicableNetwork> applicable) {
        this.applicable = applicable;
    }

    /**
     * Gets value of resourcesLastUpdate.
     *
     * @return the resourcesLastUpdate.
     */
    public Date getResourcesLastUpdate() {
        return resourcesLastUpdate;
    }

    /**
     * Sets value of resourcesLastUpdate.
     *
     * @param resourcesLastUpdate the resourcesLastUpdate to set.
     */
    public void setResourcesLastUpdate(Date resourcesLastUpdate) {
        this.resourcesLastUpdate = resourcesLastUpdate;
    }
}
