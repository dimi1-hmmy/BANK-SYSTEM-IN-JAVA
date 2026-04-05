package GUI;

import main.java.com.xrusa.animals.entities.Transaction;
import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.factories.TransactionServiceFactory;
import main.java.com.xrusa.animals.services.TransactionService;
import main.java.com.xrusa.animals.enums.TransactionType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TransactionsFrame extends JFrame {

    private User user;
    private TransactionService transactionService;
    private boolean isHistoryMode; //s

    private JTable table;
    private DefaultTableModel model;

    public TransactionsFrame(User user,boolean isHistoryMode) {
        this.user = user;
        this.transactionService = TransactionServiceFactory.getTransactionService();
        this.isHistoryMode = isHistoryMode;


        setTitle(isHistoryMode ? "Ιστορικό Κινήσεων" : "Συναλλαγές");
        setSize(700, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        initUI();
        loadTransactions();
        setVisible(true);
    }

    private void initUI() {

        JLabel lblUser = new JLabel(
                "Χρήστης: " + user.getUsername() + " | Ρόλος: " + user.getRole());
        lblUser.setBounds(20, 10, 400, 25);
        add(lblUser);

        model = new DefaultTableModel(
                new String[]{"ID", "Ποσό", "Ημερομηνία", "Περιγραφή"}, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 640, 250);
        add(scrollPane);

        JButton btnRefresh = new JButton("Ανανέωση");
        btnRefresh.setBounds(20, 320, 120, 30);
        btnRefresh.addActionListener(e -> loadTransactions());
        add(btnRefresh);


        JButton btnNewTransaction = new JButton("Νέα Συναλλαγή");
        btnNewTransaction.setBounds(180, 320, 150, 30);
        btnNewTransaction.setVisible(!isHistoryMode);
        btnNewTransaction.addActionListener(e -> showCreateTransactionDialog());

        add(btnNewTransaction);
    }



    private void loadTransactions() {
        try {
            model.setRowCount(0);

            List<Transaction> transactions =
                    transactionService.getAllTransactions(
                            user.getRole(),
                            user.getUserId()
                    );

            for (Transaction t : transactions) {
                model.addRow(new Object[]{
                        t.getTransactionId(),
                        t.getAmount()+ " €",
                        t.getTimestamp(),
                        t.getDescription()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Σφάλμα φόρτωσης συναλλαγών\n" + ex.getMessage(),
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showCreateTransactionDialog() {

        JTextField amountField = new JTextField();
        JTextField ibanFromField = new JTextField();
        JTextField ibanToField = new JTextField();
        JTextField descField = new JTextField();

        Object[] message = {
                "Ποσό:", amountField,
                "IBAN Αποστολέα (δικό σου):", ibanFromField,
                "IBAN Παραλήπτη:", ibanToField,
                "Περιγραφή:", descField
        };

        int option = JOptionPane.showConfirmDialog(
                this, message, "Εκτέλεση Συναλλαγής", JOptionPane.OK_CANCEL_OPTION);

        if (option != JOptionPane.OK_OPTION) return;

        try {
            String ibanFrom = ibanFromField.getText().trim();
            String ibanTo = ibanToField.getText().trim();
            String desc = descField.getText().trim();

            if (ibanFrom.isEmpty() || ibanTo.isEmpty()) {
                showError("Συμπλήρωσε IBAN Αποστολέα και IBAN Παραλήπτη.");
                return;
            }

            BigDecimal amount = new BigDecimal(amountField.getText().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Το ποσό πρέπει να είναι > 0.");
                return;
            }

            Transaction newTx = new Transaction(
                    UUID.randomUUID().toString().substring(0, 8),
                    TransactionType.TRANSFER,
                    amount,
                    new Date(),
                    desc,
                    null,
                    ibanFrom,
                    ibanTo
            );

            transactionService.createTransaction(newTx);

            JOptionPane.showMessageDialog(this, "Η συναλλαγή ολοκληρώθηκε επιτυχώς!");
            loadTransactions();

        } catch (NumberFormatException ex) {
            showError("Βάλε σωστό αριθμό στο ποσό (π.χ. 40 ή 40.50).");
        } catch (Exception ex) {
            showError("Αποτυχία συναλλαγής: " + ex.getMessage());
        }
    }


    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Σφάλμα", JOptionPane.ERROR_MESSAGE);
    }
}



