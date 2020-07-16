/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import androidx.fragment.app.DialogFragment;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.validation.ValidationResult;

/**
 * Class acting as a presenter for the widgets hosted in the List adapter
 */
class CardWidgetPresenter implements WidgetPresenter {

    private final PaymentCardViewHolder holder;
    private final ListAdapter adapter;

    CardWidgetPresenter(PaymentCardViewHolder holder, ListAdapter adapter) {
        this.holder = holder;
        this.adapter = adapter;
    }

    @Override
    public void onActionClicked() {
        adapter.onActionClicked(holder.getAdapterPosition());
    }

    @Override
    public void onHintClicked(String type) {
        adapter.onHintClicked(holder.getAdapterPosition(), type);
    }

    @Override
    public void hideKeyboard() {
        adapter.hideKeyboard(holder.getAdapterPosition());
    }

    @Override
    public void showKeyboard() {
        adapter.showKeyboard(holder.getAdapterPosition());
    }

    @Override
    public int getMaxLength(String code, String type) {
        return adapter.getMaxLength(holder.getAdapterPosition(), code, type);
    }

    @Override
    public void showDialogFragment(DialogFragment dialog, String tag) {
        adapter.showDialogFragment(holder.getAdapterPosition(), dialog, tag);
    }

    @Override
    public ValidationResult validate(String type, String value1, String value2) {
        return adapter.validate(holder.getAdapterPosition(), type, value1, value2);
    }

    @Override
    public void onTextInputChanged(String type, String text) {
        adapter.onTextInputChanged(holder.getAdapterPosition(), type, text);
    }
}
