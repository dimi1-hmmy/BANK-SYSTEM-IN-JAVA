package main.java.com.xrusa.animals.filereaders.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.com.xrusa.animals.datasource.DataSource;
import main.java.com.xrusa.animals.entities.StandingOrder;
import main.java.com.xrusa.animals.enums.StandingOrderStatus;
import main.java.com.xrusa.animals.enums.StandingOrderType;
import main.java.com.xrusa.animals.filereaders.FileAccess;

public class StandingOrderAccess extends FileAccess<StandingOrder> {

  private final String filePath;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private List<StandingOrder> cache;

  public StandingOrderAccess(String filePath, DataSource datasource) {
    super(datasource);
    this.filePath = filePath;
  }

  @Override
  public List<StandingOrder> read() {
    if(cache != null) {
      return cache;
    }
    List<StandingOrder> orders = new ArrayList<>();
    List<String[]> rows = datasource.readAll(filePath);

    for (String[] parts : rows) {
      if (parts.length < 9 || "orderId".equalsIgnoreCase(parts[0].trim())) continue;

      try {
        StandingOrder order = new StandingOrder(
          parts[0].trim(),
          parts[1].trim(),
          dateFormat.parse(parts[2].trim()),
          parts[3].trim(),
          new BigDecimal(parts[4].trim()),
          StandingOrderStatus.valueOf(parts[5].trim()),
          StandingOrderType.valueOf(parts[6].trim()),
          parts[7].trim(),
          parts[8].trim(),
          parts[9].trim()
        );
        orders.add(order);
      } catch (Exception ignored) {}
    }
    cache = orders;
    return cache;
  }

  @Override
  public void saveOrReplace(StandingOrder order) {
    datasource.writeOrReplace(filePath, order.getOrderId(), toRow(order));
    reload();
  }

  private String[] toRow(StandingOrder order) {
    return new String[] {
      order.getOrderId(),
      order.getTitle(),
      dateFormat.format(order.getStartDate()),
      order.getFrequency(),
      order.getAmount().toPlainString(),
      order.getStatus().name(),
      order.getType().name(),
      order.getCustomerId(),
      order.getIbanFrom(),
      order.getIbanTo()
    };
  }

  public void reload() {
    cache = null;
  }

}
