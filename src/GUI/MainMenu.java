package GUI;

import javax.swing.*;
import java.awt.*;

import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.factories.BankAccountServiceFactory;
import main.java.com.xrusa.animals.factories.CustomerServiceFactory;
import main.java.com.xrusa.animals.factories.ServiceCreator;
import main.java.com.xrusa.animals.factories.StandingOrderServiceFactory;
import main.java.com.xrusa.animals.services.BankAccountService;

public class MainMenu extends JFrame {

    private User user;
    private int currentY = 70;

    public MainMenu(User user) {
        this.user = user;

        setTitle("e-Banking – Κεντρικό Μενού");
        setSize(520, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {

        JLabel welcome = new JLabel(
                "Χρήστης: " + user.getUsername() + " | Ρόλος: " + user.getRole());
        welcome.setBounds(20, 20, 480, 25);
        welcome.setFont(new Font("Arial", Font.BOLD, 14));
        add(welcome);

        switch (user.getRole()) {

            case INDIVIDUAL_CUSTOMER:
                accountsSection(true);
                transactionsIndividual();
                standingOrdersSection();
                break;

            case COMPANY_CUSTOMER:
                accountsSection(true);
                transactionsCompany();
                standingOrdersSection();
                billsSection();
                break;

            case ADMIN:
                userManagementSection();
                accountsSection(false);
                adminTransactions();
                auditSection();
                break;
        }
        
        addMenuButton("Συναλλαγές", () -> {
            new TransactionsFrame(user,true);
        });

        addLogoutButton();
    }

    private void addMenuButton(String title, Runnable action) {
        JButton btn = new JButton(title);
        btn.setBounds(100, currentY, 300, 30);
        btn.addActionListener(e -> action.run());
        add(btn);
        currentY += 40;
    }



    private void accountsSection(boolean allowSimpleOps) {
        addSection("Διαχείριση Λογαριασμών");
        
        addButton("Προβολή Λογαριασμών", () -> {
            new AccountsFrame(user);
        });
        
        addButton("Προβολή Κινήσεων", () -> {
            new AccountsFrame(user);
        });

        if (!allowSimpleOps) {
            addButton("Ενεργοποίηση / Απενεργοποίηση", () -> {
                 JOptionPane.showMessageDialog(this, "Admin λειτουργία: Διαχείριση status λογαριασμού.");
            });
        }
    }

    private void transactionsIndividual() {
        addSection("Συναλλαγές");
        
        // Καλεί το TransactionsFrame
        addButton("Μεταφορά Χρημάτων", () -> {
             new TransactionsFrame(user,false);
        });
        
        addButton("Πληρωμή RF", () -> {
            new BillsFrame(user);
        });
        
        addButton("Πληρωμή Bill", () -> {
             new BillsFrame(user);
        });
    }

    private void transactionsCompany() {
        addSection("Συναλλαγές");
        

        addButton("Μεταφορά Χρημάτων", () -> {
            new TransactionsFrame(user, false);
        });
    }

    private void adminTransactions() {
        addSection("Συναλλαγές");
        addButton("Χειροκίνητη Πληρωμή", () -> {
             new TransactionsFrame(user,true);
        });
    }

    private void standingOrdersSection() {
        addSection("Πάγιες Εντολές");
        
        // Καλεί το StandingOrdersFrame
        addButton("Δημιουργία Πάγιας", () -> {
            new StandingOrdersFrame(user, StandingOrderServiceFactory.getStandingOrderService(), CustomerServiceFactory.getCustomerService(), BankAccountServiceFactory.getBankAccountService());
        });
        
        addButton("Ακύρωση Πάγιας", () -> {
             new StandingOrdersFrame(user, StandingOrderServiceFactory.getStandingOrderService(), CustomerServiceFactory.getCustomerService(), BankAccountServiceFactory.getBankAccountService());
        });
    }

    private void billsSection() {
        addSection("Bills");
        addButton("Έκδοση Bill", () -> {
             new BillsFrame(user);
        });
    }

    private void userManagementSection() {
        addSection("Διαχείριση Χρηστών");
        addButton("Προβολή / Διαχείριση Πελατών", () -> {
             new AccountsFrame(user);
        });
    }

    private void auditSection() {
        addSection("Audit & Simulation");
        addButton("Time Simulation", () -> {
             new AdminToolsFrame(user, 1); 
        });
        

        addButton("Audit Logs", () -> {
             new AdminToolsFrame(user, 0);
        });
        
      
        addButton("Εξαγωγή Αναφορών", () -> {
             new AdminToolsFrame(user, 2);
        });
    }
    



    private void addSection(String title) {
        JLabel lbl = new JLabel(title);
        lbl.setBounds(20, currentY, 480, 25);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        currentY += 30;
        add(lbl);
    }

    private void addButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setBounds(40, currentY, 420, 30);
        btn.setFocusable(false);
        
        if (action != null) {
            btn.addActionListener(e -> action.run());
        }
        
        currentY += 35;
        add(btn);
    }

    private void addLogoutButton() {
        JButton logout = new JButton("Αποσύνδεση");
        logout.setBounds(180, 520, 150, 30);
        logout.addActionListener(e -> {
            dispose();
            new MyFrame(); 
        });
        add(logout);
    }

   
}
