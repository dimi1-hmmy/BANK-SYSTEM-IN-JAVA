package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.Customer;
import main.java.com.xrusa.animals.enums.Role;

public interface CustomerService {

  List<Customer> getAllCustomers(Role role);

  Customer findCustomerById(String customerId, Role role);

  Customer findCustomerByUserId(String userId);

}