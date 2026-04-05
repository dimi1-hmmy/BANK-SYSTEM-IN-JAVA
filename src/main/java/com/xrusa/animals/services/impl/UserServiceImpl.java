package main.java.com.xrusa.animals.services.impl;

import java.util.List;

import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.UserService;

public class UserServiceImpl implements UserService {

  private final FileAccess<User> userReader;

  public UserServiceImpl(FileAccess<User> userReader) {
    this.userReader = userReader;
  }

  @Override
  public List<User> getAllUsers() {
    return userReader.read();
  }

  @Override
  public User loginAndGetUser(String username, String password, Role role) {
    for (User user : getAllUsers()) {
      if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

}
