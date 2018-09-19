package com.fererlab.semver.story;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

/**
 * Base class for story configuration.
 */
public abstract class BaseJUnitStory extends JUnitStory {

	/**
     * Generic configuration for all stories.
     *
     * @return configuration
     */
    @Override
    public Configuration configuration() {
        StoryReporterBuilder storyReporterBuilder = new StoryReporterBuilder()
            .withDefaultFormats()
            .withFormats(Format.CONSOLE, Format.TXT);
        LoadFromClasspath storyLoader = new LoadFromClasspath(this.getClass());
        return new MostUsefulConfiguration()
            // where to find the stories
            .useStoryLoader(storyLoader)
            // CONSOLE and TXT reporting
            .useStoryReporterBuilder(storyReporterBuilder);
    }

	/**
     * Creates a new instance of step factory with configuration and steps.
     *
     * @return step factory
     */
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), getSteps());
    }

    public abstract List<?> getSteps();

}
