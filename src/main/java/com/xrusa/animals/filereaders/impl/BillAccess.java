package main.java.com.xrusa.animals.filereaders.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.Bill;
import main.java.com.xrusa.animals.enums.BillStatus;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class BillAccess extends FileAccess<Bill> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private List<Bill> cache;

  public BillAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<Bill> read() {
    if(cache != null) {
      return cache;
    }
    List<Bill> bills = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 6 || "billId".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        Bill bill = new Bill(
          parts[0].trim(),
          parts[1].trim(),
          new BigDecimal(parts[2].trim()),
          dateFormat.parse(parts[3].trim()),
          dateFormat.parse(parts[4].trim()),
          BillStatus.valueOf(parts[5].trim())
        );
        bills.add(bill);
      } catch (Exception ignored) {}
    }
    cache = bills;
    return cache;
  }

  @Override
  public void saveOrReplace(Bill bill) {
    datasource.writeOrReplace(
      filePath,
      bill.getBillId(),
      toRow(bill)
    );
    reload();
  }

  private String[] toRow(Bill bill) {
    return new String[] {
      bill.getBillId(),
      bill.getRfCode(),
      bill.getAmount().toPlainString(),
      dateFormat.format(bill.getIssueDate()),
      dateFormat.format(bill.getDueDate()),
      bill.getStatus().name()
    };
  }

  public void reload() {
    cache = null;
  }

}
