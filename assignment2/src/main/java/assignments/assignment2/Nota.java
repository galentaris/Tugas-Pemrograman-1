package assignments.assignment2;
//Mengimport library yang dibutuhkan
import java.util.ArrayList;
import assignments.assignment1.NotaGenerator;

public class Nota {
    //Membuat Atribut class Nota
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;

    //Membuat constructor saat parameter 0 untuk memudahkan penggunaan methode-methode pada class Nota
    public Nota(){
        this.idNota = 0;
    }
    //Membuet constructor saat parameter 4 agar bisa dipassing dari MainMenu
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.isReady = false;
    }
    //Membuat methode setArray untuk memasukkan class Nota kedalam Arraylist Nota
    public void setArray(ArrayList<Nota> arr, Nota other){
        arr.add(other);
    }
    //Membuat methode setIDNota untuk set atribut idNota
    public void setIDNota(int idNota){
        this.idNota = idNota;
    }
    //Membuat methode getIDNota untuk get atribut idNota
    public int getIDNota(){
        return this.idNota;
    }
    //Membuat methode removeNota 
    public void removeNota(ArrayList<Nota> arr, int idNota){
        for (int i = 0; i < arr.size(); i++){                       //Looping untuk mengecek class Nota satupersatu
            if (arr.get(i).getIDNota() == idNota) arr.remove(i);    //Meremove class Nota dari Arraylist Nota sesuai dengan idNota yang udah diambil cuciannya
        }
    }
    //Membuat methode getNota untuk print Nota
    public String getNota(){
        if (this.paket.equalsIgnoreCase("express")) this.sisaHariPengerjaan = 1;              //Saat user menginput express
        else if(this.paket.equalsIgnoreCase("fast")) this.sisaHariPengerjaan = 2;             //Saat user menginput fast
        else if (this.paket.equalsIgnoreCase("reguler")) this.sisaHariPengerjaan = 3;         //Saat user menginput reguler
        this.member.setDiskon();
        //Mereturn Nota yang tidak diskon saat member melakukan pembelian selain kelipatan ke-3, begitu juga sebaliknya
        return this.member.getDiskon() != 3 ? NotaGenerator.generateNota(this.member.getID(), paket, berat, tanggalMasuk):NotaGenerator.generateNota(this.member.getID(), paket, berat, tanggalMasuk,this.member.getDiskon()); 
    }
    //Membuat methode getStatus
    public String getStatus(){
        //Mereturn status tidak dapat diambil saat sisa hari pengerjaan masih lebih dari 0, begitu juga sebaliknya
        return this.sisaHariPengerjaan > 0 ? String.format("- [%s] Status      	: Belum bisa diambil :(", this.idNota):String.format("- [%s] Status      	: Sudah dapat diambil!", this.idNota);
    }
    //Membuat methode cekIDNota
    public boolean cekIDNota(ArrayList<Nota> arr, int idNota){
        for (Nota element : arr){                             //Looping untuk mengecek satupersatu idNota dalam notalist 
            if (element.getIDNota() == idNota) return true;   
        } return false;
    }
    //Membuat methode ambilCucian
    public String ambilCucian(ArrayList<Nota> arr, String input){
        if (isReady) this.removeNota(arr, this.idNota);     //Saat cucian sudah diambil, idNota tersebut akan di remove
        //Mereturn nota gagal diambil saat cucian belum ready, begitupun sebaliknya
        return !isReady ? String.format("Nota dengan ID %s gagal diambil!", input):String.format("Nota dengan ID %s berhasil diambil!", input);
    }
    //Membuat methode getSisaHari
    public int getSisaHari(){
        return this.sisaHariPengerjaan;
    }
    //Membuat methode setSisaHari untuk mengurangi sisaHariPengerjaan dengan 1
    public void setSisaHari() {
        this.sisaHariPengerjaan -= 1;
    }
    //Membuat methode setALLSisaHari untuk mengurangi sisa Hari pengerjaan class nota yang ada dalam notaList
    public void setALLSisaHari(ArrayList<Nota> arr){
        for (Nota element : arr){
            element.setSisaHari();
            if (element.getSisaHari() <= 0) {         //Saat sisaHariPengerjaan kurang dari sama dengan 0 cucian pada nota tersebut akan siap diambil
                element.isReady = true;
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", element.getIDNota());
            }
        }
    }
}