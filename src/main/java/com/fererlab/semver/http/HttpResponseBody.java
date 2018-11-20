package com.fererlab.semver.http;

import okhttp3.ResponseBody;

/**
 * Http Response Body.
 */
public class HttpResponseBody {

    private final ResponseBody responseBody;

    /**
     * Constructor.
     * @param body response body
     */
    public HttpResponseBody(final ResponseBody body) {
        this.responseBody = body;
    }

    /**
     * Returns the response body.
     * @return response body
     */
    public final ResponseBody getResponseBody() {
        return responseBody;
    }
}
