package com.fererlab.semver.gitflow;

import com.fererlab.semver.FlowException;
import com.fererlab.semver.FlowStrategy;
import com.fererlab.semver.SemverModel;
import com.fererlab.semver.SemverModelFactory;

import java.util.Map;

/**
 * Git flow repository operations.
 */
public class GitFlowStrategy implements FlowStrategy {

    /**
     * Validation parameters.
     */
    private final Map<String, String> parameters;

    /**
     * Constructor with parameters.
     *
     * @param params validation parameters
     */
    public GitFlowStrategy(final Map<String, String> params) {
        this.parameters = params;
        SemverModel semverModel = SemverModelFactory.create(params);
    }

    /**
     * Validates versioning against semantic versioning according to git-flow.
     *
     * @throws FlowException if versioning doesn't align with semantic versioning
     */
    @Override
    public final void validate() throws FlowException {

        System.out.println(parameters);
    }

}
