package com.fererlab.semver.story;


import com.fererlab.semver.params.DefaultParameterFactory;
import com.fererlab.semver.flow.FlowException;
import com.fererlab.semver.http.HttpClient;
import com.fererlab.semver.params.Params;
import com.fererlab.semver.model.SemverModelFactory;
import com.fererlab.semver.gitflow.GitFlowStrategy;
import lombok.val;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Git-Flow steps in the git_flow_story.story file.
 */
public class GitFlowSteps {

    private static Boolean CURRENT_VERSION_IS_GREATER_THAN_TARGET = Boolean.TRUE;
    private static FlowException FLOW_EXCEPTION;

    private Map<String, String> parameters;
    private GitFlowStrategy gitFlowStrategy;

    @Given("a gitflow parameter map")
    public void givenAGitflowParameterMap() {
        parameters = new HashMap<>();
    }

    @Given("the current branch is $current")
    public void whenTheCurrentBranchIs(String current) {
        parameters.put(Params.CURRENT.getName(), current);
    }

    @Given("the target branch is $target")
    public void whenTheTargetBranchIs(String target) {
        parameters.put(Params.TARGET.getName(), target);
    }

    @Given("the type is $type")
    public void whenTheTypeIs(String type) {
        parameters.put(Params.TYPE.getName(), type);
    }

    @Given("the directory is $directory")
    public void whenTheDirectoryIs(String directory) {
        parameters.put(Params.DIRECTORY.getName(), directory);
    }

    @Given("the user is $user")
    public void whenTheUserIs(String user) {
        parameters.put(Params.USER.getName(), user);
    }

    @Given("the project is $project")
    public void whenTheProjectIs(String project) {
        parameters.put(Params.PROJECT.getName(), project);
    }

    @Given("the url is $url")
    public void whenTheUrlIs(String url) {
        parameters.put(Params.URL.getName(), url);
    }

    @Given("a gitflow semver checker")
    public void givenAGitflowSemverChecker() {
        val defaultParameterFactory = new DefaultParameterFactory();
        val semverModelFactory = new SemverModelFactory(defaultParameterFactory);
        val httpClient = new HttpClient();
        gitFlowStrategy = new GitFlowStrategy(parameters, semverModelFactory, httpClient);
    }

    @When("it checks current against target using parameter map")
    public void whenItChecksCurrentAgainstTargetUsingParmaeterMap() {
        try {
            gitFlowStrategy.validate();
        } catch (FlowException e) {
            CURRENT_VERSION_IS_GREATER_THAN_TARGET = Boolean.FALSE;
            FLOW_EXCEPTION = e;
        }
    }

    @Then("current version should be greater than the target version")
    public void thenCurrentVersionShouldBeGreaterThanTheTargetVersion() {
        assertThat(String.valueOf(FLOW_EXCEPTION), CURRENT_VERSION_IS_GREATER_THAN_TARGET, Matchers.is(true));
    }

}
