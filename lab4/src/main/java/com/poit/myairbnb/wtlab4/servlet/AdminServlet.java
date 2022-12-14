package com.poit.myairbnb.wtlab4.servlet;

import com.poit.myairbnb.wtlab4.entity.Offer;
import com.poit.myairbnb.wtlab4.service.OfferService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/add-offer")
public class AdminServlet extends HttpServlet {
    private final OfferService offerService = new OfferService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        if (!request.getSession().getAttribute("role").equals(2)) return;

        offerService.save(new Offer(
            request.getParameter("hotel-name"),
            Integer.parseInt(request.getParameter("beds-count"), 10)
        ));
    }
}
