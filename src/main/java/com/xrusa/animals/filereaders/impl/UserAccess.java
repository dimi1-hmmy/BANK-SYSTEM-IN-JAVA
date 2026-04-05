package main.java.com.xrusa.animals.filereaders.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class UserAccess extends FileAccess<User> {

  private final String filePath;
  private List<User> cache;

  public UserAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<User> read() {
    if (cache != null) {
      return cache;
    }
    List<User> users = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 4 || "userId".equalsIgnoreCase(parts[0].trim())) continue;
      String userId = parts[0].trim();
      String username = parts[1].trim();
      String password = parts[2].trim();
      Role role = Role.valueOf(parts[3].trim());

      users.add(new User(userId, username, password, role));
    }
    cache = users;
    return cache;
  }

  public void reload() {
    cache = null;
  }

  @Override
  public void saveOrReplace(User user){
    //do nothing
  }

}
