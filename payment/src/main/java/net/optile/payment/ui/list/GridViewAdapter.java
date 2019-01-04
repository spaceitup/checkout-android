/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.list;

import java.util.List;

import net.optile.payment.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.net.URL;
import java.util.Locale;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.ImageHelper;
import net.optile.payment.util.PaymentUtils;

/**
 *
 *
 *
 */
class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<URL> items;

    public GridViewAdapter(Context context, List<URL> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        URL url = items.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            imageView = (ImageView) inflater.inflate(R.layout.list_item_logo, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }
        ImageHelper.getInstance().loadImage(imageView, url);
        //PaymentUtils.setImageBackground(imageView, asset);
        return imageView;
    }
}
