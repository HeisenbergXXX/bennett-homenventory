package services;

import dataaccess.UsersDB;
import java.util.List;
import models.Users;

public class UsersService {
    public List<Users> getAll() throws Exception {
        UsersDB db = new UsersDB();
        return db.getAll();
    }

    public Users get(String username) throws Exception {
        UsersDB db = new UsersDB();
        return db.get(username);
    }

    public void update(Users user) throws Exception {
        UsersDB db = new UsersDB();
        db.update(user);
    }

    public void delete(Users user) throws Exception {
        UsersDB db = new UsersDB();
        db.delete(user);
    }

    public void insert(Users user) throws Exception {
        UsersDB db = new UsersDB();
        db.insert(user);
    }
}
