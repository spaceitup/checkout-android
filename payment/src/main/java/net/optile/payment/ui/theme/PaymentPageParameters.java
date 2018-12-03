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

 package net.optile.payment.ui.theme;

 import net.optile.payment.R;

 /**
  * Class for holding the PaymentPage theme parameters
  */
 public final class PaymentPageParameters {

     private int themeResId;

     PaymentPageParameters() {
     }

     public static Builder createBuilder() {
         return new Builder();
     }

     public int getThemeResId() {
         return themeResId;
     }

     public final static class Builder {
         int themeResId = R.style.PaymentTheme_PaymentPage;

         public Builder() {
         }

         public Builder setThemeResId(int themeResId) {
             this.themeResId = themeResId;
             return this;
         }

         public PaymentPageParameters build() {
             PaymentPageParameters params = new PaymentPageParameters();
             params.themeResId = this.themeResId;
             return params;
         }
     }
 }

