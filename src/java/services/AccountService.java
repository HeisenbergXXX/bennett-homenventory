package services;

import dataaccess.UsersDB;
import models.Users;

public class AccountService {
    public Users login(String username, String password) {
        UsersDB udb = new UsersDB();

        try {
            Users user = udb.get(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception ignored) {
        }
            return null;
    }

}
