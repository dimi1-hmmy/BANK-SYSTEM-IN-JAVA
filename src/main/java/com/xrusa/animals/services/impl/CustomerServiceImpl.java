package main.java.com.xrusa.animals.services.impl;

import java.util.List;

import main.java.com.xrusa.animals.entities.Customer;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.CustomerService;

public class CustomerServiceImpl implements CustomerService {

  private final FileAccess<Customer> customerReader;

  public CustomerServiceImpl(FileAccess<Customer> customerReader) {
    this.customerReader = customerReader;
  }

  @Override
  public List<Customer> getAllCustomers(Role role) {
    if(role == Role.ADMIN){
      return customerReader.read();
    }
    throw new RuntimeException("You do not have permission to access this resource!");
  }

  @Override
  public Customer findCustomerById(String customerId, Role role) {
    if(role == Role.ADMIN){
      return customerReader.read().stream().filter(c -> c.getCustomerId().equals(customerId)).findFirst().orElse(null);
    }
    throw new RuntimeException("You do not have permission to access this resource!");
  }

  @Override
  public Customer findCustomerByUserId(String userId) {
    return customerReader.read().stream().filter(c -> c.getUserId().equals(userId)).findFirst().orElse(null);
  }

}
