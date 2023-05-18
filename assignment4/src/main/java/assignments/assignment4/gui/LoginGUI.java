package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;  
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;
    private GridBagConstraints gbc = new GridBagConstraints();


    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        idLabel = new JLabel("Masukkan ID Anda");

        idTextField = new JTextField();
        idTextField.setColumns(60);

        passwordLabel = new JLabel("Masukkan password Anda");
        
        passwordField = new JPasswordField();
        passwordField.setColumns(60);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(idTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo("HOME");
        idTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        SystemCLI systemCLI = loginManager.getSystem(idTextField.getText());
        if (systemCLI == null){     //Saat user input ID yang tidak ada dalam memberList
            JOptionPane.showMessageDialog(null, "ID atau password invalid.", "Info", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            passwordField.setText("");
            return;
        }
        if (MainFrame.getInstance().login(idTextField.getText(), String.valueOf(passwordField.getPassword()))) {
            if (systemCLI.getClass().getSimpleName().equals("MemberSystem")) {
                MainFrame.getInstance().navigateTo("MEMBER");        
            }
            else if (systemCLI.getClass().getSimpleName().equals("EmployeeSystem")) {
                MainFrame.getInstance().navigateTo("EMPLOYEE"); 
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "ID atau password invalid.", "Info", JOptionPane.ERROR_MESSAGE);
        }
        idTextField.setText("");
        passwordField.setText("");
    }
}
