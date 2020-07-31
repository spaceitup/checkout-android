/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.demo.shared;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import net.optile.example.demo.R;

/**
 * Base Activity for Activities used in this demo, it stores and retrieves the listUrl value.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final static String EXTRA_LISTURL = "listurl";
    public final static int PAYMENT_REQUEST_CODE = 1;
    public final static int EDIT_REQUEST_CODE = 2;

    protected boolean active;
    protected String listUrl;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;

        if (bundle != null) {
            this.listUrl = bundle.getString(EXTRA_LISTURL);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_LISTURL, listUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        this.active = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        this.active = true;
    }

    /**
     * Show error dialog to the user
     *
     * @param error the error message to be shown in the dialog
     */
    public void showErrorDialog(String error) {
        String message = String.format(getString(R.string.dialog_error_message), error);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.dialog_error_title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.dialog_error_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onErrorDialogClosed();
                }
            });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onErrorDialogClosed();
            }
        });
        builder.create().show();    
    }

    /**
     * Called when the error dialog has been closed by clicking a button or has been dismissed.
     * Activities extending from this BaseActivity should implement this method in order to receive this event.
     */
    public void onErrorDialogClosed() {
    }
}
