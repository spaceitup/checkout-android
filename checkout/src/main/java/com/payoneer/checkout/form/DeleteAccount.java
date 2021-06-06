/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.form;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class holding DeleteAccount data
 */
public class DeleteAccount implements Parcelable {

    public final static Parcelable.Creator<DeleteAccount> CREATOR = new Parcelable.Creator<DeleteAccount>() {
        public DeleteAccount createFromParcel(Parcel in) {
            return new DeleteAccount(in);
        }

        public DeleteAccount[] newArray(int size) {
            return new DeleteAccount[size];
        }
    };

    public DeleteAccount() {
    }

    private DeleteAccount(Parcel in) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
    }
}
