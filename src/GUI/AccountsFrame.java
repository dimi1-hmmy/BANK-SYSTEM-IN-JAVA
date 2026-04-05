package GUI;

import main.java.com.xrusa.animals.entities.BankAccount;
import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.factories.BankAccountServiceFactory;
import main.java.com.xrusa.animals.services.BankAccountService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class AccountsFrame extends JFrame {

    private final User user;
    private final BankAccountService bankAccountService;
    private JTable table;
    private DefaultTableModel model;

    public AccountsFrame(User user) {
        this.user = user;
        this.bankAccountService = BankAccountServiceFactory.getBankAccountService();

        setTitle("Προβολή Λογαριασμών");
        setSize(600, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadAccounts();

        setVisible(true);
    }

    private void initUI() {
        JLabel lblUser = new JLabel("Χρήστης: " + user.getUsername() + " | Ρόλος: " + user.getRole());
        lblUser.setBounds(20, 10, 500, 25);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblUser);


        model = new DefaultTableModel(
                new String[]{"IBAN", "Υπόλοιπο (€)", "ID Κατόχου"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only πίνακας
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 540, 280);
        add(scrollPane);

        JButton btnRefresh = new JButton("Ανανέωση");
        btnRefresh.setBounds(20, 350, 130, 30);
        btnRefresh.addActionListener(e -> loadAccounts());
        add(btnRefresh);

        JButton btnClose = new JButton("Κλείσιμο");
        btnClose.setBounds(430, 350, 130, 30);
        btnClose.addActionListener(e -> dispose());
        add(btnClose);
    }

    private void loadAccounts() {
        try {
            model.setRowCount(0);

            List<BankAccount> accounts = bankAccountService.getAllBankAccounts(user.getRole(), user.getUserId());

            if (accounts == null) return;

            for (BankAccount a : accounts) {
                model.addRow(new Object[]{
                        a.getIban(),
                        a.getBalance() + " €",
                        a.getCustomerId()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα φόρτωσης: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
