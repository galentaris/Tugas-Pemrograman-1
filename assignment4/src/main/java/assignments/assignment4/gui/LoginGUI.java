package assignments.assignment4.gui;
//Mengimport library-library
import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    //Mendefinisikan atribut-atribut LoginGUI
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

    //Membuat constructor LoginGUI
    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Mengatur idLabel
        idLabel = new JLabel("Masukkan ID Anda");

        //Mengatur textField
        idTextField = new JTextField();
        idTextField.setColumns(60);

        //Mengatur passwordLabel
        passwordLabel = new JLabel("Masukkan password Anda");
        
        //Mengatur password Field
        passwordField = new JPasswordField();
        passwordField.setColumns(60);

        //Mengatur loginButton
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        //Mengatur backButton
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
        
        //Penempatan idLabel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(idLabel, gbc);

        //Penempatan idTextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(idTextField, gbc);

        //Penempatan passwordLabel
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        //Penempatan passwordField
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.CENTER;

        //Penempatan loginButton
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(loginButton, gbc);

        //Penempatan backButton
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

        //Mereset semua field dari panel
        idTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        SystemCLI systemCLI = loginManager.getSystem(idTextField.getText());

        //Saat user input ID yang tidak ada dalam memberList
        if (systemCLI == null){ 
            JOptionPane.showMessageDialog(null, "ID atau password invalid.", "Info", JOptionPane.ERROR_MESSAGE);

            //Mereset semua field
            idTextField.setText("");
            passwordField.setText("");
            return;
        }

        //Saat user berhasil login
        if (MainFrame.getInstance().login(idTextField.getText(), String.valueOf(passwordField.getPassword()))) {
            if (systemCLI.getClass().getSimpleName().equals("MemberSystem")) { //Saat user tersebut merupakan customer
                MainFrame.getInstance().navigateTo("MEMBER");        
            }
            else if (systemCLI.getClass().getSimpleName().equals("EmployeeSystem")) { //Saat user tersebut merupakan Employee
                MainFrame.getInstance().navigateTo("EMPLOYEE"); 
            }
        }

        //Saat user gagal login karena password dan id tidak sesuai
        else {
            JOptionPane.showMessageDialog(null, "ID atau password invalid.", "Info", JOptionPane.ERROR_MESSAGE);
        }

        //Mereset semua field dari panel
        idTextField.setText("");
        passwordField.setText("");
    }
}