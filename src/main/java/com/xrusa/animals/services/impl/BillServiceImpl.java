package main.java.com.xrusa.animals.services.impl;

import java.util.List;

import main.java.com.xrusa.animals.entities.Bill;
import main.java.com.xrusa.animals.enums.BillStatus;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.filereaders.FileAccess;
import main.java.com.xrusa.animals.services.BillService;

public class BillServiceImpl implements BillService {

  private final FileAccess<Bill> billReader;

  public BillServiceImpl(FileAccess<Bill> billReader) {
    this.billReader = billReader;
  }

  @Override
  public List<Bill> getAllBills() {
    return billReader.read();
  }

  @Override
  public void createNewBill(Bill bill, Role role){
    if(role == Role.COMPANY_CUSTOMER) {
      billReader.saveOrReplace(bill);
    }
    throw new RuntimeException("You do not have permission to access this resource!");
  }

  @Override
  public void changeBillStatus(Role role, BillStatus status, String billId) {
    billReader.read().stream()
      .filter(so -> so.getBillId().equals(billId))
      .findFirst()
      .ifPresent(so -> so.setStatus(status));
    Bill bill = billReader.read().stream()
      .filter(so -> so.getBillId().equals(billId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Bill not found: " + billId));
    bill.setStatus(status);
    billReader.saveOrReplace(bill);
  }
}
