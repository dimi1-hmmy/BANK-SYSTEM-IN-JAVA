package main.java.com.xrusa.animals.filereaders.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.entities.Statement;
import main.java.com.xrusa.animals.entities.Transaction;
import main.java.com.xrusa.animals.enums.BankAccountStatus;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class BankAccountAccess extends FileAccess<BankAccount> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private List<BankAccount> cache;

  public BankAccountAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<BankAccount> read() {
    if(cache != null) {
      return cache;
    }

    List<BankAccount> accounts = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 7) continue;
      if ("iban".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        String iban = parts[0].trim();
        BigDecimal balance = new BigDecimal(parts[1].trim());
        BigDecimal interestRate = new BigDecimal(parts[2].trim());
        BankAccountStatus status = BankAccountStatus.valueOf(parts[3].trim().toUpperCase());
        Date createdAt = dateFormat.parse(parts[4].trim());
        String customerId = parts[5].trim();
        BigDecimal maintenanceFee = new BigDecimal(parts[6].trim());
        List<Transaction> transactions = new ArrayList<>();
        List<Statement> statements = new ArrayList<>();

        BankAccount account = new BankAccount(
          iban, balance, interestRate, status, createdAt,
          transactions, statements, customerId, maintenanceFee, null
        );
        accounts.add(account);

      } catch (ParseException | IllegalArgumentException e) {
        System.err.println("Invalid bank account row skipped: " + Arrays.toString(parts));
      }
    }
    cache = accounts;
    return cache;
  }

  @Override
  public void saveOrReplace(BankAccount account) {
    datasource.writeOrReplace(
      filePath,
      account.getIban(),
      toRow(account)
    );
    reload();
  }

  private String[] toRow(BankAccount account) {
    return new String[] {
      account.getIban(),
      account.getBalance().toPlainString(),
      account.getInterestRate().toPlainString(),
      account.getStatus().name(),
      dateFormat.format(account.getCreatedAt()),
      account.getCustomerId(),
      account.getMaintenanceFee().toPlainString()
    };
  }

  public void reload() {
    cache = null;
  }

}
