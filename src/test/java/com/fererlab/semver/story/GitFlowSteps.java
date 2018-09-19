package com.fererlab.semver.story;


import com.fererlab.semver.FlowException;
import com.fererlab.semver.gitflow.GitFlowStrategy;
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
        parameters.put("current", current);
    }

    @Given("the target branch is $target")
    public void whenTheTargetBranchIs(String target) {
        parameters.put("target", target);
    }

    @Given("the type is $type")
    public void whenTheTypeIs(String type) {
        parameters.put("type", type);
    }

    @Given("the user is $user")
    public void whenTheUserIs(String user) {
        parameters.put("user", user);
    }

    @Given("the project is $project")
    public void whenTheProjectIs(String project) {
        parameters.put("project", project);
    }

    @Given("the url is $url")
    public void whenTheUrlIs(String url) {
        parameters.put("url", url);
    }

    @Given("a gitflow semver checker")
    public void givenAGitflowSemverChecker() {
        gitFlowStrategy = new GitFlowStrategy(parameters);
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
