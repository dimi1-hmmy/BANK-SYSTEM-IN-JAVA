package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.enums.BankAccountStatus;
import main.java.com.xrusa.animals.enums.Role;

public interface BankAccountService {

  List<BankAccount> getAllBankAccounts(Role role, String customerId);

  List<BankAccount> getAccountsByCustomerId(String customerId);

  BankAccount getAccountByIBan(String iban);

  void createOrReplaceBankAccount(BankAccount account);

  void changeBankAccountStatus(Role role, String iban, BankAccountStatus status);

}

