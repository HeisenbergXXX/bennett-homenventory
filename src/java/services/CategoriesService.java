package services;

import dataaccess.CategoriesDB;
import java.util.List;
import models.Categories;

public class CategoriesService {
    public List<Categories> getAll() throws Exception {
        CategoriesDB db = new CategoriesDB();
        return db.getAll();
    }

    public Categories get(int categoryID) throws Exception {
        CategoriesDB db = new CategoriesDB();
        return db.get(categoryID);
    }

    public void update(Categories category) throws Exception {
        CategoriesDB db = new CategoriesDB();
        db.update(category);
    }

    public void delete(Categories category) throws Exception {
        CategoriesDB db = new CategoriesDB();
        db.delete(category);
    }

    public void insert(Categories category) throws Exception {
        CategoriesDB db = new CategoriesDB();
        db.insert(category);
    }
}
