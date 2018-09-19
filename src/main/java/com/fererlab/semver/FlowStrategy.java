package com.fererlab.semver;

/**
 * Defines the flow operation.
 */
public interface FlowStrategy {

    /**
     * Validates versioning against semantic versioning.
     *
     * @throws FlowException if versioning doesn't align with semantic versioning
     */
    void validate() throws FlowException;

}
