package assignments.assignment3;
//Mengimport library-library yang dibutuhkan
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment1.NotaGenerator;

import java.util.Scanner;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MainMenu {
    //Membuat atribut MainMenu
    private final Scanner in;
    private final LoginManager loginManager;

    /**
     * Entry point for the CuciCuci System application.
     *
     * @param args command line arguments, bisa kalian ignore.
     */
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new Scanner(System.in), new LoginManager(new EmployeeSystem(), new MemberSystem()));
        mainMenu.run();
    }
    //Membuat constructor MainMenu
    public MainMenu(Scanner in, LoginManager loginManager) {
        this.in = in;
        this.loginManager = loginManager;
    }

    /**
     * Menjalankan main menu.
     */
    public void run() {
        boolean exit = false;
        //Looping untuk menjalankan program
        while (!exit) {
            displayMenu();
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> toNextDay();
                case 4 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }    
        }
        in.close();
    }

    /**
     * Skips ke hari selanjutnya dan mengupdate sistem.
     */
    private void toNextDay() {
        System.out.println("Kamu tidur hari ini... zzz...");
        NotaManager.toNextDay();
    }

    /**
     * Mendaftarkan user pada sistem.
     */
    void register() {
        System.out.println("Masukan nama Anda: ");
        String nama = in.nextLine();
        System.out.println("Masukan nomor handphone Anda: ");
        String noHp = in.nextLine();

        //Looping untuk mengecek apakah hp itu hanya terdiri dari angka
        while (NotaGenerator.cekDigit(noHp)){                   
            System.out.println("Field nomor hp hanya menerima digit.");
            noHp = in.nextLine();
        }

        System.out.println("Masukan password Anda: ");
        String password = in.nextLine();
        Member registeredMember = loginManager.register(nama, noHp, password);
        if (registeredMember == null){ //Saat ada ID yang sama dengan user sebelumnya
            System.out.printf("User dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHp);
            return;
        }
        System.out.printf("Berhasil membuat user dengan ID %s!\n", registeredMember.getId());
    }

    /**
     * Meminta user untuk login dan memulai SystemCLI yang sesuai.
     */
    private void login() {
        System.out.print("Masukan ID Anda: ");
        String inputId = in.nextLine();
        System.out.print("Masukan password Anda: ");
        String inputPassword = in.nextLine();
        SystemCLI systemCLI = loginManager.getSystem(inputId);
        if (systemCLI == null){     //Saat user input ID yang tidak ada dalam memberList
            System.out.println("ID atau password invalid.");
            return;
        }
        systemCLI.login(in, inputId, inputPassword);
    }

    /**
     * Menampilkan menu
     */
    private void displayMenu() {
        System.out.println("\nSelamat datang di CuciCuci System!");
        System.out.printf("Sekarang tanggal %s\n", fmt.format(cal.getTime()));
        System.out.println("1. Login");
        System.out.println("2. Register Member");
        System.out.println("3. Tidur (Skip hari)");
        System.out.println("4. Exit");
        System.out.print("Apa yang ingin Anda lakukan hari ini? ");
    }
}