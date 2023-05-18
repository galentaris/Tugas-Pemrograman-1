package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;
    private GridBagConstraints gbc = new GridBagConstraints();

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleToLogin());

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleToRegister());
        
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(e -> handleNextDay());

        String date = NotaManager.fmt.format(NotaManager.cal.getTime());
        dateLabel = new JLabel("Hari ini: " + date);
        
        initGUI();
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel,gbc);

        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(toNextDayButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(dateLabel,gbc);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo("REGISTER");
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo("LOGIN");
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        NotaManager.toNextDay();
        String date = NotaManager.fmt.format(NotaManager.cal.getTime());
        dateLabel.setText("Hari ini: " + date);

        JOptionPane.showMessageDialog(null, "Kamu tidur hari ini... zzz...","Info",JOptionPane.INFORMATION_MESSAGE);
    }
}
