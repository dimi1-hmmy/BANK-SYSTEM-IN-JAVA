package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.java.com.xrusa.animals.factories.UserServiceFactory;
import main.java.com.xrusa.animals.utils.FilePaths;

public class MyFrame extends JFrame {

    // Δηλώνουμε το termsPanel εδώ για να το βλέπουν όλα τα methods
    JPanel termsPanel;

    public MyFrame() {
        // images
        ImageIcon imageBg = new ImageIcon(FilePaths.MAIN_MENU_IMAGE);
        ImageIcon imageLogo = new ImageIcon(FilePaths.LOGO_IMAGE);

        /* --- Background Panel με GridBagLayout για ΚΕΝΤΡΑΡΙΣΜΑ --- */
        BackgroundPanel bgPanel = new BackgroundPanel(imageBg.getImage());
        // Το GridBagLayout είναι ιδανικό για να κεντράρει ένα αντικείμενο στη μέση
        bgPanel.setLayout(new GridBagLayout());
        bgPanel.setBounds(0, 0, 1920, 1080); // Γεμίζει όλο το frame

        /*
         * --- Main Container (Το κουτί που θα έχει μέσα τα Labels και τα Buttons) ---
         */
        JPanel mainContainer = new JPanel();
        // BoxLayout Y_AXIS: Βάζει τα στοιχεία το ένα κάτω από το άλλο
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setOpaque(false); // Διαφανές για να φαίνεται η εικόνα από πίσω

        /* --- Στοιχεία (Labels) --- */
        JLabel labelWelcome = new JLabel("Καλώς Ήρθατε στην Bank of Tuc");
        labelWelcome.setAlignmentX(Component.CENTER_ALIGNMENT); // Κεντράρισμα στον άξονα Χ
        labelWelcome.setForeground(new Color(255, 0, 100));
        labelWelcome.setFont(new Font("Arial", Font.BOLD, 24));

        // Κενό ανάμεσα (strut)
        mainContainer.add(labelWelcome);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel labelChoice = new JLabel("Επιλέξτε τύπο λογαριασμού:");
        labelChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelChoice.setFont(new Font("Arial", Font.ITALIC, 20));
        labelChoice.setForeground(Color.BLACK); // Μαύρο για να διαβάζεται

        mainContainer.add(labelChoice);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 30)));

        /* --- Panel για τα Κουμπιά (για να μπουν δίπλα-δίπλα) --- */
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // FlowLayout κεντραρισμένο με κενό 20px
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // INDIVIDUAL_CUSTOMER button
        JButton btnPrivate = createStyledButton("Ιδιώτης");
        btnPrivate.addActionListener(e -> {
            dispose();
            new LoginForm("Ιδιώτη", UserServiceFactory.getUserService());
        });

        // COMPANY_CUSTOMER button
        JButton btnCompany = createStyledButton("Εταιρεία");
        btnCompany.addActionListener(e -> {
            dispose();
            new LoginForm("Εταιρεία", UserServiceFactory.getUserService());
        });

        JButton btnAdmin = createStyledButton("Διαχειριστής"); 
        btnAdmin.addActionListener(e -> {
            dispose();
            new LoginForm("Διαχειριστή", UserServiceFactory.getUserService());
        });

        buttonsPanel.add(btnPrivate);
        buttonsPanel.add(btnCompany);
        buttonsPanel.add(btnAdmin);

        mainContainer.add(buttonsPanel);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 40)));

        /* --- Όροι Χρήσης Link --- */
        JLabel termsLabel = new JLabel("<html><a href=''>Όροι Χρήσης και Προϋποθέσεις</a></html>");
        termsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        termsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        termsLabel.setForeground(Color.BLUE);
        termsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        mainContainer.add(termsLabel);

        // Προσθέτουμε το mainContainer στο bgPanel.
        // Λόγω του GridBagLayout, θα μπει αυτόματα στο κέντρο.
        bgPanel.add(mainContainer);

        /* --- FRAME --- */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bank of Tuc - Welcome");
        this.setSize(800, 600);
        if (imageLogo.getImage() != null)
            this.setIconImage(imageLogo.getImage());

        // Χρησιμοποιούμε BorderLayout στο Frame για να πιάνει το bgPanel όλο τον χώρο
        this.setLayout(new BorderLayout());
        this.add(bgPanel, BorderLayout.CENTER);

        /* --- POPUP TERMS PANEL (Μέσω LayeredPane) --- */
        // We create the terms but keep it hidden .
        createTermsPanel();

        // Listener (Override so we can show the TermsPanel)
        termsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showTermsPanel();
            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // method that produces the same style buttons for the aesthetics
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(130, 50));
        btn.setFocusable(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(Color.lightGray);
        btn.setBorder(BorderFactory.createEtchedBorder());
        return btn;
    }

    // Method that creates the TermsPanel
    private void createTermsPanel() {
        termsPanel = new JPanel();
        termsPanel.setSize(500, 300);
        termsPanel.setBackground(Color.WHITE);
        termsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        termsPanel.setLayout(null);
        termsPanel.setVisible(false); // hidden

        JTextArea termsText = new JTextArea(
                "ΟΡΟΙ ΧΡΗΣΗΣ BANK OF TUC:\n\n"
                        + "1. Ο λογαριασμός είναι δικός σου.\n Μην δώσεις τα στοιχεία σου ούτε στον κολλητό σου ούτε στην μάνα σου.\n Αν κάποιος μπει στον λογαριασμό σου και χάσεις λεφτά, εμείς δεν φταίμε(ούτε εμείς έχουμε\n λεφτά εκεί μέσα).\n"
                        + "2. Ασφάλεια συναλλαγών δεν υπάρχει, η τράπεζα είναι εδώ για τα funsies...\n"
                        + "3. Προστασία δεδομένων όχι γιατί μας έχει εξαγοράσει η ελληνική κυβέρνηση...\n"
                        + "4. Το σάιτ φτιάχτηκε από ερασιτέχνες και συνεπώς αν κάτι μπαγκάρει δείξτε \nκατανόηση το IT department μας έχει διαφορά ώρας και πληρώνουμε τους συναδέλφους\n στην Ινδία 0,01ευρώ/ώρα");
        termsText.setEditable(false);
        termsText.setBounds(10, 10, 480, 240);
        termsPanel.add(termsText);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(200, 260, 100, 30);
        closeButton.addActionListener(e -> termsPanel.setVisible(false));
        termsPanel.add(closeButton);

        // adding it to LayeredPane (POPUP_LAYER) so it appears on top
        this.getLayeredPane().add(termsPanel, JLayeredPane.POPUP_LAYER);
    }

    // method that shows the termsPanel and centers it
    private void showTermsPanel() {
        // Calculate the frame's width and height so the panel is centered
        int x = (this.getWidth() - termsPanel.getWidth()) / 2;
        int y = (this.getHeight() - termsPanel.getHeight()) / 2;

        termsPanel.setLocation(x, y);
        termsPanel.setVisible(true); // visible!!
    }
}