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

package net.optile.payment.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.view.LayoutInflater;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import net.optile.payment.R;
import net.optile.payment.core.LanguageFile;
import net.optile.payment.core.PaymentInputType;
import net.optile.payment.model.InputElement;
import net.optile.payment.model.InputElementType;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.ui.theme.IconParameters;
import net.optile.payment.ui.theme.ButtonWidgetParameters;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.widget.ButtonWidget;
import net.optile.payment.ui.widget.CheckBoxWidget;
import net.optile.payment.ui.widget.DateWidget;
import net.optile.payment.ui.widget.FormWidget;
import net.optile.payment.ui.widget.SelectWidget;
import net.optile.payment.ui.widget.TextInputWidget;
import net.optile.payment.ui.widget.WidgetPresenter;
import net.optile.payment.util.PaymentUtils;
import net.optile.payment.validation.ValidationResult;
import android.view.ContextThemeWrapper;

/**
 * This class contains helper methods to inflate View from XML layout files.
 */
public final class InflaterUtils {

    /** 
     * Inflate the layout given the parent ViewGroup
     * 
     * @param parent ViewGroup in which this inflated view will be added
     * @param layoutResId layout resource id of the view that should be inflated
     * @return the inflated view 
     */
    public static View inflate(ViewGroup parent, int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(layoutResId, parent, false);
    }        

    /** 
     * Inflate the layout and attach the themed internal view
     * 
     * @param parent ViewGroup in which this inflated view will be added
     * @param layoutResId layout resource id of the view that should be inflated
     * @param viewLayoutResId internal view resource id that should be inflated with the theme 
     * @param viewThemeResId theme used to inflate the internval view element
     * @return the inflated view 
     */
    public static View inflateWithThemedView(ViewGroup parent, int layoutResId, int viewLayoutResId, int viewThemeResId) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResId, parent, false);
        ViewGroup group = view.findViewById(R.id.layout_viewholder);
        inflater = LayoutInflater.from(new ContextThemeWrapper(context, viewThemeResId));
        inflater.inflate(viewLayoutResId, group, true);
        return view;
    }
}
