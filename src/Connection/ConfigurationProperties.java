package Connection;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationProperties {

    private static Properties properties;

    private static void initialize() {
        properties = new Properties();
        try {
            properties.load(ConfigurationProperties.class
                    .getResourceAsStream("configuracion.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (properties == null) {
            initialize();
        }
    }

    public static String getDriverClass() {
        return properties.getProperty("jdbc.driver.class");
    }

    public static String getPassword() {
        return properties.getProperty("jdbc.driver.password");
    }

    public static String getURL() {
        return properties.getProperty("jdbc.driver.url");
    }

    public static String getUser() {
        return properties.getProperty("jdbc.driver.user");
    }

}
