package com.fererlab.semver.github;

import com.fererlab.semver.FlowException;
import com.fererlab.semver.FlowStrategy;
import com.fererlab.semver.SemverModel;
import com.fererlab.semver.SemverModelFactory;

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

    private SemverModelFactory semverModelFactory;

    /**
     * Constructor with parameters.
     *
     * @param params  validation parameters
     * @param factory Semantic Version Model Factory
     */
    public GithubFlowStrategy(final Map<String, String> params,
                              final SemverModelFactory factory) {
        this.parameters = Collections.unmodifiableMap(params);
        this.semverModelFactory = factory;
    }

    /**
     * Validates versioning against semantic versioning according to Github's flow.
     *
     * @throws FlowException if versioning doesn't align with semantic versioning
     */
    @Override
    public final void validate() throws FlowException {
        final SemverModel semverModel = semverModelFactory.create(parameters);
        validateModel(semverModel);
        validate(semverModel);
    }

    private void validateModel(final SemverModel semverModel) throws FlowException {
        if (semverModel == null) {
            throw new FlowException("could not create Semantic Version Model");
        }
        String current = semverModel.getCurrent();
        if (current == null || current.trim().isEmpty()) {
            throw new FlowException("Current branch name cannot be empty");
        }
        if (!"master".equalsIgnoreCase(current)) {
            final String error = String.format("Current branch name cannot be 'master', found: %s", current);
            throw new FlowException(error);
        }
    }

    private void validate(final SemverModel model) {
        // https://raw.githubusercontent.com/$user/$project/$target/$type_file
        // type_file can be pom.xml or build.gradle etc
        String urlTemplate = "%url/%user/%project/%target/%type_file";
        final String url = String.format(urlTemplate,
            model.getUrl(),
            model.getUser(),
            model.getProject(),
            model.getTarget(),
            model.getType());
        System.out.println(">>>> " + url);
    }

}
