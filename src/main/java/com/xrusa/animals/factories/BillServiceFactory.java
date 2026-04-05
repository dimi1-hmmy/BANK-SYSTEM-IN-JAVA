package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.BillService;
import main.java.com.xrusa.animals.services.impl.BillServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class BillServiceFactory {

  private BillServiceFactory() {}

  public static BillService getBillService() {
    return ServiceFactory.get(
      BillService.class,
      rf -> new BillServiceImpl(
        rf.getReader(EntityType.BILL.name(), FilePaths.BILLS)
      )
    );
  }

}
