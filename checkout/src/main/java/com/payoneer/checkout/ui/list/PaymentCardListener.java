/*
 * Copyright (c) 2021 Payoneer Germany GmbH
 * https://payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.checkout.ui.list;

import java.util.List;
import java.util.Map;

import com.payoneer.checkout.ui.model.PaymentCard;
import com.payoneer.checkout.ui.page.PaymentListActivity;
import com.payoneer.checkout.ui.widget.FormWidget;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Implement this interface for listening to PaymentCard events.
 */
interface PaymentCardListener {

    /** 
     * 
     * 
     */
    void hideKeyboard();

    /** 
     * 
     * 
     * @param view 
     */
    void showKeyboard(View view);

    /** 
     * 
     * 
     * @param paymentCard 
     */
    void onDeleteClicked(PaymentCard paymentCard);

    /** 
     * 
     * 
     * @param networkCode 
     * @param type 
     */
    void onHintClicked(String networkCode, String type);

    /** 
     * 
     * 
     * @param paymentCard 
     * @param String 
     * @param widgets 
     */
    void onActionClicked(PaymentCard paymentCard, Map<String, FormWidget> widgets);

    /** 
     * 
     * 
     * @param position 
     */
    void onCardClicked(int position);
}
