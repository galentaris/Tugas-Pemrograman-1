package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";
    private JTextArea kerjakanText;
    private JTextArea displayText;

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


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
            JOptionPane.showMessageDialog(null, "Belum ada Nota!", "Info",JOptionPane.ERROR_MESSAGE);
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
        if (NotaManager.notaList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nothing to cuci here!", "Info",JOptionPane.ERROR_MESSAGE);
            return;
        }
        initKerjakanFrame();
        kerjakanText.setText(Nota.kerjakan());
    }

    private void initKerjakanFrame() {
        JOptionPane.showMessageDialog(null, String.format("Stand back! %s beginning to nyuci!\n", loggedInMember.getNama()),"Info",JOptionPane.INFORMATION_MESSAGE);

        JFrame notaFrame = new JFrame("Detail Nota");
        notaFrame.setSize(250, 200);
        notaFrame.setVisible(true);
        notaFrame.setLocationRelativeTo(null);
        notaFrame.setLayout(new FlowLayout());
        notaFrame.setResizable(false);

        kerjakanText = new JTextArea();
        kerjakanText.setEditable(false);

        JScrollPane notaScroll = new JScrollPane(kerjakanText);
        notaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notaScroll.setViewportView(kerjakanText);
        notaScroll.getViewport().setPreferredSize(new Dimension(200,100));
        notaFrame.add(notaScroll);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> notaFrame.dispose());

        notaFrame.add(okButton);

    }

    private void initDisplayFrame() {
        JFrame notaFrame = new JFrame("Detail Nota");
        notaFrame.setSize(250, 200);
        notaFrame.setVisible(true);
        notaFrame.setLocationRelativeTo(null);
        notaFrame.setLayout(new FlowLayout());
        notaFrame.setResizable(false);

        displayText = new JTextArea();
        displayText.setEditable(false);

        JScrollPane notaScroll = new JScrollPane(displayText);
        notaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notaScroll.setViewportView(displayText);
        notaScroll.getViewport().setPreferredSize(new Dimension(200,100));
        notaFrame.add(notaScroll);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> notaFrame.dispose());

        notaFrame.add(okButton);
    }
}
