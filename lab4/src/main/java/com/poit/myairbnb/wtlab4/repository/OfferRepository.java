package com.poit.myairbnb.wtlab4.repository;

import com.poit.myairbnb.wtlab4.connection.ConnectionPool;
import com.poit.myairbnb.wtlab4.entity.Offer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferRepository {

    public void save(Offer offer) throws SQLException {
        String saveQuery = "INSERT INTO public.\"offers\" (hotel, beds_count)"
            + "VALUES (?, ?);";

        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = c.prepareStatement(saveQuery);

        statement.setString(1, offer.getHotel());
        statement.setInt(2, offer.getBedCount());
        statement.execute();

        ConnectionPool.getInstance().releaseConnection(c);
    }

    public List<Offer> findAll() throws SQLException {
        String query = "SELECT * FROM public.\"offers\";";
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = c.prepareStatement(query);
        statement.execute();
        ResultSet res = statement.getResultSet();
        List<Offer> offers = new ArrayList<>();

        while (res.next()) offers.add(new Offer(
                res.getInt("id"),
                res.getString("hotel"),
                res.getInt("beds_count")));

        ConnectionPool.getInstance().releaseConnection(c);

        return offers;
    }
}
