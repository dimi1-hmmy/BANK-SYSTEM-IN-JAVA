package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

  private String transactionId;
  private TransactionType type;
  private BigDecimal amount;
  private Date timestamp;
  private String description;
  private String customerId;
  private String ibanFrom;
  private String ibanTo;

  public Transaction(String transactionId,
                     TransactionType type,
                     BigDecimal amount,
                     Date timestamp,
                     String description,
                     String customerId,
                     String ibanFrom,
                     String ibanTo) {
    this.transactionId = transactionId;
    this.type = type;
    this.amount = amount;
    this.timestamp = timestamp;
    this.description = description;
    this.customerId = customerId;
    this.ibanFrom = ibanFrom;
    this.ibanTo = ibanTo;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerIdTo) {
    this.customerId = customerIdTo;
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
}
