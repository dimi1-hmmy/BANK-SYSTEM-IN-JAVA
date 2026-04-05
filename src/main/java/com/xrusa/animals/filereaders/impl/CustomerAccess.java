package main.java.com.xrusa.animals.filereaders.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.Customer;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class CustomerAccess extends FileAccess<Customer> {

  private final String filePath;
  private List<Customer> cache;

  public CustomerAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<Customer> read() {
    if (cache != null) {
      return cache;
    }
    List<Customer> customers = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 7 || "userId".equalsIgnoreCase(parts[0].trim())) continue;

      Customer customer = new Customer(
        parts[0].trim(),
        parts[1].trim(),
        parts[2].trim(),
        Role.valueOf(parts[3].trim()),
        parts[4].trim(),
        parts[5].trim(),
        parts[6].trim(),
        parts[7].trim(),
        null
      );
      customers.add(customer);
    }
    cache = customers;
    return cache;
  }

  public void reload() {
    cache = null;
  }

  @Override
  public void saveOrReplace(Customer customer){
    //do nothing
  }

}