//Nama : Galen Taris Bariqi
//Kelas : 2206029052
//Memulai Program
package assignments.assignment1;
//Mengimport library Scanner, LocalDate, dan DateTimeFormatter
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);
    //Membuat methode untuk validasi string apakah mengandung digit atau tidak
    public static boolean cekDigit(String str){
        str = str.replaceAll("\\s","");
        char[] arraychar = str.toCharArray();           //Menjadikan String menjadi array dengan tipe data char
        for(char c: arraychar){                         //Looping untuk mengecek satupersatu huruf pada string apakah ada yang digit atau tidak
            if (!Character.isDigit(c)){                 //Saat ada string mengandung yang bukan digit akan me-return true
                return true;
            }
        } return false;
    }
    //Membuat methode untuk validasi string apakah mengandung letter atau tidak
    public static boolean cekLetter(String str){
        str = str.replaceAll("\\s","");
        char[] arraychar = str.toCharArray();
        for(char c: arraychar){                         //Looping untuk mengecek satupersatu huruf pada string apakah mengandung letter atau tidak
            if (!Character.isLetter(c)){                //Saat string mengandung yang bukan letter akan me-return true
                return true;
            }
        } return false;
    }
    public static void main(String[] args) {
        while (true){
            printMenu();
            System.out.print("Pilihan : ");
            String pilihan = input.nextLine();
            if (pilihan.equals("0")) {                  //Saat user menginput 0 program akan berhenti
                System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                input.close();
                break;
            } else if (pilihan.equals("1")){            //Saat user menginput 1 akan masuk ke fitur generate id
                System.out.print("""
================================
Masukkan nama Anda:\n""");
                String nama = input.nextLine();
                while (cekLetter(nama)){                //Looping untuk memvalidasi nama apakah mengandung yang bukan letter
                    System.out.println("Nama hanya menerima letter");
                    nama = input.nextLine();
                }
                System.out.println("Masukkan nomor handphone Anda:");
                String nomor = input.nextLine();
                while (cekDigit(nomor)){                //Looping untuk memvalidasi nomor apakah mengandung yang bukan digit
                    System.out.println("Nomor hp hanya menerima digit");
                    nomor = input.nextLine();
                }
                System.out.printf("ID Anda : %s\n", generateId(nama, nomor));
            }
            else if (pilihan.equals("2")){              //Saat user menginput 2 akan masuk ke fitur generate nota
                System.out.print("""
================================
Masukkan nama Anda:\n""");
                String nama = input.nextLine();
                while (cekLetter(nama)){                //Looping untuk memvalidasi nomor apakah mengandung yang bukan letter
                    System.out.println("Nama hanya menerima letter");
                    nama = input.nextLine();
                }
                System.out.println("Masukkan nomor handphone Anda:");
                String nomor = input.nextLine();
                while (cekDigit(nomor)){                //Looping untuk memvalidasi nomor apakah mengandung yang bukan digit
                    System.out.println("Nomor hp hanya menerima digit");
                    nomor = input.nextLine();
                }
                System.out.println("Masukkan tanggal terima:");
                String tanggalTerima = input.nextLine();
                System.out.println("Masukkan paket laundry:");
                String paket = input.nextLine();
                while (!paket.equalsIgnoreCase("reguler") & !paket.equalsIgnoreCase("express") & !paket.equalsIgnoreCase("fast")){  //Looping untuk validasi input paket
                    if (paket.equals("?")){showPaket();}             //Saat user menginput "?"
                    else {                                           //Saat user menginpput selain 3 paket diatas dan ?
                        System.out.printf("""
Paket %s tidak diketahui
[ketik ? untuk mencari tahu jenis paket]""", paket);
                    } System.out.println("\nMasukkan paket laundry:");
                    paket = input.nextLine();
                }
                System.out.println("Masukkan berat cucian Anda [Kg]:");
                int berat2 = 0;
                while(true){                                                    //Looping untuk validasi berat
                    try{
                        String berat = input.nextLine();
                        berat2 = Integer.parseInt(berat);
                        if (berat2 <= 0) {throw new NumberFormatException();}   //Saat berat negatif dan 0
                        else if (berat2 < 2){                                   //Saat berat kurang dari 2 kg akan dianggap 1 kg
                            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg Nota Laundry");
                            berat2 = 2;
                        }
                    } catch(NumberFormatException e){
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        continue;
                    } break;
                }
                System.out.println(generateNota(generateId(nama, nomor), paket, berat2, tanggalTerima));
            } 
            else {System.out.println("Perintah tidak diketahui, silakan periksa kembali");} //Saat user menginput selain pilihan 0,1,dan 2
        }       
    }
    //Methode untuk mem-print Menu
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }
    //Methode untuk mem-print penjelasan paket
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    //Methode untuk fitur generate id
    public static String generateId(String nama, String nomorHP){
        String[] namaSplit = nama.split(" ");
        nomorHP = nomorHP.replaceAll("\\s","");
        String[] nomorArray = nomorHP.split("");
        String namaDepan = namaSplit[0].toUpperCase();
        String checksum = Integer.toString(hitungChecksum(nomorArray, namaDepan) % 100);
        if (checksum.length() <= 1) checksum = "0" + checksum;   //Saat nilai checksum berada di range 0-9, akan menambahkan "01" pada penulisan 
        String id = namaDepan + "-" + nomorHP + "-" + checksum;
        return id;
    }
    //Methode untuk menghitung checksum
    public static int hitungChecksum(String[] nomorArray, String idNama){
        int checksum = 0;
        for (String i : nomorArray){                    //Looping untuk menjumlahkan nomor HP
            int num = Integer.parseInt(i);              
            checksum += num;
        }
        byte[] arrayAscii = idNama.getBytes();          //Mengubah nama menjadi ascii code dalam array
        for(int ascii:arrayAscii){                      //Looping untuk menjumlahkan nilai pada nama
            int num = ascii - 64;                       //Karena nilai ascii code A 65, maka dikurangi 64 agar menjadi 1
            checksum += num; 
        }
        checksum += 7;
        return checksum;
    }
    //Methode untuk fitur generate Nota
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        String[] dateArray = tanggalTerima.split("/");                              //Memisahkan tanggal ke dalam array
        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[2]);
        LocalDate date = LocalDate.of(year,month,day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");    //Formatting tanggal terima
        int hargaperkg = 0;
        int durasi = 0;
        if (paket.equalsIgnoreCase("express")){                                     //Saat user menginput express
            hargaperkg = 12000;
            durasi = 1;
        }
        else if(paket.equalsIgnoreCase("fast")){                                    //Saat user menginput fast
            hargaperkg =10000;
            durasi = 2;
        }
        else if (paket.equalsIgnoreCase("reguler")){                                //Saat user menginput reguler
            hargaperkg = 7000;
            durasi = 3;
        }
        System.out.println("Nota Laundry");
        return String.format("""      
ID    : %s
Paket : %s
Harga :
%d kg x %d = %d
Tanggal Terima  : %s
Tanggal Selesai : %s""", id, paket.toLowerCase(), berat, hargaperkg, berat* hargaperkg, tanggalTerima, formatter.format(date.plusDays(durasi)));
    }
}