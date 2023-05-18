package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";
    private JTextArea notaText;

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
            new JButton("Saya Ingin Laundry"),
            new JButton("Lihat Detail Nota")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *3
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
            e -> createNota(),
            e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        initNotaFrame();
        if (loggedInMember.getNotaList().isEmpty()) {
            notaText.setText("Belum pernah Laundry di CuciCuci hiks :(");
            return;
        }

        String result = "";
        for (Nota nota : loggedInMember.getNotaList()) {
            result += nota.toString() + "\n";
        }
        notaText.setText(result);
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo("CREATE_NOTA");
    }

    private void initNotaFrame() {
        JFrame notaFrame = new JFrame("Detail Nota");
        notaFrame.setSize(400, 400);
        notaFrame.setVisible(true);
        notaFrame.setLocationRelativeTo(null);
        notaFrame.setLayout(new FlowLayout());
        notaFrame.setResizable(false);

        notaText = new JTextArea();
        notaText.setEditable(false);
        
        JScrollPane notaScroll = new JScrollPane(notaText, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        notaScroll.setViewportView(notaText);
        notaFrame.getContentPane().add(notaScroll);
        notaScroll.getViewport().setPreferredSize(new Dimension(350,300));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> notaFrame.dispose());

        notaFrame.add(okButton);
    }

}
