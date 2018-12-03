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
  * Class for holding the CheckBoxWidgetParameters for the PaymentTheme
  */
 public final class CheckBoxWidgetParameters {

     private int themeResId;
     private int checkedTextAppearance;
     private int uncheckedTextAppearance;
     
     CheckBoxWidgetParameters() {
     }

     public static Builder createBuilder() {
         return new Builder();
     }

     public int getThemeResId() {
         return themeResId;
     }

     public int getCheckedTextAppearance() {
         return checkedTextAppearance;
     }

     public int getUncheckedTextAppearance() {
         return uncheckedTextAppearance;
     }
     
     public final static class Builder {
         int themeResId = R.style.PaymentThemeCheckBox;
         int uncheckedTextAppearance = R.style.PaymentText_Medium_Hint;
         int checkedTextAppearance = R.style.PaymentText_Medium;
             
         public Builder() {
         }

         public Builder setThemeResId(int themeResId) {
             this.themeResId = themeResId;
             return this;
         }

         public Builder setCheckedTextAppearance(int checkedTextAppearance) {
             this.checkedTextAppearance = checkedTextAppearance;
             return this;
         }

         public Builder setUncheckedTextAppearance(int uncheckedTextAppearance) {
             this.uncheckedTextAppearance = uncheckedTextAppearance;
             return this;
         }
         
         public CheckBoxWidgetParameters build() {
             CheckBoxWidgetParameters params = new CheckBoxWidgetParameters();
             params.themeResId = this.themeResId;
             params.checkedTextAppearance = this.checkedTextAppearance;
             params.uncheckedTextAppearance = this.uncheckedTextAppearance;
             return params;
         }
     }
 }

