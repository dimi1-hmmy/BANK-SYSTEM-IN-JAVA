package main.java.com.xrusa.animals.entities;

import main.java.com.xrusa.animals.enums.BillStatus;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {

    private String billId;
    private String rfCode;
    private BigDecimal amount;
    private Date issueDate;
    private Date dueDate;
    private BillStatus status;

    public Bill(String billId, String rfCode, BigDecimal amount, Date issueDate, Date dueDate, BillStatus status) {
        this.billId = billId;
        this.rfCode = rfCode;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getRfCode() {
        return rfCode;
    }

    public void setRfCode(String rfCode) {
        this.rfCode = rfCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
