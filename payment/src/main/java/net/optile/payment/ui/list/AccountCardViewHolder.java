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

package net.optile.payment.ui.list;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.model.AccountMask;
import net.optile.payment.model.PaymentMethod;
import net.optile.payment.ui.model.AccountCard;
import net.optile.payment.ui.model.PaymentCard;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * The AccountCardViewHolder class holding and binding views for an AccountCard
 */
final class AccountCardViewHolder extends PaymentCardViewHolder {

    private final TextView title;
    private final TextView subTitle;

    private AccountCardViewHolder(ListAdapter adapter, View parent, AccountCard accountCard) {
        super(adapter, parent);

        PaymentTheme theme = adapter.getPaymentTheme();
        PageParameters params = theme.getPageParameters();

        this.title = parent.findViewById(R.id.text_title);
        PaymentUtils.setTextAppearance(title, params.getAccountCardTitleStyle());

        this.subTitle = parent.findViewById(R.id.text_subtitle);
        PaymentUtils.setTextAppearance(subTitle, params.getAccountCardSubtitleStyle());

        addAccountLogo(parent, accountCard, theme);
        addElementWidgets(accountCard.getInputElements(), theme);
        addButtonWidget(theme);

        setLastImeOptions();
    }

    static ViewHolder createInstance(ListAdapter adapter, AccountCard accountCard, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_accountcard, parent, false);
        return new AccountCardViewHolder(adapter, view, accountCard);
    }

    void onBind(PaymentCard paymentCard) {

        if (!(paymentCard instanceof AccountCard)) {
            throw new IllegalArgumentException("Expected AccountCard in onBind");
        }
        super.onBind(paymentCard);
        AccountCard card = (AccountCard) paymentCard;
        AccountMask mask = card.getMaskedAccount();
        bindTitle(mask, card.getPaymentMethod());
        bindSubTitle(mask);
        bindLogoView(card.getCode(), card.getLink("logo"), isExpanded());
    }

    private void addAccountLogo(View parent, AccountCard card, PaymentTheme theme) {
        List<String> names = Arrays.asList(card.getCode());
        addLogoViews(parent, names, theme);
    }

    private void bindTitle(AccountMask mask, String method) {
        switch (method) {
            case PaymentMethod.CREDIT_CARD:
            case PaymentMethod.DEBIT_CARD:
                title.setText(mask.getNumber());
                break;
            default:
                title.setText(mask.getDisplayLabel());
        }
    }

    private void bindSubTitle(AccountMask mask) {
        int expiryMonth = PaymentUtils.toInt(mask.getExpiryMonth());
        int expiryYear = PaymentUtils.toInt(mask.getExpiryYear());

        if (expiryMonth > 0 && expiryYear > 0) {
            String format = subTitle.getContext().getString(R.string.pmlist_date);
            String monthLabel = String.format(Locale.getDefault(), "%02d", expiryMonth);
            String yearLabel = Integer.toString(expiryYear);
            subTitle.setText(String.format(format, monthLabel, yearLabel));
            subTitle.setVisibility(View.VISIBLE);
        } else {
            subTitle.setVisibility(View.GONE);
        }
    }
}
