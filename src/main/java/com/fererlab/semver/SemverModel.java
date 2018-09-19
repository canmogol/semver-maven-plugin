package com.fererlab.semver;

import lombok.Builder;

@Builder
public class SemverModel {
    private String current;
    private String target;
    private String type;
    private String user;
    private String project;
    private String url;
}
