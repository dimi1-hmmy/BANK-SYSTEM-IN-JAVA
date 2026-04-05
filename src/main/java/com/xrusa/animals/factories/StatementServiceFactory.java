package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.StatementService;
import main.java.com.xrusa.animals.services.impl.StatementServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class StatementServiceFactory {

  private StatementServiceFactory() {}

  public static StatementService getStatementService() {
    return ServiceFactory.get(
      StatementService.class,
      rf -> new StatementServiceImpl(
        rf.getReader(EntityType.STATEMENT.name(), FilePaths.STATEMENTS)
      )
    );
  }

}
