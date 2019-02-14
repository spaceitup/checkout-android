/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import java.util.List;

/**
 * Form input element description.
 */
public class InputElement {
    /** name */
    private String name;
    /** type */
    private String type;
    /** localized label */
    private String label;
    /** options */
    private List<SelectOption> options;

    /**
     * Gets name.
     *
     * @return A name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name A name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return A type value.
     */
    @InputElementType.Definition
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type A type value.
     */
    public void setType(@InputElementType.Definition String type) {
        this.type = type;
    }

    /**
     * Gets localized label.
     *
     * @return A localized label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets localized label.
     *
     * @param label A localized label.
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Gets options.
     *
     * @return Non -empty list of options.
     */
    public List<SelectOption> getOptions() {
        return options;
    }

    /**
     * Sets options.
     *
     * @param options Non-empty list of options.
     */
    public void setOptions(final List<SelectOption> options) {
        this.options = options;
    }
}
