package main.java.com.xrusa.animals.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import main.java.com.xrusa.animals.datasource.impl.CsvDataSource;
import main.java.com.xrusa.animals.enums.EntityType;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.filereaders.impl.AuditLogAccess;
import main.java.com.xrusa.animals.filereaders.impl.BankAccountAccess;
import main.java.com.xrusa.animals.filereaders.impl.BillAccess;
import main.java.com.xrusa.animals.filereaders.impl.CustomerAccess;
import main.java.com.xrusa.animals.filereaders.impl.StandingOrderAccess;
import main.java.com.xrusa.animals.filereaders.impl.StatementAccess;
import main.java.com.xrusa.animals.filereaders.impl.TransactionAccess;
import main.java.com.xrusa.animals.filereaders.impl.UserAccess;

public class FileReaderFactory {

  private static final FileReaderFactory INSTANCE = new FileReaderFactory();

  private final Map<String, Function<String, FileAccess<?>>> registry = new HashMap<>();

  private FileReaderFactory() {
    register(EntityType.USER.name(), path -> new UserAccess(path, CsvDataSource.getInstance()));
    register(EntityType.TRANSACTION.name(), path -> new TransactionAccess(path, CsvDataSource.getInstance()));
    register(EntityType.BANK_ACCOUNT.name(), path -> new BankAccountAccess(path, CsvDataSource.getInstance()));
    register(EntityType.CUSTOMER.name(), path -> new CustomerAccess(path, CsvDataSource.getInstance()));
    register(EntityType.STANDING_ORDER.name(), path -> new StandingOrderAccess(path, CsvDataSource.getInstance()));
    register(EntityType.STATEMENT.name(), path -> new StatementAccess(path, CsvDataSource.getInstance()));
    register(EntityType.AUDIT_LOG.name(), path -> new AuditLogAccess(path, CsvDataSource.getInstance()));
    register(EntityType.BILL.name(), path -> new BillAccess(path, CsvDataSource.getInstance()));

  }

  public static FileReaderFactory getInstance() {
    return INSTANCE;
  }

  public void register(String key, Function<String, FileAccess<?>> creator) {
    registry.put(key.toUpperCase(), creator);
  }

  public <T> FileAccess<T> getReader(String key, String filePath) {
    Function<String, FileAccess<?>> creator = registry.get(key.toUpperCase());
    if (creator == null) {
      throw new IllegalArgumentException("No reader registered for type: " + key);
    }
    return (FileAccess<T>) creator.apply(filePath);
  }

}
