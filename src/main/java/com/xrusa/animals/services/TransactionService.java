package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.Transaction;
import main.java.com.xrusa.animals.enums.Role;

public interface TransactionService {

  List<Transaction> getAllTransactions(Role role, String customerId);

  void createTransaction(Transaction transaction);
    
}