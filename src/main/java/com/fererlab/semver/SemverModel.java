package com.fererlab.semver;

import lombok.Builder;
import lombok.Data;

/**
 * Semantic Version Model.
 */
@Data
@Builder
public class SemverModel {
    private String current;
    private String target;
    private String user;
    private String project;
    private String url;
    private String type;
    private String directory;
}
