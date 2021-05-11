/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.model;

import java.net.URL;

/**
 * The Preset list section
 */
public final class PresetSection extends ListSection {

    private final PresetCard presetCard;

    /**
     * Construct a new PresetSection
     *
     * @param labelKey localization key of this section
     * @param presetCard
     */
    public PresetSection(String labelKey, PresetCard presetCard) {
        super(labelKey);
        this.presetCard = presetCard;
    }

    /**
     * Get the presetCard from this section
     *
     * @return the presetCard
     */
    public PresetCard getPresetCard() {
        return presetCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsLink(String name, URL url) {
        return presetCard.containsLink(name, url);
    }
}
