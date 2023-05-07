package assignments.assignment3.nota;
//Mengimport library-library yang dibutuhkan
import java.util.ArrayList;

import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment1.NotaGenerator;

public class Nota {
    //Membuat atribut class Nota
    private Member member;
    private String paket;
    private ArrayList<LaundryService> services = new ArrayList<LaundryService>();;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    private long minusHarga;
    public static int totalNota = 0; //totalNota untuk menghitung total pesanan laundry

    //Membuat constructor Nota
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.isDone = false;
        this.id = totalNota++;

        if (this.paket.equalsIgnoreCase("express")) {
            this.sisaHariPengerjaan = 1;              //Saat user menginput express
            this.baseHarga = 12000;
        }
        else if(this.paket.equalsIgnoreCase("fast")) {
            this.sisaHariPengerjaan = 2;              //Saat user menginput fast
            this.baseHarga = 10000;
        }
        else if (this.paket.equalsIgnoreCase("reguler")) {
            this.sisaHariPengerjaan = 3;              //Saat user menginput reguler
            this.baseHarga = 7000;
        }
    }
    //Membuat methode addService
    public void addService(LaundryService service){
        this.services.add(service);
    }
    //Membuat methode kerjakan
    public static String kerjakan(){
        String result = "";
        String statusSementara = "";
        //Looping untuk mendapatkan semua nota yang dipesan dari semua member
        for (Nota nota : NotaManager.notaList) {
            //Looping list services untuk mendapatkan semua service di nota tersebut
            for (int i = 0; i < nota.getServices().size(); i++) {
                if (!nota.getServices().get(i).isDone()) { //Saat service tertentu belum dilakukan
                    statusSementara = nota.getServices().get(i).doWork();
                    if (i == nota.getServices().size() - 1) nota.setIsDone(); //Set isDone = true saat mencapai service terakhir dari list
                    break;
                }
                else if (i == nota.getServices().size() - 1) { //Saat semua service sudah selesai
                    statusSementara = "Sudah selesai.";
                }
            }
            result += String.format("Nota %d : %s\n", nota.id, statusSementara);
        }
        return result;
    }
    //Membuat methode toNextDay
    public void toNextDay() {
        this.sisaHariPengerjaan -= 1;
        this.calculateMinusHarga();
    }
    //Membuat methode calculateHarga
    public long calculateHarga(){
        long tempTotalHarga = this.baseHarga*this.berat;
        for (LaundryService service : services) { //Looping untuk mendapatkan total harga dari masing-masing service
            tempTotalHarga += service.getHarga(this.berat);
        }
        return tempTotalHarga;
    }
    //Membuat methode calculateMinusHarga untuk menghitung harga yang minus
    public void calculateMinusHarga() {
        if (this.calculateHarga() > Math.abs(this.minusHarga)) {
            if (this.sisaHariPengerjaan < 0) this.minusHarga += 2000;
        }
    }
    //Membuat methode getNotaStatus
    public static String getNotaStatus(){
        String result = "";
        //Looping untuk mendapatkan semua nota
        for (Nota nota : NotaManager.notaList) {
            if (nota.isDone) result += String.format("Nota %d : %s\n", nota.id, "Sudah selesai."); 
            else if (!nota.isDone) result += String.format("Nota %d : %s\n", nota.id, "Belum selesai.");
        }
        return result;
    }
    //Methode untuk printing nota
    @Override
    public String toString(){
        String kompensasi = "";
        if (this.sisaHariPengerjaan < 0) kompensasi = String.format("Ada kompensasi keterlambatan %d * 2000 hari", Math.abs(this.sisaHariPengerjaan)); 
        return String.format("""
\n[ID Nota = %d]
%s
--- SERVICE LIST ---
%s
Harga Akhir: %d %s""", this.id, NotaGenerator.generateNota(this.member.getId(), this.paket, this.berat, this.tanggalMasuk), this.printService(), this.calculateHarga() - this.minusHarga, kompensasi);
    }
    //Methode untuk printing list service
    public String printService() {
        String result = "";
        for (int i = 0; i < this.services.size(); i++) {
            result += String.format("-%s @ Rp.%d", this.services.get(i).getServiceName(), this.services.get(i).getHarga(this.berat));
            if (i != this.services.size() - 1) result += "\n";
        }
        return result;
    }

    //Membuat setter

    public void setIsDone() {
        this.isDone = true;
    }

    //Membuat getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public ArrayList<LaundryService> getServices(){
        return this.services;
    }
}