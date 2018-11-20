/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.widget;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.model.SelectOption;
import net.optile.payment.util.PaymentUtils;

/**
 * Widget for handling the Select input type
 */
public final class SelectInputWidget extends FormWidget {

    private final Spinner spinner;
    private final TextView label;
    private ArrayAdapter<SpinnerItem> adapter;

    /**
     * Construct a new SelectInputWidget
     *
     * @param name identifying this widget
     * @param rootView the root view of this input
     */
    public SelectInputWidget(String name, View rootView) {
        super(name, rootView);
        label = rootView.findViewById(R.id.input_label);
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

    public void putValue(Charge charge) throws PaymentException {
        SpinnerItem selected = (SpinnerItem) spinner.getSelectedItem();

        if (selected != null) {
            charge.putValue(name, selected.value);
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

        public String toString() {
            return label;
        }
    }
}
