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
  * Class for holding the DateWidgetParameters for the PaymentTheme
  */
 public final class DateWidgetParameters {

     DateWidgetParameters() {
     }

     public static Builder createBuilder() {
         return new Builder();
     }

     public final static class Builder {
             
         public Builder() {
         }

         public DateWidgetParameters build() {
             DateWidgetParameters params = new DateWidgetParameters();
             return params;
         }
     }
 }

