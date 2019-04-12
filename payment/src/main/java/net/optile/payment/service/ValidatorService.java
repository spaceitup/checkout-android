/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.service;

import java.util.concurrent.Callable;

import android.content.res.Resources;
import net.optile.payment.core.PaymentException;
import net.optile.payment.core.WorkerSubscriber;
import net.optile.payment.core.WorkerTask;
import net.optile.payment.core.Workers;
import net.optile.payment.resource.ResourceLoader;
import net.optile.payment.validation.Validator;

/**
 * The ValidatorService providing asynchronize initializing of the Validator.
 * This service makes callbacks in the ValidatorListener class to notify of request completions.
 */
public final class ValidatorService {

    private WorkerTask<Validator> validatorTask;
    private ValidatorListener listener;

    /**
     * Create a new empty ValidatorService
     */
    public ValidatorService() {
    }

    /**
     * Stop and unsubscribe from tasks that are currently active in this service.
     */
    public void stop() {

        if (validatorTask != null) {
            validatorTask.unsubscribe();
            validatorTask = null;
        }
    }

    /**
     * Set the listener in this validator service.
     *
     * @param listener to be notified when the loading is completed or has failed.
     */
    public void setListener(ValidatorListener listener) {
        this.listener = listener;
    }

    /**
     * Is the service active
     *
     * @return true when active, false otherwise
     */
    public boolean isActive() {
        return validatorTask != null;
    }

    /**
     * Load the validator given the Android resources and validation settings file
     *
     * @param resources the Adroid resources
     * @param validationResId resource id of the validation settings file
     */
    public void loadValidator(final Resources resources, final int validationResId) {

        if (validatorTask != null) {
            throw new IllegalStateException("Already loading validator, stop first");
        }
        validatorTask = WorkerTask.fromCallable(new Callable<Validator>() {
            @Override
            public Validator call() throws PaymentException {
                return asyncLoadValidator(resources, validationResId);
            }
        });
        validatorTask.subscribe(new WorkerSubscriber<Validator>() {
            @Override
            public void onSuccess(Validator validator) {
                validatorTask = null;

                if (listener != null) {
                    listener.onValidatorSuccess(validator);
                }
            }

            @Override
            public void onError(Throwable cause) {
                validatorTask = null;

                if (listener != null) {
                    listener.onValidatorError(cause);
                }
            }
        });
        Workers.getInstance().forNetworkTasks().execute(validatorTask);
    }

    /**
     * Load the Validator in the background using the validation settings file.
     *
     * @param validationResId resource id of the validation settings file
     * @param res the Android resources
     * @return the validator
     */
    private Validator asyncLoadValidator(Resources res, int validationResId) throws PaymentException {
        //int validationResId = PaymentUI.getInstance().getValidationResId();
        //Resources res = presenter.getContext().getResources();
        return new Validator(ResourceLoader.loadValidations(res, validationResId));
    }

    /**
     * ValidatorService Listener interface
     */
    public static interface ValidatorListener {

        /**
         * Called when the validator was successfully loaded
         *
         * @param validator the loaded validator
         */
        public void onValidatorSuccess(Validator validator);

        /**
         * Called when an error occured in this service
         *
         * @param cause the reason why it failed
         */
        public void onValidatorError(Throwable cause);
    }
}
