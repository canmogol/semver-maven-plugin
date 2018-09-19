package com.fererlab.semver.story;

import java.util.Collections;
import java.util.List;

/**
 * Git-Flow story, see git_flow_story.story file.
 */
public class GitFlowStory extends BaseJUnitStory {

    @Override
    public List<?> getSteps() {
        return Collections.singletonList(new GitFlowSteps());
    }

}
