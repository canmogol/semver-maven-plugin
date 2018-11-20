package com.fererlab.semver.gitflow;

import com.fererlab.semver.params.DefaultParameterFactory;
import com.fererlab.semver.flow.FlowException;
import com.fererlab.semver.http.HttpClient;
import com.fererlab.semver.model.SemverModelFactory;
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

    private GithubFlowStrategy githubFlowStrategy;

    @Before
    public void setup() {
        DefaultParameterFactory defaultParameterFactory = new DefaultParameterFactory();
        SemverModelFactory semverModelFactory = new SemverModelFactory(defaultParameterFactory);
        HttpClient httpClient = new HttpClient();
        Map<String, String> parameters = new HashMap<>();
        githubFlowStrategy = new GithubFlowStrategy(parameters, semverModelFactory, httpClient);
    }

    /**
     * Tests if it is the same version on development branch and current branch.
     */
    @Test
    public void shouldFailOnSameVersionNumber() throws Exception {
        expectedException.expect(FlowException.class);
        githubFlowStrategy.validate();
    }

}
