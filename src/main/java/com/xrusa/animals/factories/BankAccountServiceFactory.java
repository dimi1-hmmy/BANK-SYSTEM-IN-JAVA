package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.BankAccountService;
import main.java.com.xrusa.animals.services.impl.BankAccountServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class BankAccountServiceFactory {

  private BankAccountServiceFactory() {}

  public static BankAccountService getBankAccountService() {
    return ServiceFactory.get(
      BankAccountService.class,
      rf -> new BankAccountServiceImpl(
        rf.getReader(EntityType.BANK_ACCOUNT.name(), FilePaths.BANK_ACCOUNTS)
      )
    );
  }

}
