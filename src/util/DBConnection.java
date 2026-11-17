package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String CONFIG_FILE = "db.properties";

    private static Properties props;

    private static void loadConfig() throws SQLException {
        if (props != null) {
            return;
        }
        Properties loaded = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            loaded.load(in);
        } catch (IOException e) {
            throw new SQLException("Cannot read " + CONFIG_FILE
                    + ". Copy db.properties.example to db.properties and fill in your password.", e);
        }
        props = loaded;
    }

    public static Connection getConnection() throws SQLException {
        loadConfig();
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"));
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            System.out.println("Connected to: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
