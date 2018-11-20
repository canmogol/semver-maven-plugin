package com.fererlab.semver.flow;

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

    /**
     * Flow exception constructor.
     *
     * @param message error message
     * @param cause cause exception
     */
    public FlowException(final String message, final Exception cause) {
        super(message, cause);
    }
}
