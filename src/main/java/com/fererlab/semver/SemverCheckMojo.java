package com.fererlab.semver;

import com.fererlab.semver.flow.FlowContext;
import com.fererlab.semver.flow.FlowStrategy;
import com.fererlab.semver.gitflow.GitFlowStrategy;
import com.fererlab.semver.github.GithubFlowStrategy;
import com.fererlab.semver.http.HttpClient;
import com.fererlab.semver.model.SemverModelFactory;
import com.fererlab.semver.params.DefaultParameterFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;

/**
 * Plugin to validate semantic versioning.
 */
@Mojo(name = "install", defaultPhase = LifecyclePhase.INITIALIZE)
public class SemverCheckMojo extends AbstractMojo {

    private static final String GITFLOW = "gitflow";
    private static final String GITHUB = "github";

    @Parameter
    private String type;

    @Parameter
    private Map<String, String> parameters;

    private FlowContext context = new FlowContext();
    private DefaultParameterFactory defaultParameterFactory  = new DefaultParameterFactory();
    private SemverModelFactory semverModelFactory = new SemverModelFactory(defaultParameterFactory);
    private HttpClient httpClient = new HttpClient();

    /**
     * Maven plugin execution entry point.
     *
     * @throws MojoExecutionException wraps all plugin exceptions
     */
    @Override
    public final void execute() throws MojoExecutionException {
        FlowStrategy strategy = createStrategy(type, parameters);
        context.validate(strategy);
    }

    /**
     * Creates a flow instance according to flowType parameter.
     *
     * @param flowType flow type
     * @param params parameters map
     * @return flow implementation
     * @throws MojoExecutionException if there are no implementations for this type
     */
    public final FlowStrategy createStrategy(final String flowType, final Map<String, String> params)
        throws MojoExecutionException {
        switch (flowType) {
            case GITFLOW:
                return new GitFlowStrategy(params, semverModelFactory, httpClient);
            case GITHUB:
                return new GithubFlowStrategy(params, semverModelFactory, httpClient);
            default:
                throw new MojoExecutionException(flowType + " is not a defined flow.");
        }
    }

}
