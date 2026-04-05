package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.CustomerService;
import main.java.com.xrusa.animals.services.impl.CustomerServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class CustomerServiceFactory {

  private CustomerServiceFactory() {}

  public static CustomerService getCustomerService() {
    return ServiceFactory.get(
      CustomerService.class,
      rf -> new CustomerServiceImpl(
        rf.getReader(EntityType.CUSTOMER.name(), FilePaths.CUSTOMERS)
      )
    );
  }

}