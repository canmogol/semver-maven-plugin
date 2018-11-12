package com.fererlab.semver;

/**
 * Configuration parameters.
 */
public enum Params {
    CURRENT("current"),
    TARGET("target"),
    TYPE("type"),
    DIRECTORY("directory"),
    USER("user"),
    PROJECT("project"),
    URL("url");

    private final String name;

    /**
     * Constructor.
     * @param paramName parameter name
     */
    Params(final String paramName) {
        this.name = paramName;
    }

    /**
     * Returns the name of the parameter.
     *
     * @return name of the parameter
     */
    public String getName() {
        return name;
    }
}
