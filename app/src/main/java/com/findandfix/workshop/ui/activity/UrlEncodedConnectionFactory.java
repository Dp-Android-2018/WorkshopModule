package com.findandfix.workshop.ui.activity;

/**
 * Created by DELL on 03/06/2018.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Form URL-Encoded Connection Factory
 *
 * Allows HttpAuthorizer to write URL parameters to the connection
 */
public class UrlEncodedConnectionFactory extends ConnectionFactory {

    private Map<String, String> mQueryStringParameters = new HashMap<String, String>();

    /**
     * Create a Form URL-encoded factory
     */
    public UrlEncodedConnectionFactory() {
    }

    /**
     * Create a Form URL-encoded factory
     *
     * @param queryStringParameters extra parameters that need to be added to query string.
     */
    public UrlEncodedConnectionFactory(final Map<String, String> queryStringParameters) {
        this.mQueryStringParameters = queryStringParameters;
    }

    @Override
    public String getCharset() {
        return "UTF-8";
    }

    @Override
    public String getContentType() {
        return "application/x-www-form-urlencoded";
    }

    public String getBody() {
        final StringBuffer urlParameters = new StringBuffer();
        try {
            urlParameters.append("channel_name=").append(URLEncoder.encode(getChannelName(), getCharset()));
            urlParameters.append("&socket_id=").append(URLEncoder.encode(getSocketId(), getCharset()));

            // Adding extra parameters supplied to be added to query string.
            for (final String parameterName : mQueryStringParameters.keySet()) {
                urlParameters.append("&").append(parameterName).append("=");
                urlParameters.append(URLEncoder.encode(mQueryStringParameters.get(parameterName), getCharset()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Pusher Demo :"+urlParameters.toString());
        return urlParameters.toString();
    }
}