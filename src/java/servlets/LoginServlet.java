package servlets;

import services.AccountService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import models.Users;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();
        if (request.getParameter("logout") != null) {
            request.setAttribute("message", "You have successfully logged out!");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountService as = new AccountService();
        Users user = as.login(username, password);

        if (user == null || username.equals("")) {
            request.setAttribute("username", username);
            request.setAttribute("errorMsg", "Invalid username or password");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        } else if (!user.getActive()) {
            request.setAttribute("username", username);
            request.setAttribute("errorMsg", "User is not active, contact your administrator!");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if (user.getIsAdmin()) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("inventory");
        }
    }
}
