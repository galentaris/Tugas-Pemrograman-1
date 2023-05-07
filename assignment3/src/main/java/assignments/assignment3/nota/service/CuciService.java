package assignments.assignment3.nota.service;
//Mengimplementasikan semua methode dari interfave LaundryService
public class CuciService implements LaundryService{
    //Membuat atribut
    boolean isDone = false;
    
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
