package main.java.com.xrusa.animals.filereaders.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.Transaction;
import main.java.com.xrusa.animals.enums.TransactionType;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class TransactionAccess extends FileAccess<Transaction> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private List<Transaction> cache;

  public TransactionAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<Transaction> read() {
    if (cache != null) {
      return cache;
    }

    List<Transaction> transactions = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 5) continue;
      if ("transactionId".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        String id = parts[0].trim();
        TransactionType type = TransactionType.valueOf(parts[1].trim());
        BigDecimal amount = new BigDecimal(parts[2].trim());
        Date date = dateFormat.parse(parts[3].trim());
        String description = parts[4].trim();
        String customerId = parts[5].trim();
        String ibanFrom = parts[6].trim();
        String ibanTo = parts[7].trim();
        transactions.add(new Transaction(id, type, amount, date, description, customerId, ibanFrom, ibanTo));
      } catch (ParseException | IllegalArgumentException e) {
        System.err.println("Invalid transaction row skipped: " + Arrays.toString(parts));
      }
    }
    cache = transactions;
    return cache;
  }

  @Override
  public void saveOrReplace(Transaction transaction) {
    datasource.writeOrReplace(
      filePath,
      transaction.getTransactionId(),
      toRow(transaction)
    );
    reload();
  }

  private String[] toRow(Transaction transaction) {
    return new String[] {
      transaction.getTransactionId(),
      transaction.getType().name(),
      transaction.getAmount().toPlainString(),
      dateFormat.format(transaction.getTimestamp()),
      transaction.getDescription(),
      transaction.getIbanFrom()
    };
  }

  public void reload() {
    cache = null;
  }

}
