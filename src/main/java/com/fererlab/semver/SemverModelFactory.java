package com.fererlab.semver;

import java.util.Map;

/**
 * Semantic versioning model factory.
 */
public class SemverModelFactory {

    private final DefaultParameterFactory defaultParameterFactory;

    /**
     * Constructor.
     *
     * @param factory factory for default parameters
     */
    public SemverModelFactory(final DefaultParameterFactory factory) {
        this.defaultParameterFactory = factory;
    }

    /**
     * Creates a SemverModel with given and default parameters.
     *
     * @param params map of the given parameters.
     * @return SemverModel populated with given parameter values and default values.
     * @throws FlowException throws exception at missing parameters
     */
    public final SemverModel create(final Map<String, String> params) throws FlowException {
        try {
            return SemverModel.builder()
                .current(getParameter(params, Params.CURRENT.getName()))
                .target(getParameter(params, Params.TARGET.getName()))
                .type(getParameter(params, Params.TYPE.getName()))
                .directory(getParameter(params, Params.DIRECTORY.getName()))
                .user(getParameter(params, Params.USER.getName()))
                .project(getParameter(params, Params.PROJECT.getName()))
                .url(getParameter(params, Params.URL.getName()))
                .build();
        } catch (IllegalArgumentException e) {
            throw new FlowException(e.getMessage(), e);
        }
    }

    private String getParameter(
        final Map<String, String> params,
        final String mandatoryParameter) {
        return params
            .keySet()
            .stream()
            .filter(k -> k.equals(mandatoryParameter))
            .map(params::get)
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(String.format("%s mandatory parameter is missing", mandatoryParameter)));
    }

}
