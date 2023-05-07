package assignments.assignment3.nota.service;
//Mengimplementasikan semua methode dari interfave LaundryService
public class SetrikaService implements LaundryService{
    //Membuat atribut
    boolean isDone = false;
    
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {;
        return berat*1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
