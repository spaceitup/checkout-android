/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.shared;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for holding the DemoSettings of this app
 */
public final class DemoSettings implements Parcelable {

    public final static Parcelable.Creator<DemoSettings> CREATOR = new Parcelable.Creator<DemoSettings>() {

        public DemoSettings createFromParcel(Parcel in) {
            return new DemoSettings(in);
        }

        public DemoSettings[] newArray(int size) {
            return new DemoSettings[size];
        }
    };

    private boolean customTheme;
    private boolean registered;
    private boolean summary;

    /**
     * Construct a new DemoSettings object
     *
     * @param customTheme if true then use the custom theme else use the default payment theme
     * @param registered if true when use the user registered account
     * @param summary if true use the summary page flow
     */
    public DemoSettings(boolean customTheme, boolean registered, boolean summary) {
        this.customTheme = customTheme;
        this.registered = registered;
        this.summary = summary;
    }

    private DemoSettings() {
    }

    private DemoSettings(Parcel in) {
        this.customTheme = in.readInt() > 0;
        this.registered = in.readInt() > 0;
        this.summary = in.readInt() > 0;
    }

    public boolean getCustomTheme() {
        return customTheme;
    }

    public boolean getRegistered() {
        return registered;
    }

    public boolean getSummary() {
        return summary;
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
        out.writeInt(customTheme ? 1 : 0);
        out.writeInt(registered ? 1 : 0);
        out.writeInt(summary ? 1 : 0);
    }
}
