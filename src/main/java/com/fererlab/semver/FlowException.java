package com.fererlab.semver;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Flow exception.
 */
@SuppressWarnings("serial")
public class FlowException extends MojoExecutionException {

    /**
     * Flow exception constructor.
     *
     * @param message error message
     */
    public FlowException(final String message) {
        super(message);
    }

}
