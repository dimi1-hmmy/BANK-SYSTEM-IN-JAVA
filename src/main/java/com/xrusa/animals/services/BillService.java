package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.Bill;
import main.java.com.xrusa.animals.enums.BillStatus;
import main.java.com.xrusa.animals.enums.Role;

public interface BillService {

  List<Bill> getAllBills();

  void createNewBill(Bill bill, Role role);

  void changeBillStatus(Role role, BillStatus status, String billId);

}
