//Nama : Galen Taris Bariqi
//NPM : 2206029052
//Memulai Program

package assignments.assignment2;
//Mengimport library yang dibutuhkan
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;
import assignments.assignment1.NotaGenerator;
import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    //Membuat variabel untuk class Main Menu
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static ArrayList<Member> memberList = new ArrayList<Member>();
    private static int idNota = -1; //idNota mulai dari -1 agar idNota mulai dari 0 saat ditambah 1

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {                    //Looping untuk menjalankan program utama
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false; //Memberhentikan program saat user menginput 0
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali."); //Selain dari range 0-6, maka akan masuk program ini
            }
        }
        input.close();
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }
    //Methode untuk fitur generate member
    private static void handleGenerateUser() {
        System.out.println("Masukan nama Anda:");
        String nama = input.nextLine();
        System.out.println("Masukan nomor handphone Anda:");
        String noHP = input.nextLine();
        while (cekDigit(noHP)){                   //Looping untuk mengecek apakah hp itu hanya terdiri dari angka
            System.out.println("Field nomor hp hanya menerima digit.");
            noHP = input.nextLine();
        } Member member = new Member(nama, noHP); //Memasukkan parameter ke dalam constructor member
        if (member.cekID(memberList, member.getID())) System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHP); //saat ID Member sudah ada dalam memberlist
        else {                                    //Saat ID Memberbelum ada di memmberlist akan memasukkan ID Member kedalam memberlist
            System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getID());
            member.setArray(memberList, member);
        }
    }
    //Methode untuk fitur Generate Nota
    private static void handleGenerateNota() {
        System.out.println("Masukan ID member:");
        String id = input.nextLine();
        Member member = new Member();                             //Inisiasi constructor member agar bisa mengakses methode-methode dari class member
        if (!member.cekID(memberList, id)) System.out.printf("Member dengan ID %s tidak ditemukan!\n", id); //Saat user menginput ID Member yang belum terdaftar
        else {                                                    //Saat user menginput ID Member yang telah terdaftar
            System.out.println("Masukan paket laundry:");
            String paket = input.nextLine();
            while (!paket.equalsIgnoreCase("reguler") & !paket.equalsIgnoreCase("express") & !paket.equalsIgnoreCase("fast")){  //Looping untuk validasi input paket
                if (paket.equals("?")) NotaGenerator.showPaket(); //Saat user menginput "?"
                else {                                            //Saat user menginput selain 3 paket diatas dan ?
                    System.out.printf("""
Paket %s tidak diketahui
[ketik ? untuk mencari tahu jenis paket]""", paket);
                } System.out.println("\nMasukkan paket laundry:");
                paket = input.nextLine();
            }
            System.out.println("Masukan berat cucian Anda [Kg]:");
            int berat = 0;
            while(true) {                                              //Looping untuk validasi berat
                try {
                    berat = Integer.parseInt(input.nextLine());
                    if (berat <= 0) throw new NumberFormatException(); //Saat berat negatif dan 0
                    else if (berat < 2){                               //Saat berat kurang dari 2 kg akan dianggap 1 kg
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg Nota Laundry");
                        berat = 2;
                    }
                } catch(NumberFormatException e){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    continue;
                } break;
            } String tanggalMasuk = fmt.format(cal.getTime());
            member = cariMember(id);                                   //Membuat variabel member sesuai dengan id yang di input oleh user
            Nota nota = new Nota(member, paket.toLowerCase(), berat, tanggalMasuk);
            idNota += 1;                                               //Akan bertambah 1 terus setiap pesanan yang dilakukan oleh user
            nota.setIDNota(idNota);
            System.out.println("Berhasil menambahkan nota!");
            System.out.printf("[ID Nota = %d]\n", idNota);
            System.out.println(nota.getNota());
            if (member.getDiskon() == 3) member.resetBonus();          //Saat member dengan ID tertentu sudah melakukan pembelian tiap kelipatan 3 akan mereset bonuscounter
            System.out.println("Status      	: Belum bisa diambil :("); //Status bersifat statis di fitur generate Nota
            nota.setArray(notaList, nota);                             //Memasukkan Nota yang sudah dibuat oleh user kedalam notalist
        }
    }
    //Methode untuk fitur List Nota
    private static void handleListNota() {
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
        for (Nota element : notaList){        //Looping untuk print status Nota
            System.out.println(element.getStatus());
        }
    }
    //Methode untuk fitur List User
    private static void handleListUser() {
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for (Member element : memberList){    //Looping untuk print ID dan Nama Lengkap
            System.out.printf("- %s : %s\n", element.getID(), element.getNama());
        }
    }
    //Methode untuk fitur Ambil Cucian
    private static void handleAmbilCucian() {
        System.out.println("Masukan ID nota yang akan diambil:");
        int inputIDNota = 0;
        String inputID = "";
        while (true) {                                                  //Looping untuk validasi ID Nota
            try {
                inputID = input.nextLine();
                inputIDNota = Integer.parseInt(inputID);
                if (inputIDNota < 0) throw new NumberFormatException(); //Saat user menginput idNota kurang dari 0
            }
            catch (NumberFormatException e) {
                System.out.println("ID nota berbentuk angka!");
                continue;
            }
            break;
        }
        Nota nota = new Nota();                                         //Inisiasi variabel nota agar bisa mengakses methode-methode didalam class Nota
        if (!nota.cekIDNota(notaList, inputIDNota)) System.out.printf("Nota dengan ID %s tidak ditemukan!\n", inputID); //Saat ID Nota tidak ada dalam List Nota
        else {                                                          //Saat ID Nota ada dalam List Nota
            nota = cariNota(inputIDNota);                         
            System.out.println(nota.ambilCucian(notaList, inputID));
        }
    }
    //Methode untuk fitur Next Day
    private static void handleNextDay() {
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DAY_OF_MONTH, 1);      //Menambah 1 hari pada waktu di program
        Nota nota = new Nota();                 //Membuat variabel nota agar dapat mengakses methode didalamnya
        nota.setALLSisaHari(notaList);          //Mengubah sisa waktu pengerjaan pada semua nota
        System.out.println("""
Selamat pagi dunia!
Dek Depe: It's CuciCuci Time.""");
    }
    //Methode untuk fitur Print Menu
    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate Member");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List Member");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
    //Methode untuk mencari Member berdasarkan ID
    public static Member cariMember(String id){
        for (Member element : memberList){                    //Looping untuk mengecek satupersatu ID member dalam memberlist
            if (element.getID().equals(id)) return element;         //Saat ID member yang di input dan ID member yang ada dalam memberlist sama akan mereturn Member tersebut
        }
        return null;
    }
    //Methode untuk mencari Nota berdasarkan ID
    private static Nota cariNota(int inputIDNota){
        for (Nota element : notaList){                        //Looping untuk mengecek satupersatu ID member dalam notalist
            if (element.getIDNota() == inputIDNota) return element; //Saat ID Nota input dan ID Nota yang ada dalam notalist sama 
        }
        return null;
    }
}