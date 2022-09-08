package com.volvo.project.parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class ExecutionParametersLoader {
    private static final String DEFAULT_PROPERTIES_FILE_NAME = "default.properties";
    private static final String PROPERTIES_FILE_NAME_ENV_VARIABLE = "propertiesFileName";
    private static final String projectDir = System.getProperty("user.dir");
    private static final String propertiesFileName = getPropertiesFileName();
    private static final String propertiesFilePath = String.format("%s/src/test/resources/properties/%s", projectDir, propertiesFileName);
    private final Properties propertiesFromFile = new Properties();
    private ExecutionParameters parameters;

    public ExecutionParametersLoader() {
        loadExecutionParameters();
    }

    public ExecutionParameters getExecutionParameters() {
        return parameters;
    }

    private void loadExecutionParameters() {
        loadPropertiesFromFile();
        parameters = new ExecutionParameters.ExecutionParametersBuilder()
                .browser(Optional.ofNullable(System.getProperty("browser")).orElse(propertiesFromFile.getProperty("browser")))
                .docker(Boolean.parseBoolean(Optional.ofNullable(System.getProperty("docker")).orElse(propertiesFromFile.getProperty("docker"))))
                .headless(Boolean.parseBoolean(Optional.ofNullable(System.getProperty("headless")).orElse(propertiesFromFile.getProperty("headless"))))
                .proxy(Boolean.parseBoolean(Optional.ofNullable(System.getProperty("proxy")).orElse(propertiesFromFile.getProperty("proxy"))))
                .resolutionWidth(Integer.parseInt(Optional.ofNullable(System.getProperty("resolutionWidth")).orElse(propertiesFromFile.getProperty("resolutionWidth"))))
                .resolutionHeight(Integer.parseInt(Optional.ofNullable(System.getProperty("resolutionHeight")).orElse(propertiesFromFile.getProperty("resolutionHeight"))))
                .build();
    }

    private void loadPropertiesFromFile() {
        try {
            propertiesFromFile.load(Files.newInputStream(Paths.get(propertiesFilePath)));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to load properties from file: %s", propertiesFilePath));
        }
    }

    private static String getPropertiesFileName() {
        return Optional.ofNullable(System.getProperty(PROPERTIES_FILE_NAME_ENV_VARIABLE, System.getenv(PROPERTIES_FILE_NAME_ENV_VARIABLE))).orElse(DEFAULT_PROPERTIES_FILE_NAME);
    }
}
