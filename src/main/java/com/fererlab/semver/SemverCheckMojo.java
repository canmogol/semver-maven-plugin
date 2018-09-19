package com.fererlab.semver;

import com.fererlab.semver.gitflow.GitFlowStrategy;
import com.fererlab.semver.github.GithubFlowStrategy;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;

/**
 * Plugin to validate semantic versioning.
 */
@Mojo(name = "install", defaultPhase = LifecyclePhase.INITIALIZE)
public class SemverCheckMojo extends AbstractMojo {

    @Parameter
    private String type;

    @Parameter
    private Map<String, String> parameters;

    private FlowContext context = new FlowContext();

    /**
     * Maven plugin execution entry point.
     *
     * @throws MojoExecutionException wraps all plugin exceptions
     */
    @Override
    public final void execute() throws MojoExecutionException {
        initialize();
        validate();
        FlowStrategy strategy = createStrategy(type);
        context.validate(strategy);
    }

    private void initialize() {
        
    }

    private void validate() {

    }

    /**
     * Creates a flow instance according to flowType parameter.
     *
     * @param flowType flow type
     * @return flow implementation
     * @throws MojoExecutionException if there are no implementations for this type
     */
    private FlowStrategy createStrategy(final String flowType) throws MojoExecutionException {
        switch (flowType) {
            case "gitflow":
                return new GitFlowStrategy(parameters);
            case "github":
                return new GithubFlowStrategy(parameters);
            default:
                throw new MojoExecutionException(flowType + " is not a defined flow.");
        }
    }

}
