package assignments.assignment4.gui;
//Mengimport library library
import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    //Membuat atribut RegisterGUI
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;
    private GridBagConstraints gbc = new GridBagConstraints();

    //Membuat constructor RegisterGUI
    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Mengatur namaLabel
        nameLabel = new JLabel("Masukkan nama Anda");

        //Mengatur nameTextField
        nameTextField = new JTextField();
        nameTextField.setColumns(60);

        //Mengatur phoneLabel
        phoneLabel = new JLabel("Masukkan Nomor handphone Anda");

        //Mengatur phoneTextField
        phoneTextField = new JTextField();
        phoneTextField.setColumns(60);

        //Mengatur passwordLabel
        passwordLabel = new JLabel("Masukkan password Anda:");

        //Mengatur passwordFIeld
        passwordField = new JPasswordField();
        passwordField.setColumns(60);

        //Mengatur registerButton
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());

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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Penempatan nameLabel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);

        //Penempatan nameTextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameTextField, gbc);

        //Penempatan phoneLabel
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(phoneLabel, gbc);

        //Penempatan phoneTextField
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(phoneTextField, gbc);

        //Penempatan passwordLabel
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(passwordLabel, gbc);

        //Penempatan passwordField
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.CENTER;

        //Penempatan registerButton
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(registerButton, gbc);

        //Penempatan backButton
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(backButton, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo("HOME");

        //Mereset semua field dari panel
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        Member registeredMember = null;
        String password = String.valueOf(passwordField.getPassword());

        try {
            //Validasi apabila ada field yang kosong
            if (password.equals("") || nameTextField.getText().equals("") || phoneTextField.getText().equals("")) {
                throw new Exception();
            }
            registeredMember = this.loginManager.register(nameTextField.getText(), phoneTextField.getText(), password);
        }
        //Validasi apabila noHP bukan angka
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Nomor handphone harus berisi angka!","Info",JOptionPane.ERROR_MESSAGE);
            return;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Semua field di atas wajib diisi","Info",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Saat ada ID yang sama dengan user sebelumnya
        if (registeredMember == null){ 
            JOptionPane.showMessageDialog(null, String.format("User dengan nama %s dan nomor hp %s sudah ada!", nameTextField.getText(), phoneTextField.getText()),"Info",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(null, String.format("Berhasil membuat user dengan ID %s!", registeredMember.getId()),"Info",JOptionPane.INFORMATION_MESSAGE);

        MainFrame.getInstance().navigateTo("HOME"); 
        
        //Mereset semua field dari panel
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }
}