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
  * Class for holding the DateParameters for the PaymentTheme
  */
 public final class DateParameters {

     private int dialogTitleTextAppearance;
     private int dialogButtonTextAppearance;
     
     DateParameters() {
     }

     public int getDialogTitleTextAppearance() {
         return dialogTitleTextAppearance;
     }

     public int getDialogButtonTextAppearance() {
         return dialogButtonTextAppearance;
     }
     
     public static Builder createBuilder() {
         return new Builder();
     }

     public final static class Builder {
         int dialogTitleTextAppearance = R.style.PaymentText_Medium_Bold;
         int dialogButtonTextAppearance = R.style.PaymentText_Small_Bold_Primary;
             
         Builder() {
         }

         public Builder setDialogTitleTextAppearance(int dialogTitleTextAppearance) {
             this.dialogTitleTextAppearance = dialogTitleTextAppearance;
             return this;
         }

         public Builder setDialogButtonTextAppearance(int dialogButtonTextAppearance) {
             this.dialogButtonTextAppearance = dialogButtonTextAppearance;
             return this;
         }

         public DateParameters build() {
             DateParameters params = new DateParameters();
             params.dialogTitleTextAppearance = this.dialogTitleTextAppearance;
             params.dialogButtonTextAppearance = this.dialogButtonTextAppearance;
             return params;
         }
     }
 }

