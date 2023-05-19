package assignments.assignment4;
//Mengimport library-library yang dibutuhkan
import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.gui.HomeGUI;
import assignments.assignment4.gui.LoginGUI;
import assignments.assignment4.gui.RegisterGUI;
import assignments.assignment4.gui.member.Loginable;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.CreateNotaGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    //Membuat atribut-atribut MainFrame
    private static MainFrame instance;
    private final Loginable[] loginablePanel;
    private final MemberSystem memberSystem = new MemberSystem();
    private final EmployeeSystem employeeSystem = new EmployeeSystem();
    private final CardLayout cards = new CardLayout();
    private final JPanel mainPanel = new JPanel(cards);
    private final LoginManager loginManager = new LoginManager(employeeSystem, memberSystem);
    private final HomeGUI homeGUI = new HomeGUI();
    private final RegisterGUI registerGUI = new RegisterGUI(loginManager);
    private final LoginGUI loginGUI = new LoginGUI(loginManager);
    private final EmployeeSystemGUI employeeSystemGUI = new EmployeeSystemGUI(employeeSystem);
    private final MemberSystemGUI memberSystemGUI = new MemberSystemGUI(memberSystem);
    private final CreateNotaGUI createNotaGUI = new CreateNotaGUI(memberSystemGUI);

    //Membuat constructor untuk MainFrame
    private MainFrame(){
        super("CuciCuciSystem");

        //Menambahkan employee kedalam memberList dalam employee
        employeeSystem.addEmployee(new Employee("delta Epsilon Huha Huha", "ImplicitDiff"));
        employeeSystem.addEmployee(new Employee("Regret", "FansBeratKanaArima"));
        
        //Mengatur Frame utama
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 432);
        setVisible(true);
        setLocationRelativeTo(null);
        loginablePanel = new Loginable[]{
                employeeSystemGUI,
                memberSystemGUI,
        };

        initGUI();

        cards.show(mainPanel, HomeGUI.KEY);
        add(mainPanel);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        mainPanel.add(homeGUI, HomeGUI.KEY);
        mainPanel.add(registerGUI, RegisterGUI.KEY);
        mainPanel.add(loginGUI, LoginGUI.KEY);
        mainPanel.add(employeeSystemGUI, EmployeeSystemGUI.KEY);
        mainPanel.add(memberSystemGUI, MemberSystemGUI.KEY);
        mainPanel.add(createNotaGUI, CreateNotaGUI.KEY);
    }

    /**
     * Method untuk mendapatkan instance MainFrame.
     * Instance Class MainFrame harus diambil melalui method ini agar menjamin hanya terdapat satu Frame pada program.
     *
     * @return instance dari class MainFrame
     * */
    public static MainFrame getInstance(){
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    /**
     * Method untuk pergi ke panel sesuai dengan yang diberikan pada param.
     *
     * @param page -> key dari halaman yang diinginkan.
     * */
    public void navigateTo(String page){
        if (page.equals("REGISTER")) {
            cards.show(mainPanel, RegisterGUI.KEY);
        }
        else if (page.equals("HOME")) {
            cards.show(mainPanel, HomeGUI.KEY);
        }
        else if (page.equals("LOGIN")) {
            cards.show(mainPanel, LoginGUI.KEY);
        }
        else if (page.equals("MEMBER")) {
            cards.show(mainPanel, MemberSystemGUI.KEY);
        }
        else if (page.equals("EMPLOYEE")) {
            cards.show(mainPanel, EmployeeSystemGUI.KEY); 
        }
        else if (page.equals("CREATE_NOTA")) {
            cards.show(mainPanel, CreateNotaGUI.KEY);
        }
    }

    /**
     * Method untuk login pada sistem.
     * Jika gagal login akan mengembalikan boolean false dan jika berhasil login: <p>
     * - return boolean true <p>
     * - menampilkan halaman yang sesuai <p>
     *
     * @param id -> ID dari pengguna
     * @param password -> password dari pengguna
     * @return boolean yang menandakan apakah login berhasil atau gagal.
     * */
    public boolean login(String id, String password){
        for (Loginable panel:
                loginablePanel) {
            //Saat id dan password sesuai akan me return true
            if (true == panel.login(id, password)) return true; 
        }
        return false;
    }

    /**
     * Method untuk logout dari sistem, kemudian menampilkan halaman Home.
     * */
    public void logout(){
        for (Loginable panel:
                loginablePanel) {
            panel.logout();
        }
        navigateTo(HomeGUI.KEY);
    }

    public static void main(String[] args) {
        // menampilkan GUI kalian.
        // Jika ingin tau lebih lanjut mengapa menggunakan SwingUtilities.invokeLater
        // silakan akses https://stackoverflow.com/questions/6567870/what-does-swingutilities-invokelater-do
        // Tapi in general kalian tidak usah terlalu overthinking line ini selain fungsi utamanya adalah menampilkan GUI
        SwingUtilities.invokeLater(MainFrame::getInstance);
    }
}
