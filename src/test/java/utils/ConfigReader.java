package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties PROPERTIES;

    public ConfigReader() {
        PROPERTIES = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/config.PROPERTIES")) {
            PROPERTIES.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getUrl(String key) {
        return PROPERTIES.getProperty(key);
    }
}
