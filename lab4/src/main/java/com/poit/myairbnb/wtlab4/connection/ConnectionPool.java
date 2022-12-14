package com.poit.myairbnb.wtlab4.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instance.initialize();
                }

            }
        }
        return instance;
    }

    private ConnectionPool() { }

    public void initialize() {
        availableConnections = new ArrayBlockingQueue<>(10);
        usedConnections = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < 10; i++) {
            Connection connection = ConnectionProvider.createConnection();
            if (connection != null) {
                availableConnections.add(connection);
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void destroy() {
        try {
            for (Connection connection : availableConnections) {
                connection.close();
            }
            for (Connection connection : usedConnections) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }

    }
}
