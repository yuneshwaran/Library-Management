package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbUtility {
    private static final String URL = "jdbc:postgresql://localhost:5433/library";
    private static final String USER = " ";
    private static final String PASSWORD = " ";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
