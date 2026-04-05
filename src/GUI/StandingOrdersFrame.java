package GUI;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.com.xrusa.animals.entities.StandingOrder;
import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.enums.StandingOrderStatus;
import main.java.com.xrusa.animals.enums.StandingOrderType;
import main.java.com.xrusa.animals.services.BankAccountService;
import main.java.com.xrusa.animals.services.CustomerService;
import main.java.com.xrusa.animals.services.StandingOrderService;

public class StandingOrdersFrame extends JFrame {

  private User user;
  private DefaultTableModel tableModel;
  private JTable table;
  private final StandingOrderService standingOrderService;
  private final CustomerService customerService;
  private final BankAccountService bankAccountService;

  public StandingOrdersFrame(User user, StandingOrderService standingOrderService, CustomerService customerService, BankAccountService bankAccountService) {
      this.user = user;
      this.standingOrderService = standingOrderService;
      this.customerService = customerService;
      this.bankAccountService = bankAccountService;
      setTitle("e-Banking – Πάγιες Εντολές");
      setSize(600, 500);
      setLayout(null);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      initUI();
      setVisible(true);
    }

    private void initUI() {

        JLabel lblTitle = new JLabel("Λίστα Πάγιων Εντολών");
        lblTitle.setBounds(20, 20, 300, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle);

        //
        String[] columns = {"IBAN Παραλήπτη", "Ποσό", "Συχνότητα", "Ημ/νία Επόμενης"};
        tableModel = new DefaultTableModel(columns, 0);
        List<StandingOrder> standingOrderList = standingOrderService.getStandingOrdersByCustomerId(user.getUserId());

        for (StandingOrder standingOrder : standingOrderList) {
          tableModel.addRow(new Object[]{standingOrder.getIbanFrom(), standingOrder.getAmount(), standingOrder.getFrequency(), standingOrder.getStartDate()});

        }
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 540, 300);
        add(scrollPane);


        

        JButton btnAdd = new JButton("Νέα Πάγια");
        btnAdd.setBounds(20, 380, 150, 35);
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> openAddDialog());
        add(btnAdd);


        JButton btnDelete = new JButton("Διαγραφή Επιλεγμένης");
        btnDelete.setBounds(190, 380, 180, 35);
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(e -> deleteSelectedOrder());
        add(btnDelete);


        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(410, 380, 150, 35);
        btnBack.addActionListener(e -> dispose());
        add(btnBack);
    }


    private void deleteSelectedOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Είστε σίγουροι για τη διαγραφή;", "Επιβεβαίωση", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Η πάγια εντολή διαγράφηκε.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε μια εντολή από τη λίστα.");
        }
    }


    private void openAddDialog() {
        JDialog dialog = new JDialog(this, "Δημιουργία Πάγιας", true);
        dialog.setSize(400, 350);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        JLabel lblIbanTo = new JLabel("IBAN Παραλήπτη:");
        lblIbanTo.setBounds(20, 20, 150, 25);
        dialog.add(lblIbanTo);

        JTextField txtIbanTo = new JTextField();
      txtIbanTo.setBounds(20, 45, 340, 25);
        dialog.add(txtIbanTo);

      JLabel lblIbanFrom = new JLabel("IBAN Αποστολέα:");
      lblIbanFrom.setBounds(20, 140, 150, 25);
      dialog.add(lblIbanFrom);

      JTextField txtIbanFrom = new JTextField();
      txtIbanFrom.setBounds(20, 165, 340, 25);
      dialog.add(txtIbanFrom);

        JLabel lblAmount = new JLabel("Ποσό (€):");
        lblAmount.setBounds(20, 200, 150, 25);
        dialog.add(lblAmount);

        JTextField txtAmount = new JTextField();
        txtAmount.setBounds(20, 225, 150, 25);
        dialog.add(txtAmount);

        JLabel lblFreq = new JLabel("Συχνότητα:");
        lblFreq.setBounds(200, 200, 150, 25);
        dialog.add(lblFreq);

        String[] frequencies = {"Μηνιαία", "Τριμηνιαία", "Ετήσια"};
        JComboBox<String> cmbFreq = new JComboBox<>(frequencies);
        cmbFreq.setBounds(200, 225, 160, 25);
        dialog.add(cmbFreq);

        JLabel lblTitle = new JLabel("Τίτλος Πάγιας Εντολής:");
        lblTitle.setBounds(20, 80, 200, 25);
        dialog.add(lblTitle);

        JTextField txtTitle = new JTextField();
        txtTitle.setBounds(20, 105, 340, 25);
        dialog.add(txtTitle);

        JLabel lblType = new JLabel("Τύπος:");
        lblType.setBounds(20, 260, 150, 25);
        dialog.add(lblType);

        String[] type = {StandingOrderType.PAYMENT_STANDING_ORDER.name(), StandingOrderType.TRANSFER_STANDING_ORDER.name()};
        JComboBox<String> cmbType = new JComboBox<>(type);
        cmbType.setBounds(200, 285, 160, 25);
        dialog.add(cmbType);
        
        JButton btnSave = new JButton("Αποθήκευση");
        btnSave.setBounds(120, 320, 160, 35);
        btnSave.addActionListener(e -> {


            if(!txtIbanFrom.getText().isEmpty() && !txtAmount.getText().isEmpty() && cmbFreq.getSelectedIndex() != -1 && !txtTitle.getText().isEmpty()) {
              standingOrderService.createStandingOrder(new StandingOrder(UUID.randomUUID().toString(),
                                                                         "ΠΑΓΙΑ ΕΝΤΟΛΗ",
                                                                         new Date(),
                                                                         cmbFreq.getSelectedItem().toString(),
                                                                         new BigDecimal(txtAmount.getText()),
                                                                         StandingOrderStatus.PENDING,
                                                                         cmbType.getSelectedItem() == StandingOrderType.PAYMENT_STANDING_ORDER ? StandingOrderType.PAYMENT_STANDING_ORDER : StandingOrderType.TRANSFER_STANDING_ORDER,
                                                                         customerService.findCustomerByUserId(user.getUserId()).getCustomerId(),
                                                                         txtIbanFrom.getText(),
                                                                         txtIbanTo.getText()), user.getRole());

                dialog.dispose();
                initUI();
            } else {
                JOptionPane.showMessageDialog(dialog, "Συμπληρώστε όλα τα πεδία!");
            }
        });
        dialog.add(btnSave);

        dialog.setVisible(true);
    }
}
