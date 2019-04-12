/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.service;

import java.util.List;
import java.util.concurrent.Callable;

import android.util.Log;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.model.ListResult;
import net.optile.payment.network.ListConnection;

/**
 * The ListService providing asynchronize loading of language files and list results from the Payment API .
 * This service makes callbacks in the listener to notify of request completions.
 */
public final class ListService {

    private final ListConnection listConnection;
    private ListListener listener;
    private WorkerTask<ListResult> listTask;
    private WorkerTask<List<LanguageFile>> langTask;

    /**
     * Create a new ListService, this service is used to load ListResult and language files from the Payment API
     */
    public ListService() {
        this.listConnection = new ListConnection();
    }

    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {

        if (listTask != null) {
            listTask.unsubscribe();
            listTask = null;
        }

        if (langTask != null) {
            langTask.unsubscribe();
            langTask = null;
        }
    }

    /**
     * Set the listener in this payment service.
     *
     * @param listener to be notified when the request is completed or has failed.
     */
    public void setListener(ListListener listener) {
        this.listener = listener;
    }

    /**
     * Is this ListService currently active
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return listTask != null || langTask != null;
    }

    /**
     * Load the ListResult from the Payment API
     *
     * @param listUrl containing the url pointing to the ListResult
     */
    public void loadListResult(final String listUrl) {

        if (listTask != null) {
            throw new IllegalStateException("Already loading ListResult, stop first");
        }
        listTask = WorkerTask.fromCallable(new Callable<ListResult>() {
            @Override
            public ListResult call() throws PaymentException {
                return asyncLoadListResult(listUrl);
            }
        });
        listTask.subscribe(new WorkerSubscriber<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                listTask = null;

                if (listener != null) {
                    listener.onListResultSuccess(listResult);
                }
            }

            @Override
            public void onError(Throwable cause) {
                Log.w("payment", cause);
                listTask = null;

                if (listener != null) {
                    listener.onListResultError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(listTask);
    }

    /**
     * Load language files from the Payment API
     *
     * @param files list of language files that should be loaded
     */
    public void loadLanguageFiles(final List<LanguageFile> files) {

        if (langTask != null) {
            throw new IllegalStateException("Already loading language files, stop first");
        }
        langTask = WorkerTask.fromCallable(new Callable<List<LanguageFile>>() {
            @Override
            public List<LanguageFile> call() throws PaymentException {
                return asyncLoadLanguageFiles(files);
            }
        });
        langTask.subscribe(new WorkerSubscriber<List<LanguageFile>>() {
            @Override
            public void onSuccess(List<LanguageFile> files) {
                langTask = null;

                if (listener != null) {
                    listener.onLanguageFilesSuccess(files);
                }
            }

            @Override
            public void onError(Throwable cause) {
                Log.w("ListService", cause);
                langTask = null;

                if (listener != null) {
                    listener.onLanguageFilesError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(langTask);
    }

    /**
     * Load the language files from the Payment API
     *
     * @param files the list of language files that should be loaded
     * @return the list of language files that have been loaded
     */
    private List<LanguageFile> asyncLoadLanguageFiles(List<LanguageFile> files) throws PaymentException {

        for (LanguageFile file : files) {
            listConnection.loadLanguageFile(file);
        }
        return files;
    }


    /**
     * Load the ListResult from the Payment API
     *
     * @param listUrl unique list url of the list
     * @return the ListResult obtained from the Payment API
     */
    private ListResult asyncLoadListResult(String listUrl) throws PaymentException {
        return listConnection.getListResult(listUrl);
    }

    /**
     * ListService Listener interface
     */
    public static interface ListListener {

        /**
         * Called when the ListResult was successfully loaded
         *
         * @param listResult the loaded ListResult
         */
        public void onListResultSuccess(ListResult listResult);

        /**
         * Called when an error occured while loading the ListResult
         *
         * @param cause the reason why it failed
         */
        public void onListResultError(Throwable cause);

        /**
         * Called when the language files are successfully loaded
         *
         * @param files the list of language files loaded
         */
        public void onLanguageFilesSuccess(List<LanguageFile> files);

        /**
         * Called when an error occured while loading the ListResult
         *
         * @param cause the reason why it failed
         */
        public void onLanguageFilesError(Throwable cause);
    }
}
