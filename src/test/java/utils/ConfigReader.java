package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getEnvVariable(String key) {
        return System.getenv(key);
    }

    public String getUrl(String key) {
        return properties.getProperty(key);
    }

    public long getTimeout(String key) {
        return Long.parseLong(properties.getProperty(key));
    }
}
