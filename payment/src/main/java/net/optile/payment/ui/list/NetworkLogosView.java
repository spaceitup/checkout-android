/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.list;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher;
import net.optile.payment.R;
import net.optile.payment.ui.model.PaymentNetwork;
import net.optile.payment.util.ImageHelper;

/**
 * Class displaying the network logo images
 */
class NetworkLogosView {

    private final static float ALPHA_SELECTED = 1f;
    private final static float ALPHA_DESELECTED = 0f;
    private final static int ANIM_DURATION = 200;

    private final ViewSwitcher switcher;
    private final Map<String, NetworkLogo> logos;
    private final ImageView selImage;
    private final int margin;

    /**
     * Construct a new NetworkLogosView
     *
     * @param parent of this view
     */
    NetworkLogosView(View parent, List<PaymentNetwork> networks) {
        switcher = parent.findViewById(R.id.viewswitcher_logos);
        switcher.setVisibility(View.VISIBLE);

        logos = new HashMap<>();
        selImage = parent.findViewById(R.id.image_selected);

        Resources resources = parent.getContext().getResources();
        margin = (int) resources.getDimension(R.dimen.pmborder_small);

        LinearLayout layout = parent.findViewById(R.id.layout_logos);
        for (PaymentNetwork network : networks) {
            addNetworkLogo(network, layout);
        }
    }

    private void addNetworkLogo(PaymentNetwork network, LinearLayout layout) {
        String code = network.getCode();
        ImageView imageView = inflateLogoImage(layout);
        logos.put(code, new NetworkLogo(code, network.getLink("logo"), imageView));
        layout.addView(imageView);
    }

    private ImageView inflateLogoImage(LinearLayout layout) {
        LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        ImageView view = (ImageView) inflater.inflate(R.layout.view_logosmall, layout, false);
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.setMargins(0, 0, (int) margin, 0);
        view.setLayoutParams(params);
        return view;
    }

    void setSelected(String selected) {
        if (selected != null) {
            showSelectedLogo(selected);
        } else {
            showAllLogos();
        }
    }

    private void showSelectedLogo(String selected) {
        NetworkLogo logo = logos.get(selected);
        ImageHelper.getInstance().loadImage(selImage, logo.url);
        switcher.setDisplayedChild(1);
    }

    private void showAllLogos() {
        ImageHelper helper = ImageHelper.getInstance();
        for (Map.Entry<String, NetworkLogo> entry : logos.entrySet()) {
            NetworkLogo logo = entry.getValue();
            helper.loadImage(logo.image, logo.url);
        }
        switcher.setDisplayedChild(0);
    }

    class NetworkLogo {

        String name;
        URL url;
        ImageView image;

        NetworkLogo(String name, URL url, ImageView image) {
            this.name = name;
            this.url = url;
            this.image = image;
        }
    }
}
