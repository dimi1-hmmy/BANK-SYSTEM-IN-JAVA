package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.enums.Role;

public interface UserService {

  List<User> getAllUsers();

  User loginAndGetUser(String username, String password, Role role);

}
