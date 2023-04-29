package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    boolean isDone = false;
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        if (berat <= 4) return 2000;
        return berat*500;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
