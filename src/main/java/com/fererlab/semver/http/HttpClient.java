package com.fererlab.semver.http;

import com.fererlab.semver.flow.FlowException;
import lombok.val;
import okhttp3.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Http httpClientDelegate facade for underlying implementation.
 */
public class HttpClient {

    private String url;

    private HttpClientDelegate httpClientDelegate = new HttpClientDelegate();
    private HttpRequest httpRequest = new HttpRequest();

    /**
     * sets the url to be used in the http request.
     * @param requestURL request url
     */
    public final void setUrl(final String requestURL) {
        this.url = requestURL;
    }

    /**
     * sends a GET request and returns the response as stream.
     *
     * @return response as input stream
     * @throws FlowException captures the IOException of the http request.
     */
    public final InputStream getResponseBody() throws FlowException {
        try {
            val request = new Request.Builder()
                .url(url)
                .build();
            httpRequest.setRequest(request);
            val httpResponseBody = httpClientDelegate.getResponseBody(httpRequest);
            val responseBody = httpResponseBody.getResponseBody();
            if (Objects.isNull(responseBody)) {
                val error = String.format("could not get body for url %s", url);
                throw new FlowException(error);
            }
            return responseBody.byteStream();
        } catch (IOException e) {
            val error = String.format("Got an IO exception, error: %s", e.getMessage());
            throw new FlowException(error, e);
        }
    }


}
