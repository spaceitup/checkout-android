package net.optile.payment.localization;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Copyright(c) 2012-2020 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
public class LocalizationCacheTest {

    @Test
    public void setCacheId() {
        LocalizationCache cache = new LocalizationCache();
        String cacheId = "cacheId";
        cache.setCacheId(cacheId);
        assertEquals(cacheId, cache.getCacheId());
    }

    @Test
    public void clear() {
        LocalizationCache cache = new LocalizationCache();
        String cacheId = "cacheId";
        cache.setCacheId(cacheId);

        LocalizationHolder holder = LocalizationTest.createPropLocalizationHolder("key", "value", 5);
        String holderKey = "holder";
        cache.put(holderKey, holder);

        assertEquals(cacheId, cache.getCacheId());
        assertNotNull(cache.get(holderKey));

        cache.clear();
        assertNull(cache.getCacheId());
        assertNull(cache.get(holderKey));
    }
}
