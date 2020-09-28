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
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The LocalizationService providing asynchronize loading of the localizations needed for presenting the list of payment networks and showing errors.
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class LocalizationLoaderService {
    private final LocalizationConnection connection;
    private LocalizationLoaderListener listener;
    private WorkerTask<Localization> task;

    /** Memory cache of localizations */
    private static LocalizationCache cache = new LocalizationCache();

    /**
     * Create a new LocalizationService, this service is used to load the localizations.
     */
    public LocalizationLoaderService() {
        this.connection = new LocalizationConnection();
    }

    /**
     * Set the localization listener which will be informed about the state of the localizations being loaded.
     *
     * @param listener to be informed about the localizations being loaded
     */
    public void setListener(LocalizationLoaderListener listener) {
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
     * @param session the payment session containing networks for which localizations should be loaded
     */
    public void loadLocalizations(final Context context, final PaymentSession session) {
        if (task != null) {
            throw new IllegalStateException("Already loading localizations, stop first");
        }
        task = WorkerTask.fromCallable(new Callable<Localization>() {
            @Override
            public Localization call() throws PaymentException {
                return asyncLoadLocalizations(context, session);
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

    private Localization asyncLoadLocalizations(Context context, PaymentSession session)
        throws PaymentException {
        String listUrl = session.getListUrl();
        if (!listUrl.equals(cache.getCacheId())) {
            cache.clear();
            cache.setCacheId(listUrl);
        }
        LocalizationHolder localHolder = new LocalLocalizationHolder(context);
        LocalizationHolder sharedHolder = loadLocalizationHolder(session.getLink("lang"), localHolder);
        
        Map<String, LocalizationHolder> holders = new HashMap<>();
        for (PaymentNetwork network : session.getPaymentNetworks()) {
            holders.put(network.getCode(), loadLocalizationHolder(network.getLink("lang"), sharedHolder));
        }

        List<AccountCard> accounts = session.getAccountCards();
        for (AccountCard account : session.getAccountCards()) {
            holders.put(account.getCode(), loadLocalizationHolder(account.getLink("lang"), sharedHolder));
        }
        return new Localization(sharedHolder, holders);
    }

    private LocalizationHolder loadLocalizationHolder(URL url, LocalizationHolder fallback) throws PaymentException {
        String langUrl = url.toString();
        LocalizationHolder holder = cache.get(langUrl);

        if (holder == null) {
            holder = new MultiLocalizationHolder(connection.loadLocalization(url), fallback);
            cache.put(langUrl, holder);
        }
        return holder;
    }
}
