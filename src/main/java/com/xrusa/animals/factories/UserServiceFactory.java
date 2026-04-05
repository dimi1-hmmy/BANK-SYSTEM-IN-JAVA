package main.java.com.xrusa.animals.factories;

import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.services.UserService;
import main.java.com.xrusa.animals.services.impl.UserServiceImpl;
import main.java.com.xrusa.animals.utils.FilePaths;

public class UserServiceFactory {

  private UserServiceFactory() {}

  public static UserService getUserService() {
    return ServiceFactory.get(
      UserService.class,
      rf -> new UserServiceImpl(
        rf.getReader(EntityType.USER.name(), FilePaths.USERS)
      )
    );
  }

}
