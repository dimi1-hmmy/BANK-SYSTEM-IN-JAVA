package main.java.com.xrusa.animals.entities;

import java.util.List;
import main.java.com.xrusa.animals.enums.Role;

public class CompanyCustomer extends Customer {

    private String companyName;
    private String vatNumber;
    
    public String getCompanyName() {
        return companyName;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CompanyCustomer(String customerId, String userId, String username, String password, Role role, String name,
            String email, String phone, List<StandingOrder> standingOrders, String companyName, String vatNumber) {
        super(userId, username, password, role, customerId, name, email, phone, standingOrders);
        this.companyName = companyName;
        this.vatNumber = vatNumber;
    }
    
}
