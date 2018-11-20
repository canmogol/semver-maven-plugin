package com.fererlab.semver;

import com.fererlab.semver.flow.FlowContext;
import com.fererlab.semver.flow.FlowStrategy;
import com.fererlab.semver.gitflow.GitFlowStrategy;
import com.fererlab.semver.github.GithubFlowStrategy;
import com.fererlab.semver.model.SemverModelFactory;
import com.fererlab.semver.params.DefaultParameterFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Semantic versioning maven plugin entry point test.
 */
public class SemverCheckMojoTest {

    private static final String GITFLOW = "gitflow";
    private static final String GITHUB = "github";
    private static final String NON_FLOW_TYPE = "NON_FLOW_TYPE";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private SemverCheckMojo semverCheckMojo;

    @Mock
    private FlowContext context;

    @Mock
    private Map<String, String> parameters;

    @Mock
    private DefaultParameterFactory defaultParameterFactory;

    @Mock
    private SemverModelFactory semverModelFactory;

    @Mock
    private FlowStrategy flowStrategy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldCreateStrategy() throws MojoExecutionException {
        semverCheckMojo = new SemverCheckMojo();

        FlowStrategy gitFlowStrategy = semverCheckMojo.createStrategy(GITFLOW, parameters);
        assertThat(gitFlowStrategy, instanceOf(GitFlowStrategy.class));

        FlowStrategy gitHubFlowStrategy = semverCheckMojo.createStrategy(GITHUB, parameters);
        assertThat(gitHubFlowStrategy, instanceOf(GithubFlowStrategy.class));

        expectedException.expect(MojoExecutionException.class);
        String exceptionMessage = String.format("%s is not a defined flow.", NON_FLOW_TYPE);
        expectedException.expectMessage(exceptionMessage);
        semverCheckMojo.createStrategy(NON_FLOW_TYPE, parameters);
    }

}
