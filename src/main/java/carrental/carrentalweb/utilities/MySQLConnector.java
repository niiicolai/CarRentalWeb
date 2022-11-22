package carrental.carrentalweb.utilities;

import java.sql.Connection;
import java.sql.*;

public class MySQLConnector {
    Connection conn;
    public static MySQLConnector instance;

    public static MySQLConnector getInstance() {
        if (instance == null) {
            instance = new MySQLConnector();
        }
        return instance;
    }

    public Connection getConnection(String url, String username, String password) {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("SQL Connector: Cannot connect to database");
                e.printStackTrace();
            }
        }
        return conn;
    }
}