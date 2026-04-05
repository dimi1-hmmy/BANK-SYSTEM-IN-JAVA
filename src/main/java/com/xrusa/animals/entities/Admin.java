package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.Role;

public class Admin extends User {

  private String adminId;

  public Admin(String adminId, String userId, String username, String password, Role role) {
      super(userId, username, password, role);
      this.adminId = adminId;
  }

  public String getAdminId() {
      return adminId;
  }

  public void setAdminId(String adminId) {
      this.adminId = adminId;
  }

  @Override
  public void setRole(Role role) {
        throw new UnsupportedOperationException("Admin role cannot be changed");
  }

}
