package com.poit.myairbnb.wtlab4.servlet;

import com.poit.myairbnb.wtlab4.entity.User;
import com.poit.myairbnb.wtlab4.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "registrationServlet", value = "/register")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        User user = new User(
            request.getParameter("name"),
            request.getParameter("password"),
            request.getParameter("email"));

        userService.register(user);
    }
}
