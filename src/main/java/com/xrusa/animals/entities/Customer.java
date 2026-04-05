package main.java.com.xrusa.animals.entities;

import java.util.List;
import main.java.com.xrusa.animals.enums.Role;

public class Customer extends User {

    private String customerId;
    private String name;
    private String email;
    private String phone;
    private List<StandingOrder> standingOrders;

    public Customer(String userId, String username, String password, Role role, String customerId, String name, String email, String phone, List<StandingOrder> standingOrders) {
        super(userId, username, password, role);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.standingOrders = standingOrders;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<StandingOrder> getStandingOrders() {
        return standingOrders;
    }

    public void setStandingOrders(List<StandingOrder> standingOrders) {
        this.standingOrders = standingOrders;
    }

  @Override
  public void setUserId(String userId) {
    super.setUserId(userId);
  }
}
