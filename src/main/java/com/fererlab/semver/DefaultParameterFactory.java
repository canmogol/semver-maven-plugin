package com.fererlab.semver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Optional;

public class DefaultParameterFactory {

    private static final DefaultParameterFactoryLogger LOGGER = new DefaultParameterFactoryLogger();
    private static final String CONFIGURATION_FILE = "defaults.xml";
    private static DefaultParameters defaultParameters;

    static {
        try {
            LOGGER.willLoadConfigurationFile();
            // will use the JAXB (standard implementation to read the xml file to object)
            InputStream inputStream = DefaultParameterFactory.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE);
            JAXBContext jaxbContext = JAXBContext.newInstance(DefaultParameters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            defaultParameters = (DefaultParameters) jaxbUnmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            LOGGER.couldNotReadConfiguration(e.getMessage());
        }
    }

    public static String create(String parameter) {
        return Optional.ofNullable(defaultParameters.get(parameter))
            .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a default parameter", parameter)));
    }

}
