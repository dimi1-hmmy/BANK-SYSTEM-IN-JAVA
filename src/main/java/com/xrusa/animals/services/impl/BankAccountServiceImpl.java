package main.java.com.xrusa.animals.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.enums.BankAccountStatus;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

  private final FileAccess<BankAccount> bankAccountReader;

  public BankAccountServiceImpl(FileAccess<BankAccount> bankAccountReader) {
      this.bankAccountReader = bankAccountReader;
  }

  @Override
  public List<BankAccount> getAllBankAccounts(Role role, String customerId){
    if(role == Role.ADMIN){
      return bankAccountReader.read();
    } else {
      return getAccountsByCustomerId(customerId);
    }
  }

  @Override
  public List<BankAccount> getAccountsByCustomerId(String customerId) {
    List<BankAccount> allAccounts = bankAccountReader.read();

    return allAccounts.stream()
      .filter(account -> account.getCustomerId().equals(customerId))
      .collect(Collectors.toList());
  }

  @Override
  public void createOrReplaceBankAccount(BankAccount account){
    bankAccountReader.saveOrReplace(account);
  }

  @Override
  public BankAccount getAccountByIBan(String iban){
    return bankAccountReader.read().stream()
      .filter(so -> so.getIban().equals(iban))
      .findFirst()
      .orElse(null);
  }

  @Override
  public void changeBankAccountStatus(Role role, String iban, BankAccountStatus status) {
    if(role == Role.ADMIN){
      bankAccountReader.read().stream()
        .filter(so -> so.getIban().equals(iban))
        .findFirst()
        .ifPresent(so -> so.setStatus(status));
    } else {
      throw new RuntimeException("You do not have permission to access this resource!");
    }
    BankAccount account = bankAccountReader.read().stream()
      .filter(so -> so.getIban().equals(iban))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Bank account not found: " + iban));

    account.setStatus(status);
    bankAccountReader.saveOrReplace(account);
  }

}
