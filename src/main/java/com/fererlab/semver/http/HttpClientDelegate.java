package com.fererlab.semver.http;

import lombok.val;
import okhttp3.OkHttpClient;

import java.io.IOException;

/**
 * Http client delegate.
 */
public class HttpClientDelegate extends OkHttpClient {

    /**
     * Constructor.
     * @param request http request
     * @return http response body
     * @throws IOException thrown by the underlying implementation on http request.
     */
    public final HttpResponseBody getResponseBody(final HttpRequest request) throws IOException {
        val response = newCall(request.getRequest()).execute();
        return new HttpResponseBody(response.body());
    }

}
