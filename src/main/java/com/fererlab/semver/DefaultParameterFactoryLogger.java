package com.fererlab.semver;

import lombok.extern.slf4j.Slf4j;

/**
 * Logger for DefaultParameterFactory.
 */
@Slf4j
public class DefaultParameterFactoryLogger {

    /**
     * logs the configuration file cannot be read.
     * @param errorMessage exception's message
     */
    public final void couldNotReadConfiguration(final String errorMessage) {
        log.error(String.format("Could not read configuration, exception: %s", errorMessage));
    }

    /**
     * logs the name of the configuration file that will be loaded.
     * @param configurationFile name of the configuration file
     */
    public final void willLoadConfigurationFile(final String configurationFile) {
        log.info(String.format("will load configuration file: %s", configurationFile));
    }

}
