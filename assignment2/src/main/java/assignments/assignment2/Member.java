package assignments.assignment2;
//Mengimport library yang dibutuhkan
import assignments.assignment1.NotaGenerator;
import java.util.ArrayList;

public class Member {
    //Membuat atribut untuk class Member
    private String nama;
    private String noHP;
    private String id;
    private int bonusCounter;
    //membuat constructor dengan parameter kosong untuk memudahkan akses methode dalam class member
    public Member(){
        this.nama = "";
        this.noHP = "";
        this.bonusCounter = 0;
    }
    //Membuat constructor dengan parameter nama dan noHp agar bisa dipassing dari class Main Menu
    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHP = noHp;
        this.bonusCounter = 0;
    }
    //Membuat methode getID
    public String getID(){
        this.id = NotaGenerator.generateId(nama, noHP);
        return this.id;
    }
    //Membuat methode getNama
    public String getNama(){
        return this.nama;
    }
    //Membuat methode getNoHP
    public String getNoHP(){
        return this.noHP;
    }
    //Membuat methode setArray
    public void setArray(ArrayList<Member> arr, Member other){
        arr.add(other);
    }
    //Membuat methode cekID
    public boolean cekID(ArrayList<Member> arr, String otherID){
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).getID().equals(otherID)) return true;
        }
        return false;
    }
    //Membuat methode setDiskon
    public void setDiskon(){
        this.bonusCounter += 1;
    }
    //Membuat methode getDiskon
    public int getDiskon(){
        return this.bonusCounter;
    }
    //Membuat methode resetBonus
    public void resetBonus(){
        this.bonusCounter = 0;
    }
}