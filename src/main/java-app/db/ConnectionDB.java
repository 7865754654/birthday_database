package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.DBconfig;

public class ConnectionDB {

    static String url = DBconfig.getURL();
    static String nameDB = DBconfig.getName();
    static String passwordDB = DBconfig.getPassword();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, nameDB, passwordDB);
    }
}

