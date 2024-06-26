package assignments.assignment3;
//Mengimport libray-library yang dibutuhkan
import assignments.assignment1.NotaGenerator;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;

public class LoginManager {
    //Membuat atribut LoginManager
    private final EmployeeSystem employeeSystem;
    private final MemberSystem memberSystem;

    //Membuat constructor LoginManager
    public LoginManager(EmployeeSystem employeeSystem, MemberSystem memberSystem) {
        this.employeeSystem = employeeSystem;
        this.memberSystem = memberSystem;
    }

    /**
     * Method mapping dari ke SystemCLI yang sesuai.
     *
     * @param id -> ID dari user yang akan menggunakan SystemCLI
     * @return SystemCLI object yang sesuai dengan ID, null if  ID tidak ditemukan.
     */
    public SystemCLI getSystem(String id){
        if(memberSystem.isMemberExist(id)){ //Saat user menginput ID user berupa customer
            return memberSystem;
        }
        if(employeeSystem.isMemberExist(id)){ //Saat user menginput ID user berupa karyawan
            return employeeSystem;
        }
        return null;
    }

    /**
     * Mendaftarkan member baru dengan informasi yang diberikan.
     *
     * @param nama -> Nama member.
     * @param noHp -> Nomor handphone member.
     * @param password -> Password akun member.
     * @return Member object yang berhasil mendaftar, return null jika gagal mendaftar.
     */
    public Member register(String nama, String noHp, String password) {
        String id = NotaGenerator.generateId(nama, noHp);
        Member member = new Member(nama, id, password);
        if (memberSystem.cekID(id)) {
            return null;
        }
        memberSystem.addMember(member);

        return member;
    }
}