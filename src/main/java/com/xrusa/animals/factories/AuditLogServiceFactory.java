package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.AuditLogService;
import main.java.com.xrusa.animals.services.impl.AuditLogServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class AuditLogServiceFactory {

  private AuditLogServiceFactory() {}

  public static AuditLogService getAuditLogService() {
    return ServiceFactory.get(
      AuditLogService.class,
      rf -> new AuditLogServiceImpl(
        rf.getReader(EntityType.ADMIN.name(), FilePaths.AUDIT_LOGS)
      )
    );
  }

}
