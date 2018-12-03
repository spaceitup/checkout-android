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
  * Class for holding the TextInputWidgetParameters for the PaymentTheme
  */
 public final class TextInputWidgetParameters {

     private int themeResId;
     private int editTextAppearance;
     private int hintTextAppearance;
     private int errorTextAppearance;
     
     TextInputWidgetParameters() {
     }

     public int getThemeResId() {
         return themeResId;
     }

     public int getEditTextAppearance() { 
         return editTextAppearance;
     }

     public int getHintTextAppearance() { 
         return hintTextAppearance;
     }

     public int getErrorTextAppearance() { 
         return errorTextAppearance;
     }
     
     public static Builder createBuilder() {
         return new Builder();
     }

     public final static class Builder {
         int themeResId = R.style.PaymentThemeTextInput;
         int editTextAppearance = R.style.PaymentTextInputEditText;
         int hintTextAppearance = R.style.PaymentTextInputLayoutHint;
         int errorTextAppearance = R.style.PaymentTextInputLayoutError; 

         public Builder() {
         }

         public Builder setThemeResId(int themeResId) {
             this.themeResId = themeResId;
             return this;
         }

         public Builder setErrorTextAppearance(int errorTextAppearance) {
             this.errorTextAppearance = errorTextAppearance;
             return this;
         }

         public Builder setEditTextAppearance(int editTextAppearance) {
             this.editTextAppearance = editTextAppearance;
             return this;
         }

         public Builder setHintTextAppearance(int hintTextAppearance) {
             this.hintTextAppearance = hintTextAppearance;
             return this;
         }
         
         public TextInputWidgetParameters build() {
             TextInputWidgetParameters params = new TextInputWidgetParameters();
             params.themeResId = this.themeResId;
             params.hintTextAppearance = this.hintTextAppearance;
             params.errorTextAppearance = this.errorTextAppearance;
             params.editTextAppearance = this.editTextAppearance;
             return params;
         }
     }
 }

