package main.java.com.xrusa.animals.factories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ServiceFactory {

  private static final Map<Class<?>, Object> INSTANCES = new ConcurrentHashMap<>();

  private ServiceFactory() {}

  @SuppressWarnings("unchecked")
  public static <T> T get(Class<T> serviceType, ServiceCreator<T> creator) {
    Object existing = INSTANCES.get(serviceType);
    if (existing != null) {
      return (T) existing;
    }

    synchronized (ServiceFactory.class) {
      existing = INSTANCES.get(serviceType);
      if (existing != null) {
        return (T) existing;
      }

      T created = creator.create(FileReaderFactory.getInstance());
      INSTANCES.put(serviceType, created);
      return created;
    }
  }
}
