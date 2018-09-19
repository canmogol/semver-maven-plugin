package com.fererlab.semver.gitflow;

import com.fererlab.semver.FlowException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Git Flow semantic versioning tests.
 */
public class GitFlowStrategyTest {

    private GitFlowStrategy gitFlowStrategy;

    @Before
    public void setup() {
        gitFlowStrategy = new GitFlowStrategy(new HashMap<>());
    }

    /**
     * Tests if it is the same version on development branch and current branch.
     */
    @Test
    public void shouldFailOnSameVersionNumber() throws Exception {
        try {
            gitFlowStrategy.validate();
        } catch (FlowException e) {
            throw new Exception(e);
        }
    }

}
