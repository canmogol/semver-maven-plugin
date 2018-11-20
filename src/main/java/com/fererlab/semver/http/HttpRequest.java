package com.fererlab.semver.http;

import okhttp3.Request;

/**
 * Http Request.
 */
public class HttpRequest {

    private Request request;

    /**
     * sets request.
     * @param req Request
     */
    public final void setRequest(final Request req) {
        this.request = req;
    }

    /**
     * gets request.
     * @return Request
     */
    public final Request getRequest() {
        return request;
    }
}
