/**
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.network;

/**
 * Class defining all constants used for the network package
 */
public interface NetworkConstants {

    String HEADER_CONTENT_TYPE  = "Content-Type";
    String HEADER_USER_AGENT = "User-Agent";
    
    String UTF8 = "UTF-8";

    int TIMEOUT_CONNECT = 5000;
    int TIMEOUT_READ = 30000;

    String HTTP_GET = "GET";
    String HTTP_POST = "POST";
    String HTTP_PUT = "PUT";
    String HTTP_PATCH = "PATCH";
    String HTTP_DELETE = "DELETE";

}
