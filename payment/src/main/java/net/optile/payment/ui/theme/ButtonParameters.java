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
  * Class for holding the ButtonParameters for the PaymentTheme
  */
 public final class ButtonParameters {

     private int themeResId;

     private int labelTextAppearance;
     
     ButtonParameters() {
     }

     public static Builder createBuilder() {
         return new Builder();
     }

     public int getThemeResId() {
         return themeResId;
     }

     public int getLabelTextAppearance() {
         return labelTextAppearance;
     }
     
     public final static class Builder {
         int themeResId = R.style.PaymentThemeButton;
         int labelTextAppearance = R.style.PaymentText_Medium_Bold_Light;
             
         Builder() {
         }

         public Builder setThemeResId(int themeResId) {
             this.themeResId = themeResId;
             return this;
         }

         public Builder setLabelTextAppearance(int labelTextAppearance) {
             this.labelTextAppearance = labelTextAppearance;
             return this;
         }

         public ButtonParameters build() {
             ButtonParameters params = new ButtonParameters();
             params.themeResId = this.themeResId;
             params.labelTextAppearance = this.labelTextAppearance;
             return params;
         }
     }
 }

