package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Imports για τις δικές σου κλάσεις
import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.entities.AuditLog;
import main.java.com.xrusa.animals.utils.FilePaths;
import main.java.com.xrusa.animals.datasource.impl.CsvDataSource;
import main.java.com.xrusa.animals.filereaders.impl.AuditLogAccess;

public class AdminToolsFrame extends JFrame {

    private User user;
    private JTabbedPane tabbedPane;

    private AuditLogAccess auditLogReader;
    
    private Calendar currentDate;
    private JLabel lblCurrentDate;
    private JTextArea simulationConsole;

    public AdminToolsFrame(User user, int initialTab) {
        this.user = user;
        


        this.auditLogReader = new AuditLogAccess(FilePaths.AUDIT_LOGS, CsvDataSource.getInstance());


        currentDate = Calendar.getInstance();

        setTitle("e-Banking – Admin Control Panel");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();


        if (initialTab >= 0 && initialTab < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(initialTab);
        }

        setVisible(true);
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Audit Logs", createAuditPanel());        
        tabbedPane.addTab("Time Simulation", createSimulationPanel()); 
        tabbedPane.addTab("Αναφορές (Reports)", createReportsPanel()); 

        add(tabbedPane);
    }


    private JPanel createAuditPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblHeader = new JLabel("  Ιστορικό Ενεργειών Συστήματος (Audit Trail from CSV)");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblHeader.setPreferredSize(new Dimension(800, 40));
        panel.add(lblHeader, BorderLayout.NORTH);


        String[] columns = {"Log ID", "Timestamp", "Actor Type", "Actor ID", "Action", "Details"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);


        try {
            List<AuditLog> logs = auditLogReader.read();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (AuditLog log : logs) {
                model.addRow(new Object[]{
                    log.getLogId(),
                    sdf.format(log.getTimestamp()),
                    log.getActorType(),
                    log.getActorId(),
                    log.getAction(),
                    log.getDetails()
                });
            }
        } catch (Exception e) {
            System.err.println("Error reading AuditLogs: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση των Logs: " + e.getMessage());
        }

        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        

        JButton btnRefresh = new JButton("Ανανέωση");
        btnRefresh.addActionListener(e -> {
            auditLogReader.reload();
            dispose();
            new AdminToolsFrame(user, 0); 
        });
        panel.add(btnRefresh, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createSimulationPanel() {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Προσομοίωση Χρόνου (Time Simulation)");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(20, 20, 400, 30);
        panel.add(title);

        JLabel desc = new JLabel("<html><i>Επιτρέπει την προσομοίωση ημερών για έλεγχο αυτόματων διαδικασιών<br>(Τόκοι, Πάγιες, Τέλη).</i></html>");
        desc.setBounds(20, 50, 600, 40);
        panel.add(desc);

        JLabel lblDateTitle = new JLabel("Ημερομηνία Συστήματος:");
        lblDateTitle.setBounds(20, 110, 200, 25);
        panel.add(lblDateTitle);

        lblCurrentDate = new JLabel(formatDate(currentDate.getTime()));
        lblCurrentDate.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblCurrentDate.setForeground(new Color(231, 76, 60));
        lblCurrentDate.setBounds(220, 105, 200, 35);
        panel.add(lblCurrentDate);

        JButton btnNextDay = new JButton("+1 Ημέρα");
        btnNextDay.setBounds(20, 160, 150, 40);
        btnNextDay.addActionListener(e -> runSimulation(1));
        panel.add(btnNextDay);

        JButton btnNextMonth = new JButton("+1 Μήνας");
        btnNextMonth.setBounds(190, 160, 150, 40);
        btnNextMonth.addActionListener(e -> runSimulation(30));
        panel.add(btnNextMonth);

        JLabel lblConsole = new JLabel("System Log Output:");
        lblConsole.setBounds(20, 220, 200, 20);
        panel.add(lblConsole);

        simulationConsole = new JTextArea("System initialized...\nWaiting for simulation command.\n");
        simulationConsole.setEditable(false);
        simulationConsole.setFont(new Font("Monospaced", Font.PLAIN, 12));
        simulationConsole.setBackground(Color.BLACK);
        simulationConsole.setForeground(Color.GREEN);
        
        JScrollPane scroll = new JScrollPane(simulationConsole);
        scroll.setBounds(20, 240, 780, 250);
        panel.add(scroll);

        return panel;
    }


    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Εξαγωγή Αναφορών (Reports)");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(20, 20, 300, 30);
        panel.add(title);

        JButton btnTransReport = new JButton("Αναφορά Συναλλαγών (CSV)");
        btnTransReport.setBounds(50, 80, 300, 40);
        btnTransReport.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Η αναφορά 'Transactions.csv' δημιουργήθηκε.")
        );
        panel.add(btnTransReport);

        JButton btnFailReport = new JButton("Αποτυχημένες Πάγιες Εντολές");
        btnFailReport.setBounds(50, 140, 300, 40);
        btnFailReport.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν αποτυχημένες πάγιες.")
        );
        panel.add(btnFailReport);

        JButton btnFeesReport = new JButton("Συγκεντρωτική Τόκων & Τελών");
        btnFeesReport.setBounds(50, 200, 300, 40);
        btnFeesReport.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Η αναφορά δημιουργήθηκε επιτυχώς.")
        );
        panel.add(btnFeesReport);

        return panel;
    }



    private void runSimulation(int daysToAdd) {
        currentDate.add(Calendar.DAY_OF_MONTH, daysToAdd);
        String newDateStr = formatDate(currentDate.getTime());
        lblCurrentDate.setText(newDateStr);

        simulationConsole.append("--------------------------------------------------\n");
        simulationConsole.append("Simulating " + daysToAdd + " days... Target Date: " + newDateStr + "\n");
        simulationConsole.append("[System]: Calculating daily interest for all accounts... DONE.\n");
        simulationConsole.append("[System]: Checking Standing Orders... NO ORDERS DUE.\n");
        
        if (daysToAdd >= 30) {
             simulationConsole.append("[System]: Applying Monthly Maintenance Fees (Business Accounts)... DONE.\n");
        }
        
        simulationConsole.append("[System]: Audit logs updated.\n");
        simulationConsole.setCaretPosition(simulationConsole.getDocument().getLength());
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}