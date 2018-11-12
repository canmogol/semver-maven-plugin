package com.fererlab.semver.gitflow;

import com.fererlab.semver.DefaultParameterFactory;
import com.fererlab.semver.FlowException;
import com.fererlab.semver.SemverModelFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

/**
 * Git Flow semantic versioning tests.
 */
public class GitFlowStrategyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private GitFlowStrategy gitFlowStrategy;

    @Before
    public void setup() {
        DefaultParameterFactory defaultParameterFactory = new DefaultParameterFactory();
        SemverModelFactory semverModelFactory = new SemverModelFactory(defaultParameterFactory);
        final HashMap<String, String> params = new HashMap<>();
        gitFlowStrategy = new GitFlowStrategy(params, semverModelFactory);
    }

    /**
     * Tests if it is the same version on development branch and current branch.
     */
    @Test
    public void shouldFailOnSameVersionNumber() throws Exception {
        expectedException.expect(FlowException.class);
        gitFlowStrategy.validate();
    }

}
