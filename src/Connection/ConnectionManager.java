package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager singleton;

    private ConnectionManager() {
        try {
            initialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        if (singleton == null) {
            synchronized (ConnectionManager.class) {
                if (singleton == null) {
                    singleton = new ConnectionManager();
                }
            }
        }
        return singleton;
    }

    private static void initialize() throws ClassNotFoundException {
        ConfigurationProperties.load();
        Class.forName(ConfigurationProperties.getDriverClass());
    }

    public Connection retrieveConnection() throws SQLException {
        return DriverManager.getConnection(
                ConfigurationProperties.getURL(),
                ConfigurationProperties.getUser(),
                ConfigurationProperties.getPassword()
        );
    }
}
