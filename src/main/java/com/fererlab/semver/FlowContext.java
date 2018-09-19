package com.fererlab.semver;

/**
 * Flow context.
 */
public class FlowContext {

    /**
     * Delegate method that calls the flow implementation.
     *
     * @param strategy flow instance
     * @throws FlowException from flow strategy
     */
    public final void validate(final FlowStrategy strategy) throws FlowException {
        strategy.validate();
    }

}
