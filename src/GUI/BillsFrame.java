package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;
import main.java.com.xrusa.animals.entities.User;

public class BillsFrame extends JFrame {

    private User user;
    private DefaultTableModel tableModel;
    private JTable table;

    public BillsFrame(User user) {
        this.user = user;

        setTitle("e-Banking – Διαχείριση Λογαριασμών (Bills)");
        setSize(700, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
      
        JLabel lblTitle = new JLabel("Ιστορικό Εκδοθέντων Λογαριασμών");
        lblTitle.setBounds(20, 20, 400, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle);

     
        String[] columns = {"Κωδικός RF", "Οφειλέτης", "Ποσό", "Ημερομηνία", "Κατάσταση"};
        tableModel = new DefaultTableModel(columns, 0);

        tableModel.addRow(new Object[]{"RF00123456", "Παπαδόπουλος Γ.", "120.00€", "01/01/2026", "ΠΛΗΡΩΜΕΝΟ"});
        tableModel.addRow(new Object[]{"RF00987654", "Γεωργίου Α.", "45.50€", "03/01/2026", "ΕΚΚΡΕΜΕΙ"});

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 640, 300);
        add(scrollPane);

        
        JButton btnIssue = new JButton("Έκδοση Νέου Bill");
        btnIssue.setBounds(20, 380, 200, 35);
        btnIssue.setBackground(new Color(52, 152, 219)); 
        btnIssue.setForeground(Color.WHITE);
        btnIssue.addActionListener(e -> openIssueDialog());
        add(btnIssue);

        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(510, 380, 150, 35);
        btnBack.addActionListener(e -> dispose());
        add(btnBack);
    }

    
    private void openIssueDialog() {
        JDialog dialog = new JDialog(this, "Έκδοση Λογαριασμού", true);
        dialog.setSize(400, 350);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        JLabel lblName = new JLabel("Ονοματεπώνυμο Οφειλέτη:");
        lblName.setBounds(20, 20, 200, 25);
        dialog.add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(20, 50, 340, 25);
        dialog.add(txtName);

        JLabel lblAmount = new JLabel("Ποσό (€):");
        lblAmount.setBounds(20, 90, 150, 25);
        dialog.add(lblAmount);

        JTextField txtAmount = new JTextField();
        txtAmount.setBounds(20, 120, 150, 25);
        dialog.add(txtAmount);

        JLabel lblDesc = new JLabel("Αιτιολογία / Περιγραφή:");
        lblDesc.setBounds(20, 160, 200, 25);
        dialog.add(lblDesc);

        JTextField txtDesc = new JTextField();
        txtDesc.setBounds(20, 190, 340, 25);
        dialog.add(txtDesc);

        JButton btnSave = new JButton("Δημιουργία & Αποστολή");
        btnSave.setBounds(80, 250, 220, 35);
        btnSave.addActionListener(e -> {
            if (!txtName.getText().isEmpty() && !txtAmount.getText().isEmpty()) {
                
                
                String randomRF = "RF" + (new Random().nextInt(900000) + 100000);
                
                
                tableModel.addRow(new Object[]{
                    randomRF, 
                    txtName.getText(), 
                    txtAmount.getText() + "€", 
                    "04/01/2026", 
                    "ΕΚΚΡΕΜΕΙ"
                });
                
                JOptionPane.showMessageDialog(dialog, "Ο λογαριασμός εκδόθηκε επιτυχώς!\nΚωδικός: " + randomRF);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Συμπληρώστε Ονοματεπώνυμο και Ποσό.");
            }
        });
        dialog.add(btnSave);

        dialog.setVisible(true);
    }
}