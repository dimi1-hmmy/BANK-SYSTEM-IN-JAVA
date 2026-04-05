package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.StandingOrderStatus;
import main.java.com.xrusa.animals.enums.StandingOrderType;

import java.math.BigDecimal;
import java.util.Date;

public class StandingOrder {

  private String orderId;
  private String title;
  private Date startDate;
  private String frequency;
  private BigDecimal amount;
  private StandingOrderStatus status;
  private StandingOrderType type;
  private String customerId;
  private String ibanFrom;
  private String ibanTo;

  public StandingOrder(String orderId, String title, Date startDate, String frequency,
        BigDecimal amount, StandingOrderStatus status, StandingOrderType type,
        String customerId, String ibanFrom, String ibanTo) {
    this.orderId = orderId;
    this.title = title;
    this.startDate = startDate;
    this.frequency = frequency;
    this.amount = amount;
    this.status = status;
    this.type = type;
    this.customerId = customerId;
    this.ibanFrom = ibanFrom;
    this.ibanTo = ibanTo;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getIbanFrom() {
    return ibanFrom;
  }

  public void setIbanFrom(String ibanFrom) {
    this.ibanFrom = ibanFrom;
  }

  public String getIbanTo() {
    return ibanTo;
  }

  public void setIbanTo(String ibanTo) {
    this.ibanTo = ibanTo;
  }

  public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
  public void setTitle(String title) {
      this.title = title;
  }
  public void setStartDate(Date startDate) {
      this.startDate = startDate;
  }
  public void setFrequency(String frequency) {
      this.frequency = frequency;
  }
  public void setAmount(BigDecimal amount) {
      this.amount = amount;
  }
  public void setStatus(StandingOrderStatus status) {
      this.status = status;
  }
  public void setType(StandingOrderType type) {
      this.type = type;
  }

  public String getOrderId() {
      return orderId;
  }
  public String getTitle() {
      return title;
  }
  public Date getStartDate() {
      return startDate;
  }
  public String getFrequency() {
      return frequency;
  }
  public BigDecimal getAmount() {
      return amount;
  }
  public StandingOrderStatus getStatus() {
      return status;
  }
  public StandingOrderType getType() {
      return type;
  }
}
