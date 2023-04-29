package assignments.assignment3.nota;
import java.util.ArrayList;

import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment1.NotaGenerator;

public class Nota {
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
    public static int totalNota = 0;

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

    public void addService(LaundryService service){
        this.services.add(service);
    }

    public static String kerjakan(){
        String result = "";
        String statusSementara = "";
        for (Nota nota : NotaManager.notaList) {
            for (int i = 0; i < nota.getServices().size(); i++) {
                if (!nota.getServices().get(i).isDone()) {
                    statusSementara = nota.getServices().get(i).doWork();
                    if (i == nota.getServices().size() - 1) nota.setIsDone();
                    break;
                }
                else if (i == nota.getServices().size() - 1) {
                    statusSementara = "Sudah selesai.";
                }
            }
            result += String.format("Nota %d : %s\n", nota.id, statusSementara);
        }
        return result;
    }
    public void toNextDay() {
        this.sisaHariPengerjaan -= 1;
        this.calculateMinusHarga();
    }

    public long calculateHarga(){
        long tempTotalHarga = this.baseHarga*this.berat;
        for (LaundryService service : services) {
            tempTotalHarga += service.getHarga(this.berat);
        }
        return tempTotalHarga;
    }

    public void calculateMinusHarga() {
        if (this.calculateHarga() > Math.abs(this.minusHarga)) {
            if (this.sisaHariPengerjaan < 0) this.minusHarga += 2000;
        }
    }

    public static String getNotaStatus(){
        String result = "";
        for (Nota nota : NotaManager.notaList) {
            if (nota.isDone) result += String.format("Nota %d : %s\n", nota.id, "Sudah selesai."); 
            else if (!nota.isDone) result += String.format("Nota %d : %s\n", nota.id, "Belum selesai.");
        }
        return result;
    }

    @Override
    public String toString(){
        String kompensasi = "";
        if (this.sisaHariPengerjaan < 0) kompensasi = String.format("Ada kompensasi keterlambatan %d * 2000 hari", Math.abs(this.sisaHariPengerjaan)); 
        return String.format("""
[ID Nota = %d]
%s
--- SERVICE LIST ---
%s
Harga Akhir: %d %s
""", this.id, NotaGenerator.generateNota(this.member.getId(), this.paket, this.berat, this.tanggalMasuk), this.printService(), this.calculateHarga() - this.minusHarga, kompensasi);
    }

    public void setIsDone() {
        this.isDone = true;
    }

    // Dibawah ini adalah getter

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

    public String printService() {
        String result = "";
        for (int i = 0; i < this.services.size(); i++) {
            result += String.format("-%s @ Rp.%d", this.services.get(i).getServiceName(), this.services.get(i).getHarga(this.berat));
            if (i != this.services.size() - 1) result += "\n";
        }
        return result;
    }

    public ArrayList<LaundryService> getServices(){
        return this.services;
    }
}
