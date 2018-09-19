package com.fererlab.semver;

import java.util.Map;

public class SemverModelFactory {
    public static SemverModel create(Map<String, String> params) {

        SemverModel.builder()
            .current(getMandatoryParameter(params, "current"))
            .target(getMandatoryParameter(params, "target"))
            .type(getDefaultParameter(params, DefaultParameterFactory.create("type"), "target"));
//            .(getDefaultParameter(params, DefaultParameterFactory.create("type"), "target"));
        return null;
    }

    private static String getDefaultParameter(Map<String, String> params,
                                              String parameter,
                                              String defaultValue) {
        return params.keySet().stream().filter(k -> k.equals(parameter)).map(params::get).findFirst()
            .orElse(defaultValue);
    }

    private static String getMandatoryParameter(Map<String, String> params, String mandatoryParameter) {
        return params.keySet().stream().filter(k -> k.equals(mandatoryParameter)).map(params::get).findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(String.format("%s is mandatory parameter", mandatoryParameter)));
    }

}
