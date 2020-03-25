/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.localization.LocalLocalizationHolder;
import net.optile.payment.localization.Localization;
import net.optile.payment.localization.LocalizationCache;
import net.optile.payment.localization.LocalizationHolder;
import net.optile.payment.localization.MultiLocalizationHolder;
import net.optile.payment.network.LocalizationConnection;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The LocalizationService providing asynchronize loading of the localizations needed for presenting the list of payment networks and showing errors.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class LocalizationService {
    private final LocalizationConnection connection;
    private LocalizationListener listener;
    private WorkerTask<Localization> task;

    /** Memory cache of localizations */
    private static LocalizationCache cache = new LocalizationCache();

    /**
     * Create a new LocalizationService, this service is used to load the localizations.
     */
    public LocalizationService() {
        this.connection = new LocalizationConnection();
    }

    /**
     * Set the localization listener which will be informed about the state of the localizations being loaded.
     *
     * @param listener to be informed about the localizations being loaded
     */
    public void setListener(LocalizationListener listener) {
        this.listener = listener;
    }

    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {
        if (task != null) {
            task.unsubscribe();
            task = null;
        }
    }

    /**
     * Check if this service is currently active, i.e. is loading the localization files.
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return task != null && task.isSubscribed();
    }

    /**
     * Load all localizations for the payment session in this service
     *
     * @param context needed to load the local localization store
     * @param localization in which the localizations should be stored
     * @param session the payment session containing networks for which localizations should be loaded
     */
    public void loadLocalizations(final Context context, final Localization localization, final PaymentSession session) {
        if (task != null) {
            throw new IllegalStateException("Already loading localizations, stop first");
        }
        task = WorkerTask.fromCallable(new Callable<Localization>() {
            @Override
            public Localization call() throws PaymentException {
                return asyncLoadLocalizations(context, localization, session);
            }
        });
        task.subscribe(new WorkerSubscriber<Localization>() {
            @Override
            public void onSuccess(Localization loc) {
                task = null;
                if (listener != null) {
                    listener.onLocalizationSuccess(loc);
                }
            }

            @Override
            public void onError(Throwable cause) {
                task = null;
                if (listener != null) {
                    listener.onLocalizationError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(task);
    }

    private Localization asyncLoadLocalizations(Context context, Localization localization, PaymentSession session)
        throws PaymentException {
        String listUrl = session.getListUrl();
        if (!listUrl.equals(cache.getCacheId())) {
            cache.clear();
            cache.setCacheId(listUrl);
        }
        List<PaymentNetwork> networks = session.getPaymentNetworks();
        if (networks.size() == 0) {
            return localization;
        }
        LocalizationHolder shared = loadSharedLocalization(context, networks.get(0));
        Map<String, LocalizationHolder> holders = loadNetworkLocalizations(networks, shared);
        localization.setLocalizations(shared, holders);
        return localization;
    }

    private Map<String, LocalizationHolder> loadNetworkLocalizations(List<PaymentNetwork> networks, LocalizationHolder fallback)
        throws PaymentException {
        Map<String, LocalizationHolder> map = new HashMap<>();

        for (PaymentNetwork network : networks) {
            URL url = getNetworkLanguageUrl(network);
            String langUrl = url.toString();
            String code = network.getCode();
            LocalizationHolder holder = cache.get(langUrl);

            if (holder == null) {
                holder = new MultiLocalizationHolder(connection.loadLocalization(url), fallback);
                cache.put(langUrl, holder);
            }
            map.put(code, holder);
        }
        return map;
    }

    private LocalizationHolder loadSharedLocalization(Context context, PaymentNetwork network) throws PaymentException {
        try {
            URL url = getNetworkLanguageUrl(network);
            String sharedUrl = url.toString();
            int index = sharedUrl.lastIndexOf('/');

            if (index < 0 || !sharedUrl.endsWith(".properties")) {
                throw new PaymentException("Invalid URL for creating shared language URL");
            }
            sharedUrl = sharedUrl.substring(0, index) + "/paymentpage.properties";

            LocalizationHolder holder = cache.get(sharedUrl);
            if (holder == null) {
                holder = new MultiLocalizationHolder(connection.loadLocalization(new URL(sharedUrl)), new LocalLocalizationHolder(context));
                cache.put(sharedUrl, holder);
            }
            return holder;
        } catch (MalformedURLException e) {
            throw new PaymentException("Malformed paymentpage language URL", e);
        }
    }

    private URL getNetworkLanguageUrl(PaymentNetwork network) throws PaymentException {
        URL url = network.getLink("lang");
        if (url == null) {
            throw new PaymentException("Missing 'lang' url in PaymentNetwork: " + network.getCode());
        }
        return url;
    }
}
