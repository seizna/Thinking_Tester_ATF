package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger LOG = LogManager.getLogger(ConfigReader.class);
    private final Properties properties = new Properties();
    private static ConfigReader configReader;

    private ConfigReader() {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            LOG.error("Error reading properties file: {}", ex.getMessage());
        }
    }

    public static String getProperty(String key) {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader.properties.getProperty(key);
    }
}
