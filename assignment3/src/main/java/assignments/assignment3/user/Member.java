package assignments.assignment3.user;

import java.util.ArrayList;

import assignments.assignment1.NotaGenerator;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.menu.MemberSystem;
public class Member {
    protected String id;
    protected String password;
    protected String nama;
    protected String noHPString;
    protected ArrayList<Nota> notaList = new ArrayList<Nota>();

    public Member(String nama, String id, String password) {
        this.nama = nama;
        this.id = id;
        this.password = password;
    }

    /**
     * Method otentikasi member dengan ID dan password yang diberikan.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    public boolean login(String id, String password) {
        return id.equals(this.id) && authenticate(password);
    }

    /**
     * Menambahkan nota baru ke NotaList instance member.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public void addNota(Nota nota) {
        this.notaList.add(nota);
    }

    /**
     * Method otentikasi member dengan password yang diberikan.
     *
     * @param password -> sandi password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    protected boolean authenticate(String password) {
        if (password.equals(this.password)) return true;
        return false;
    }

    // Dibawah ini adalah getter

    public String getNama() {
        return this.nama;
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<Nota> getNotaList() {
        return this.notaList;
    }
}