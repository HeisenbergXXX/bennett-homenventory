package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;

import java.util.Collection;
import java.util.List;

import dataaccess.UsersDB;
import models.Categories;
import models.Items;
import models.Users;

public class ItemsService {
    public List<Items> getAll() throws Exception {
        ItemsDB idb = new ItemsDB();
        return idb.getAll();
    }

    public Collection<Items> getItemsByOwner(String username) throws Exception {
        UsersDB udb = new UsersDB();
        Users user = udb.get(username);
        return user.getItemsCollection();
    }

    public Items get(int itemID) throws Exception {
        ItemsDB idb = new ItemsDB();
        return idb.get(itemID);
    }

    public void update(int catID, int itemID, String itemName, Double price) throws Exception {
        ItemsDB idb = new ItemsDB();
        Items item = idb.get(itemID);
        item.setCategory(new Categories(catID));
        item.setItemName(itemName);
        item.setPrice(price);
        idb.update(item);
    }

    public void delete(Items item) throws Exception {
        //add if (!item.getOwner().getUsername().equals(username)) …
        ItemsDB idb = new ItemsDB();
        idb.delete(item);
    }

    public void insert(int catID, String itemName, Double price, String owner) throws Exception {
        Items item = new Items(0, itemName, price);

        UsersDB udb = new UsersDB();
        Users user = udb.get(owner);
        item.setOwner(user);

        CategoriesDB cdb = new CategoriesDB();
        Categories cat = cdb.get(catID);
        item.setCategory(cat);

        ItemsDB idb = new ItemsDB();
        idb.insert(item);
    }

}
