package com.fererlab.semver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultParameterFactoryLogger {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public void couldNotReadConfiguration(String errorMessage) {
        logger.log(Level.SEVERE, String.format("Could not read configuration, exception: %s", errorMessage));
    }

    public void willLoadConfigurationFile(String configurationFile) {
        logger.log(Level.INFO, String.format("will load configuration file: %s", configurationFile));
    }

}
