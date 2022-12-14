package com.poit.myairbnb.wtlab4.repository;

import com.poit.myairbnb.wtlab4.entity.User;
import com.poit.myairbnb.wtlab4.connection.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public void save(User user) throws SQLException {
        String saveQuery = "INSERT INTO public.\"Users\" (name, password, email, role_id)"
            + "VALUES (?, ?, ?, ?);";

        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = c.prepareStatement(saveQuery);

        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        statement.setLong(4, user.getRoleId());

        statement.execute();

        ConnectionPool.getInstance().releaseConnection(c);
    }

    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM public.\"Users\" WHERE email = ?;";
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, email);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        ConnectionPool.getInstance().releaseConnection(c);
        resultSet.next();
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("password"),
            resultSet.getString("email"),
            resultSet.getLong("role_id"));
    }
}
