package assignments.assignment3.user.menu;

//Mengimport library library yang dibutuhkan
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment1.NotaGenerator;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        //Saat user input choice = 1
        if (choice == 1) {
            System.out.println("Masukan paket laundry: ");
            NotaGenerator.showPaket();
            String paket = in.nextLine();

            //Looping untuk validasi paket
            while (!paket.equalsIgnoreCase("reguler") & !paket.equalsIgnoreCase("express") & !paket.equalsIgnoreCase("fast")){  //Looping untuk validasi input paket
                if (paket.equals("?")) NotaGenerator.showPaket();  //Saat user menginput "?"
                else {                                                      //Saat user menginput selain 3 paket diatas dan ?
                    System.out.printf("""
Paket %s tidak diketahui
[ketik ? untuk mencari tahu jenis paket]""", paket);
                } 
                System.out.println("\nMasukkan paket laundry:");
                paket = in.nextLine();
            }

            System.out.println("Masukan berat cucian anda [Kg]: ");

            int berat = 0;

            //Looping untuk validasi berat
            while(true) {                                                  
                try {
                    berat = Integer.parseInt(in.nextLine());
                    if (berat <= 0) throw new NumberFormatException(); //Saat berat negatif dan 0
                    else if (berat < 2){                               //Saat berat kurang dari 2 kg akan dianggap 1 kg
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                        berat = 2;
                    }
                } catch(NumberFormatException e){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    continue;
                } break;
            } 
            //Membuat instance nota dan service-service
            Nota nota = new Nota(loginMember, berat, paket, fmt.format(cal.getTime()));
            LaundryService serviceSetrika = new SetrikaService();
            LaundryService serviceAntar = new AntarService();
            LaundryService serviceCuci = new CuciService();

            nota.addService(serviceCuci);

            System.out.printf("""
Apakah kamu ingin cucianmu disetrika oleh staff professional kami?
Hanya tambah 1000 / kg :0
[Ketik x untuk tidak mau]:  """);

            String inputSetrika = in.nextLine();

            //Saat user menginput selain x akan memasukkan serviceSetrika ke list services
            if (!inputSetrika.equalsIgnoreCase("x")) {
                nota.addService(serviceSetrika);
            }

            System.out.printf("""
Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!
Cuma 2000 / 4kg, kemudian 500 / kg
[Ketik x untuk tidak mau]:  """);

            String inputAntar = in.nextLine();

            //Saat user menginput selain x akan memasukkan serviceAntar ke list services
            if (!inputAntar.equalsIgnoreCase("x")) {
                nota.addService(serviceAntar);
            }

            System.out.println("Nota berhasil dibuat!");
            //Menambahkan nota ke masing-masing notalist, yang loginMember dan yang semua nota
            loginMember.addNota(nota);
            NotaManager.addNota(nota);
        }
        //Saat user input choice = 2
        else if (choice == 2) {
            for (Nota nota : loginMember.getNotaList()) {
                System.out.println(nota);
            }
        }
        //Saat user input choice = 3
        else if (choice == 3) {
            logout = true;
        }
        //Saat user input choice selain 1,2,3
        else {
            System.out.println("Pilihan tidak valid, silakan coba lagi.");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        memberList.add(member);
    }
    //Membuat methode untuk melakukan check ID apakah sama atau tidak
    public boolean cekID(String otherID) {
        for (int i = 0; i < memberList.size(); i++){
            if (memberList.get(i).getId().equals(otherID)) return true; //Saat ID yang dibuat user sama, akan return true
        }
        return false;
    }
}