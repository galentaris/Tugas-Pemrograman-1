package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;
    private GridBagConstraints gbc = new GridBagConstraints();
    private String[] arrNamaPaket = {"Express", "Fast", "Reguler"};


    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;


        // Set up main panel, Feel free to make any changes
        paketLabel = new JLabel("Paket Laundry:");

        beratLabel = new JLabel("Berat Cucian (Kg)");

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");

        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4kg pertama, kemudian 500 / kg)");

        createNotaButton = new JButton("Buat Nota");
        createNotaButton.addActionListener(e -> createNota());
        createNotaButton.setPreferredSize(new Dimension(600, 25));

        backButton = new JButton("Kembali");
        backButton.addActionListener(e -> handleBack());
        backButton.setPreferredSize(new Dimension(600, 25));

        paketComboBox = new JComboBox(arrNamaPaket);

        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(e -> showPaket());
        showPaketButton.setPreferredSize(new Dimension(100, 25));

        beratTextField = new JTextField();
        beratTextField.setPreferredSize(new Dimension(75, 25));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(paketLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(beratLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(setrikaCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(antarCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(createNotaButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(paketComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(beratTextField, gbc);

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(showPaketButton, gbc);

    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        String paket = (String) paketComboBox.getSelectedItem();

        int berat = 0;
        String statusTambahan =  "";
        try {
            berat = Integer.parseInt(beratTextField.getText());
            if (berat <= 0) throw new NumberFormatException(); //Saat berat negatif dan 0

            //Saat berat kurang dari 2 kg akan dianggap 1 kg
            else if (berat < 2){   
                statusTambahan = "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg";
                berat = 2;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Berat harus angka positif!","Info",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Nota nota = new Nota(memberSystemGUI.getLoggedInMember(), berat, paket, fmt.format(cal.getTime()));

        LaundryService serviceSetrika = new SetrikaService();
        LaundryService serviceAntar = new AntarService();
        LaundryService serviceCuci = new CuciService();

        nota.addService(serviceCuci);

        if (setrikaCheckBox.isSelected()) {
            nota.addService(serviceSetrika);
        }
        if (antarCheckBox.isSelected()) {
            nota.addService(serviceAntar);
        }
        JOptionPane.showMessageDialog(null, "Nota berhasil dibuat!\n" + statusTambahan,"Info",JOptionPane.INFORMATION_MESSAGE);

        memberSystemGUI.getLoggedInMember().addNota(nota);
        NotaManager.addNota(nota);

        beratTextField.setText("");
        paketComboBox.setSelectedItem("Express");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo("MEMBER");
        beratTextField.setText("");
        paketComboBox.setSelectedItem("Express");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}