package com.fererlab.semver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Optional;

/**
 * Creates the default parameter values from defaults xml file.
 */
public class DefaultParameterFactory {

    private final DefaultParameterFactoryLogger logger = new DefaultParameterFactoryLogger();
    private DefaultParameters defaultParameters;

    /**
     * No Args Constructor.
     */
    public DefaultParameterFactory() {
        readDefaults();
    }

    private void readDefaults() {
        try {
            String defaultConfigurationFile = "defaults.xml";
            logger.willLoadConfigurationFile(defaultConfigurationFile);
            // will use the JAXB (standard implementation to read the xml file to object)
            InputStream inputStream = DefaultParameterFactory.class.getClassLoader()
                .getResourceAsStream(defaultConfigurationFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(DefaultParameters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            defaultParameters = (DefaultParameters) jaxbUnmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            logger.couldNotReadConfiguration(e.getMessage());
        }
    }

    /**
     * creates a default value for the given parameter or throws an IllegalArgumentException.
     *
     * @param parameter name of the parameter
     * @return string value of the default parameter
     */
    public final String create(final String parameter) {
        return Optional.ofNullable(defaultParameters.get(parameter))
            .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a default parameter", parameter)));
    }

}
