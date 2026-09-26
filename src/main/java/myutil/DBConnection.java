package myutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {

            Properties props = new Properties();

            InputStream input =
                    DBConnection.class
                            .getClassLoader()
                            .getResourceAsStream("db.properties");

            // db.properties is optional
            if (input != null) {
                props.load(input);
                input.close();
            }

            // Environment variables take priority
            URL = getEnvOrProperty(
                    "DB_URL",
                    props.getProperty("db.url")
            );

            USER = getEnvOrProperty(
                    "DB_USERNAME",
                    props.getProperty("db.username")
            );

            PASSWORD = getEnvOrProperty(
                    "DB_PASSWORD",
                    props.getProperty("db.password")
            );

            if (URL == null || USER == null || PASSWORD == null) {
                throw new RuntimeException(
                        "Database configuration is missing. " +
                        "Set DB_URL, DB_USERNAME and DB_PASSWORD."
                );
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getEnvOrProperty(
            String envName,
            String propertyValue) {

        String envValue = System.getenv(envName);

        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue;
        }

        return propertyValue;
    }

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}