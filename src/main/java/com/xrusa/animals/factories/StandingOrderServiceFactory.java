package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.StandingOrderService;
import main.java.com.xrusa.animals.services.impl.StandingOrderServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class StandingOrderServiceFactory {

  private StandingOrderServiceFactory() {}

  public static StandingOrderService getStandingOrderService() {
    return ServiceFactory.get(
      StandingOrderService.class,
      rf -> new StandingOrderServiceImpl(
        rf.getReader(EntityType.STANDING_ORDER.name(), FilePaths.STANDING_ORDERS),
        TransactionServiceFactory.getTransactionService(),
        BankAccountServiceFactory.getBankAccountService(),
        CustomerServiceFactory.getCustomerService()
      )
    );
  }
}