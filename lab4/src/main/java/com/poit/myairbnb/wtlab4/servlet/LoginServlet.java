package com.poit.myairbnb.wtlab4.servlet;

import com.poit.myairbnb.wtlab4.entity.User;
import com.poit.myairbnb.wtlab4.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        User u = userService.login(
            request.getParameter("username"),
            request.getParameter("password"));

        RequestDispatcher dispatcher = null;

        if (u != null) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("name", u.getName());
            httpSession.setAttribute("role", u.getRoleId());
            dispatcher = request.getRequestDispatcher("index.jsp");
        } else {
            request.setAttribute("status", "fail");
            dispatcher = request.getRequestDispatcher("login.jsp");
        }

        dispatcher.forward(request, response);
    }
}
