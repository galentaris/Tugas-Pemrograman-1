package assignments.assignment3.user;

public class Employee extends Member {
    //Membuat atribut Employee
    public static int employeeCount = 0;
    
    //Membuat constructor Employee
    public Employee(String nama, String password) {
        super(nama, generateId(nama), password);
    }

    /**
     * Membuat ID employee dari nama employee dengan format
     * NAMA_DEPAN-[jumlah employee, mulai dari 0]
     * Contoh: Dek Depe adalah employee pertama yang dibuat, sehingga IDnya adalah DEK-0
     *
     * @param nama -> Nama lengkap dari employee
     */
    private static String generateId(String nama) {
        String namaDepan = nama.split(" ")[0].toUpperCase();
        String id = String.format("%s-%d", namaDepan, employeeCount++);
        return id;
    }
}
