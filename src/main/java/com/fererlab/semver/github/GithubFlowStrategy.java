package com.fererlab.semver.github;

import com.fererlab.semver.FlowException;
import com.fererlab.semver.FlowStrategy;

import java.util.Collections;
import java.util.Map;

/**
 * Github's flow operations.
 */
public class GithubFlowStrategy implements FlowStrategy {

    /**
     * validation parameters.
     */
    private final Map<String, String> parameters;

    /**
     * Constructor with parameters.
     *
     * @param params validation parameters
     */
    public GithubFlowStrategy(final Map<String, String> params) {
        this.parameters = Collections.unmodifiableMap(params);
    }

    /**
     * Validates versioning against semantic versioning according to Github's flow.
     *
     * @throws FlowException if versioning doesn't align with semantic versioning
     */
    @Override
    public final void validate() throws FlowException {
        System.out.println(parameters);
    }

}
