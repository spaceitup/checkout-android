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
  * Class for holding the ButtonWidgetParameters for the PaymentTheme
  */
 public final class ButtonWidgetParameters {

     private int themeResId;

     private int labelTextStyle;
     
     ButtonWidgetParameters() {
     }

     public static Builder createBuilder() {
         return new Builder();
     }

     public int getThemeResId() {
         return themeResId;
     }

     public int getLabelTextStyle() {
         return labelTextStyle;
     }
     
     public final static class Builder {
         int themeResId = R.style.PaymentThemeButton;
         int labelTextStyle = R.style.PaymentText_Medium_Bold_Light;
             
         public Builder() {
         }

         public Builder setThemeResId(int themeResId) {
             this.themeResId = themeResId;
             return this;
         }

         public Builder setLabelTextStyle(int labelTextStyle) {
             this.labelTextStyle = labelTextStyle;
             return this;
         }

         public ButtonWidgetParameters build() {
             ButtonWidgetParameters params = new ButtonWidgetParameters();
             params.themeResId = this.themeResId;
             params.labelTextStyle = this.labelTextStyle;
             return params;
         }
     }
 }

