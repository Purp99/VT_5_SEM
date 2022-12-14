package com.poit.myairbnb.wtlab4.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    static Connection createConnection() {
        Connection c = null;
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/myairbnb_db";
            String dbUser = "postgres";
            String dbPassword = "abc123";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException | ClassNotFoundException ignored) {

        }
        return c;
    }
}
