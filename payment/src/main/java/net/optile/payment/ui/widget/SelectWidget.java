/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget;

import java.util.List;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Operation;
import net.optile.payment.model.SelectOption;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.WidgetParameters;
import net.optile.payment.util.PaymentUtils;

/**
 * Widget for handling the Select input type
 */
public final class SelectWidget extends FormWidget {

    private final Spinner spinner;
    private final TextView label;
    private final ArrayAdapter<SpinnerItem> adapter;

    /**
     * Construct a new SelectWidget
     *
     * @param name identifying this widget
     * @param rootView the root view of this input
     * @param theme PaymentTheme to apply
     */
    public SelectWidget(String name, View rootView, PaymentTheme theme) {
        super(name, rootView, theme);
        label = rootView.findViewById(R.id.input_label);
        WidgetParameters params = theme.getWidgetParameters();
        PaymentUtils.setTextAppearance(label, params.getSelectLabelStyle());
        adapter = new ArrayAdapter<>(rootView.getContext(), R.layout.spinner_item);

        spinner = rootView.findViewById(R.id.input_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                validate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(Operation operation) throws PaymentException {
        SpinnerItem selected = (SpinnerItem) spinner.getSelectedItem();

        if (selected != null) {
            operation.putValue(name, selected.value);
        }
    }

    public void setSelectOptions(List<SelectOption> options) {
        adapter.clear();

        if (options == null || options.size() == 0) {
            return;
        }
        int selIndex = 0;
        SelectOption option;

        for (int i = 0, e = options.size(); i < e; i++) {
            option = options.get(i);
            adapter.add(new SpinnerItem(option.getLabel(), option.getValue()));

            if (PaymentUtils.isTrue(option.getSelected())) {
                selIndex = i;
            }
        }
        spinner.setSelection(selIndex);
    }

    class SpinnerItem {
        final String label;
        final String value;

        SpinnerItem(String label, String value) {
            this.label = label;
            this.value = value;
        }

        @NonNull
        public String toString() {
            return label;
        }
    }
}
