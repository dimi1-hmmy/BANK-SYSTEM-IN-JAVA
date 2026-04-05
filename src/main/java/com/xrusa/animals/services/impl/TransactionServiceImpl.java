package main.java.com.xrusa.animals.services.impl;

import java.math.BigDecimal;
import java.util.List;

import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.BankAccountService;
import main.java.com.xrusa.animals.services.TransactionService;
import main.java.com.xrusa.animals.entities.Transaction;

public class TransactionServiceImpl implements TransactionService {

  private final FileAccess<Transaction> transactionReader;
  private final BankAccountService bankAccountService;

  public TransactionServiceImpl(FileAccess<Transaction> transactionReader,
                                BankAccountService bankAccountService) {
      this.transactionReader = transactionReader;
      this.bankAccountService = bankAccountService;
  }

  @Override
  public List<Transaction> getAllTransactions(Role role, String customerId) {
    if(role == Role.ADMIN){
      return transactionReader.read();
    }
    return transactionReader.read().stream().filter(t -> t.getIbanFrom().equals(customerId)).collect(java.util.stream.Collectors.toList());
  }

  @Override
  public void createTransaction(Transaction transaction) {
    transactionReader.saveOrReplace(transaction);
    BankAccount fromAccount = bankAccountService.getAccountByIBan(transaction.getIbanFrom());
    BankAccount toAccount = bankAccountService.getAccountByIBan(transaction.getIbanTo());
    setBalance(fromAccount, toAccount,transaction.getAmount());
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