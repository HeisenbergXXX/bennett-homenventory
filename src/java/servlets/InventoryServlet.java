package servlets;

import models.Items;
import models.Users;
import services.CategoriesService;
import services.ItemsService;
import services.UsersService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InventoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            request.setAttribute("items", user.getItemsCollection());
            request.setAttribute("categories", new CategoriesService().getAll());
        } catch (Exception e) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("message1", "error");
        }
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            request.setAttribute("action", "edit");
            try {
                String itemid = request.getParameter("itemID");
                Items item = new ItemsService().get(Integer.parseInt(itemid));
                request.setAttribute("item", item);
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("actionError", "m"+ex.getMessage());
            }
        }

        request.getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        ItemsService is = new ItemsService();
//        UsersService us = new UsersService();

        String action = request.getParameter("action");
        String categoryID = request.getParameter("category");
        String oldCategoryID = request.getParameter("oldCategory");
        String itemName = request.getParameter("itemName");
        String price = request.getParameter("itemPrice");
        String itemID = request.getParameter("itemID");

        try{
            switch (action) {
                case "add":
                    is.insert(Integer.parseInt(categoryID), itemName, Double.valueOf(price), user.getUsername());
                    request.setAttribute("actionMsg", "Item: " + itemName + " added successfully!");
                    break;
                case "delete":
                    Items item = is.get(Integer.parseInt(itemID));
                    is.delete(item);
                    request.setAttribute("actionMsg", "Item " + item.getItemName() + " deleted!");
                    break;
                case "update":
                    is.update(Integer.parseInt(oldCategoryID),Integer.parseInt(itemID), itemName, Double.valueOf(price));
                    request.setAttribute("actionMsg", "Item " + itemName + " updated!");
                    break;
                default:
                    request.setAttribute("actionError", "Invalid action!");
                    break;
            }

        } catch (Exception e) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("actionError", e.getMessage());
        }

        try {
//            request.setAttribute("firstname", user.getFirstName());
//            request.setAttribute("lastname", user.getLastName());
            request.setAttribute("user", user);
            //can't use user.getItemsCollection() because it's a lazy load? does not update upon adding/deleting items
            request.setAttribute("items", is.getItemsByOwner(user.getUsername()));
            request.setAttribute("categories", new CategoriesService().getAll());
        } catch (Exception e) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("actionError", "m"+e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
