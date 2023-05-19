package assignments.assignment4.gui.member.employee;
//Mengimport library-library
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    //Mendefinisikan atribut atribut yang dibutuhkan
    public static final String KEY = "EMPLOYEE";
    private JTextArea kerjakanText;
    private JTextArea displayText;

    //Membuat constructor untuk EmployeeSystemGUI
    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }
    //Membuat getter
    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
            new JButton("It's nyuci time"),
            new JButton("Display List Nota")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        if (NotaManager.notaList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Belum ada Nota!", "Info", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initDisplayFrame();
        displayText.setText(Nota.getNotaStatus());
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        if (NotaManager.notaList.isEmpty()) { //Saat belum ada member yang memesan nota
            JOptionPane.showMessageDialog(null, "Nothing to cuci here!", "Info", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initKerjakanFrame();

        kerjakanText.setText(Nota.kerjakan());
    }

    //Membuat methode initKerjakanFrame untuk init gui frame kerjakan
    private void initKerjakanFrame() {
        JOptionPane.showMessageDialog(null, String.format("Stand back! %s beginning to nyuci!\n", loggedInMember.getNama()),"Info",JOptionPane.INFORMATION_MESSAGE);

        //Mengatur Frame untuk kerjakan
        JFrame notaFrame = new JFrame("Detail Nota");
        notaFrame.setSize(250, 200);
        notaFrame.setVisible(true);
        notaFrame.setLocationRelativeTo(null);
        notaFrame.setLayout(new FlowLayout());
        notaFrame.setResizable(false);

        //Mengatur text area yang berisi kerjakan
        kerjakanText = new JTextArea();
        kerjakanText.setEditable(false);

        //Mengatur scrollPane
        JScrollPane notaScroll = new JScrollPane(kerjakanText);
        notaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notaScroll.setViewportView(kerjakanText);
        notaScroll.getViewport().setPreferredSize(new Dimension(200,100));

        notaFrame.add(notaScroll);

        //Mengatur button OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> notaFrame.dispose());

        notaFrame.add(okButton);
    }

    //Methode initDisplayFrame untuk menginisiasi gui dari frame Display
    private void initDisplayFrame() {
        //Mengatur Frame untuk Detail Nota
        JFrame notaFrame = new JFrame("Detail Nota");
        notaFrame.setSize(250, 200);
        notaFrame.setVisible(true);
        notaFrame.setLocationRelativeTo(null);
        notaFrame.setLayout(new FlowLayout());
        notaFrame.setResizable(false);

        //Mengatur displayText untuk 
        displayText = new JTextArea();
        displayText.setEditable(false);

        //Mengatur notaScroll
        JScrollPane notaScroll = new JScrollPane(displayText);
        notaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notaScroll.setViewportView(displayText);
        notaScroll.getViewport().setPreferredSize(new Dimension(200,100));

        notaFrame.add(notaScroll);

        //Mengatur button OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> notaFrame.dispose());

        notaFrame.add(okButton);
    }
}
