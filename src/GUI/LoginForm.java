package GUI;

import javax.swing.*;

import main.java.com.xrusa.animals.entities.User;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.services.UserService;
import main.java.com.xrusa.animals.utils.FilePaths;

import java.awt.*;

public class LoginForm extends JFrame {

    private String userTypeStr;
    private final UserService userService;

    // constructor must know the userType
    LoginForm(String userType, UserService userService) {
      this.userService = userService;
        this.userTypeStr = userType;
        
        this.setTitle("Login - " + userType);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(null);
        this.setLocationRelativeTo(null); // center it
        this.setResizable(false);
        
        // Title depending on user's choice INDIVIDUAL_CUSTOMER COMPANY_CUSTOMER
        JLabel titleLabel = new JLabel("Είσοδος " + userType);
        titleLabel.setBounds(50, 20, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 38, 84));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        // Username Label & Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 25);
        this.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(150, 70, 150, 25);
        this.add(userText);

        // Password Label & Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 110, 100, 25);
        this.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(150, 110, 150, 25);
        this.add(passText);

        // entry button
        JButton btnLogin = new JButton("Είσοδος");
        btnLogin.setBounds(120, 160, 150, 30);
        btnLogin.setFocusable(false);
        btnLogin.setBackground(new Color(0, 38, 84));
        btnLogin.setForeground(Color.WHITE);
        this.add(btnLogin);

        btnLogin.addActionListener(e -> {

            String usernameInput = userText.getText().trim();
            String passwordInput = String.valueOf(passText.getPassword()).trim();


            String type = this.userTypeStr == null ? "" : this.userTypeStr.trim();

            Role role;
            if (type.equals("Ιδιώτης")) {
                role = Role.INDIVIDUAL_CUSTOMER;
            } else if (type.equals("Εταιρεία")) {
                role = Role.COMPANY_CUSTOMER;
            } else {
                role = Role.ADMIN;
            }


            User foundUser = findUserFromCsv(usernameInput, passwordInput, role);

            if (foundUser != null) {
                new MainMenu(foundUser);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Λάθος στοιχεία ή λάθος κατηγορία χρήστη.",
                        "Σφάλμα Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back Button
        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(120, 200, 150, 30);
        btnBack.addActionListener(e -> {
            this.dispose();
            new MyFrame();
        });
        this.add(btnBack);

        this.setVisible(true);
    }

    private User findUserFromCsv(String username, String password, Role roleIgnoredForNow) {
      try {
        return userService.loginAndGetUser(username, password, roleIgnoredForNow);
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Σφάλμα ανάγνωσης users.csv: " + ex.getMessage());
      }
      return null;
    }


}