package main.java.com.xrusa.animals.services.impl;

import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.entities.Customer;
import main.java.com.xrusa.animals.entities.StandingOrder;
import main.java.com.xrusa.animals.entities.Transaction;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.enums.StandingOrderStatus;
import main.java.com.xrusa.animals.enums.TransactionType;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.BankAccountService;
import main.java.com.xrusa.animals.services.CustomerService;
import main.java.com.xrusa.animals.services.StandingOrderService;
import main.java.com.xrusa.animals.services.TransactionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StandingOrderServiceImpl implements StandingOrderService {

  private final FileAccess<StandingOrder> standingOrderReader;
  private final TransactionService transactionService;
  private final BankAccountService bankAccountService;
  private final CustomerService customerService;

  public StandingOrderServiceImpl(FileAccess<StandingOrder> standingOrderReader,
                                  TransactionService transactionService,
                                  BankAccountService bankAccountService,
                                  CustomerService customerService) {
    this.standingOrderReader = standingOrderReader;
    this.transactionService = transactionService;
    this.bankAccountService = bankAccountService;
    this.customerService = customerService;
  }

  @Override
  public List<StandingOrder> getAllStandingOrders(Role role) {
    if(role == Role.ADMIN){
      return standingOrderReader.read();
    }
    throw new RuntimeException("You do not have permission to access this resource!");
  }

  @Override
  public void changeStatndingOrderStatus(Role role, StandingOrderStatus status, String orderId) {
    standingOrderReader.read().stream()
      .filter(so -> so.getOrderId().equals(orderId))
      .findFirst()
      .ifPresent(so -> so.setStatus(status));
    StandingOrder order = standingOrderReader.read().stream()
      .filter(so -> so.getOrderId().equals(orderId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Standing order not found: " + orderId));

    order.setStatus(status);
    standingOrderReader.saveOrReplace(order);
    if(status == StandingOrderStatus.COMPLETE) {
      BankAccount fromAccount = bankAccountService.getAccountByIBan(order.getIbanFrom());
      BankAccount toAccount = bankAccountService.getAccountByIBan(order.getIbanTo());
      setBalance(fromAccount, toAccount,order.getAmount());
      Transaction transaction = new Transaction(UUID.randomUUID().toString(), TransactionType.TRANSFER, order.getAmount(), new Date(), "random description", fromAccount.getCustomerId(), fromAccount.getIban(), toAccount.getIban());
      transactionService.createTransaction(transaction);
    }
  }

  @Override
  public List<StandingOrder> getStandingOrdersByCustomerId(String userId) {
    return standingOrderReader.read().stream().filter(so -> so.getCustomerId().equals(customerService.findCustomerByUserId(userId).getCustomerId())).toList();
  }

  @Override
  public void createStandingOrder(StandingOrder standingOrder, Role role){
    standingOrderReader.saveOrReplace(standingOrder);
  }

  private void setBalance(BankAccount from, BankAccount to, BigDecimal amount) {
    if(from.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0){
      throw new RuntimeException("Insufficient funds!");
    }
    from.setBalance(from.getBalance().subtract(amount));
    to.setBalance(to.getBalance().add(amount));
    bankAccountService.createOrReplaceBankAccount(from);
    bankAccountService.createOrReplaceBankAccount(to);
  }

}
