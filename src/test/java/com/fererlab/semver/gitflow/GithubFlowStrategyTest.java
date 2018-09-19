package com.fererlab.semver.gitflow;

import com.fererlab.semver.FlowException;
import com.fererlab.semver.github.GithubFlowStrategy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

/**
 * Git Flow semantic versioning tests.
 */
public class GithubFlowStrategyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
    }

    /**
     * Tests if it is the same version on development branch and current branch.
     */
    @Test
    public void shouldFailOnSameVersionNumber() throws Exception {
        expectedException.expect(FlowException.class);
        Map<String, String> parameters = new HashMap<>();
        GithubFlowStrategy githubFlowStrategy = new GithubFlowStrategy(parameters);
        githubFlowStrategy.validate();
    }

}
