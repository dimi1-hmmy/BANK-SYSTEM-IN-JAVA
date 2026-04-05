package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.BankAccountStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankAccount {

  private String iban;
  private BigDecimal balance;
  private BigDecimal interestRate;
  private BankAccountStatus status;
  private Date createdAt;
  private List<Transaction> transactions;
  private List<Statement> statements;
  private String customerId;
  private BigDecimal maintenanceFee;
  private List<Bill> bills;

  public BankAccount() {
  }

  public BankAccount(String iban, BigDecimal balance, BigDecimal interestRate, BankAccountStatus status, Date createdAt, List<Transaction> transactions, List<Statement> statements, String customerId, BigDecimal maintenanceFee, List<Bill> bills) {
    this.iban = iban;
    this.balance = balance;
    this.interestRate = interestRate;
    this.status = status;
    this.createdAt = createdAt;
    this.transactions = transactions;
    this.statements = statements;
    this.customerId = customerId;
    this.maintenanceFee = maintenanceFee;
    this.bills = bills;
  }

  public String getIban() {
      return iban;
  }

  public void setIban(String iban) {
      this.iban = iban;
  }

  public BigDecimal getBalance() {
      return balance;
  }

  public void setBalance(BigDecimal balance) {
      this.balance = balance;
  }

  public BigDecimal getInterestRate() {
      return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
      this.interestRate = interestRate;
  }

  public BankAccountStatus getStatus() {
      return status;
  }

  public void setStatus(BankAccountStatus status) {
      this.status = status;
  }

  public Date getCreatedAt() {
      return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
  }

  public List<Transaction> getTransactions() {
      return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
      this.transactions = transactions;
  }

  public List<Statement> getStatements() {
      return statements;
  }

  public void setStatements(List<Statement> statements) {
      this.statements = statements;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
   this.customerId = customerId;
  }

  public BigDecimal getMaintenanceFee() {
    return maintenanceFee;
  }

  public void setMaintenanceFee(BigDecimal maintenanceFee) {
    this.maintenanceFee = maintenanceFee;
  }

  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

}