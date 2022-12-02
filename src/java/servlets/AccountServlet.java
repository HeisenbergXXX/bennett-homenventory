package servlets;

import models.Users;
import services.UsersService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            request.setAttribute("user", new UsersService().get(user.getUsername()));
        } catch (Exception e) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("actionError", "error");
        }
        String action = request.getParameter("action");
        String usernameS = request.getParameter("usernameS");
        if (action != null && action.equals("edit")) {
            request.setAttribute("action", "edit");
            try {
                request.setAttribute("userE", new UsersService().get(usernameS));
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("actionError", "m"+ex.getMessage());
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        UsersService us = new UsersService();

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String usernameSelected = request.getParameter("usernameS");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        boolean active = request.getParameter("active") != null;
        boolean isAdmin = request.getParameter("isAdmin") != null;
        final boolean activeD = true;
        final boolean isAdminD = false;

        try {
            switch (action) {
                case "update":
                    Users userToUpdate = new Users(username, password, email, firstname, lastname, active, isAdmin);
                    us.update(userToUpdate);
                    request.setAttribute("actionMsg", "User " + userToUpdate.getUsername() + " updated successfully!");
                    break;
                case "cancel":
                    request.setAttribute("action", null);
                    request.setAttribute("actionMsg", "Action cancelled!");
                    break;
                default:
                    request.setAttribute("actionError", "Invalid action!");
                    break;
            }
        } catch(Exception e){
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("actionError", "error");
        }
        try {
            request.setAttribute("user", new UsersService().get(user.getUsername()));
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("actionError", "m"+ex.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

}
