package servlets;

import models.Users;
import services.UsersService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if(!password.equals(confirmPassword)){
            request.setAttribute("errorMsg", "Passwords do not match");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        }

        UsersService us = new UsersService();
        Users user = new Users(username, password, email, firstName, lastName, true, false);
        try {
            if(us.get(username) != null){
                request.setAttribute("errorMsg", "User already exists!");
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
            } else {
                us.insert(user);
                request.setAttribute("message", "Registration successful for user: " + user.getUsername());
            }
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
        }

    }
}
