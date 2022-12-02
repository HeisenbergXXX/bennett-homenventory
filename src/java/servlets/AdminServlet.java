package servlets;


import models.Categories;
import models.Users;
import services.CategoriesService;
import services.UsersService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            request.setAttribute("users", new UsersService().getAll());
            request.setAttribute("categories", new CategoriesService().getAll());
        } catch (Exception e) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("actionError", "error");
        }
        String action = request.getParameter("action");
        String usernameS = request.getParameter("usernameS");
        String categoryID = request.getParameter("categoryID");

        if (action != null && action.equals("edit")) {
            request.setAttribute("action", "edit");
            try {
                request.setAttribute("userE", new UsersService().get(usernameS));
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("actionError", "m"+ex.getMessage());
            }
        } else if (action != null && action.equals("editCat")) {
            request.setAttribute("action", "editCat");
            try {
                request.setAttribute("categoryE", new CategoriesService().get(Integer.parseInt(categoryID)));
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("actionError", "m"+ex.getMessage());
            }
        }

        request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        UsersService us = new UsersService();
        CategoriesService cs = new CategoriesService();

        String categoryname = request.getParameter("categoryname");
        String categoryid = request.getParameter("categoryid");
        String categoryID = request.getParameter("categoryID");
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
                case "add":
                    Users newUser = new Users(username, password, email, firstname, lastname, activeD, isAdminD);
                    //check if user already exists
                    if (us.get(username) == null) {
                        us.insert(newUser);
                        request.setAttribute("actionMsg", "User: " + newUser.getUsername() + "  added successfully! (Default active and not admin)");
                    } else {
                        request.setAttribute("actionError", "User already exists!");
                    }
                    break;
                case "addCat":
                    Categories newCat = new Categories(Integer.parseInt(categoryid), categoryname);
                    cs.insert(newCat);
                    request.setAttribute("actionMsg", "Category: " + newCat.getCategoryName() + "  added successfully!");
                    break;
                case "delete":
                    Users userToDelete = us.get(usernameSelected);
                    //check if user is admin
                    if (userToDelete.getIsAdmin() && userToDelete.getUsername().equals(user.getUsername())) {
                        request.setAttribute("actionError", "As an admin, you can't delete yourself! but you can delete other admins.");
                    } else {
                        us.delete(userToDelete);
                        request.setAttribute("actionMsg", "User " + userToDelete.getUsername() + " deleted!");
                    }
                    break;
                case "deleteCat":
                    Categories catToDelete = cs.get(Integer.parseInt(categoryID));
                    cs.delete(catToDelete);
                    request.setAttribute("actionMsg", "Category: " + catToDelete.getCategoryName() + " deleted!");
                    break;
                case "update":
                    Users userToUpdate = new Users(username, password, email, firstname, lastname, active, isAdmin);
                    us.update(userToUpdate);
                    request.setAttribute("actionMsg", "User: " + userToUpdate.getUsername() + " updated successfully!");
                    break;
                case "updateCat":
                    Categories catToUpdate = new Categories(Integer.parseInt(categoryid), categoryname);
                    cs.update(catToUpdate);
                    request.setAttribute("actionMsg", "Category: " + catToUpdate.getCategoryName() + " updated successfully!");
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
            request.setAttribute("actionError", e.getMessage());
        }
        try {
            request.setAttribute("users", new UsersService().getAll());
            request.setAttribute("categories", new CategoriesService().getAll());
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("actionError", "m"+ex.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
