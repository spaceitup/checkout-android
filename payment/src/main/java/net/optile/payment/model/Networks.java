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
