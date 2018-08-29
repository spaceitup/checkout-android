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

    /**
     * The header fields
     */
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * The socket and read timeouts, 30 seconds read and 5 seconds connect
     */
    int TIMEOUT_READ = 30000;
    int TIMEOUT_CONNECT = 5000;

    /**
     * The request types as Strings
     */
    String HTTP_GET = "GET";
    String HTTP_POST = "POST";
    String HTTP_PUT = "PUT";
    String HTTP_PATCH = "PATCH";
    String HTTP_DELETE = "DELETE";

    /**
     * The response values
     */
    String JSON_USER = "user";
    String JSON_ACCESS_TOKEN = "accessToken";
    String JSON_NAME = "name";
    String JSON_USER_ID = "userId";

    /**
     * The request key values
     */
    String REQ_APPDEVICE_ID = "appDeviceId";
    String REQ_NAME = "name";
    String REQ_TYPE = "type";
}
