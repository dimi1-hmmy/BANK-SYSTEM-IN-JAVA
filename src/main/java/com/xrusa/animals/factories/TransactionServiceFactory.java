package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.TransactionService;
import main.java.com.xrusa.animals.services.impl.TransactionServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class TransactionServiceFactory {

  private TransactionServiceFactory() {}

  public static TransactionService getTransactionService() {
    return ServiceFactory.get(
      TransactionService.class,
      rf -> new TransactionServiceImpl(
        rf.getReader(EntityType.TRANSACTION.name(), FilePaths.TRANSACTIONS), BankAccountServiceFactory.getBankAccountService()
      )
    );
  }

}
