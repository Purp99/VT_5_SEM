package com.poit.myairbnb.wtlab4.servlet;

import com.poit.myairbnb.wtlab4.service.OfferService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OffersServlet", value = "offers")
public class OffersServlet extends HttpServlet {

    private final OfferService offerService = new OfferService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        offerService.findAll().forEach(o -> {
            try {
                PrintWriter writer = response.getWriter();
                writer.println(o.getHotel());
                writer.println(o.getBedCount() + '\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
