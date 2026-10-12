public class Person {
    private String nim;
    private String nama;
    private int angkatan;
    private String jenisKelamin; // radio button

    public Person (String nim, String nama, int angkatan, String jenisKelamin){
        this.nim = nim;
        this.nama = nama;
        this.angkatan = angkatan;
        this.jenisKelamin = jenisKelamin;
    }

    // Setter & Getter
    public String getNim(){ return nim;}
    public void setNim(String nim){this.nim = nim;}

    public String getNama(){ return nama;}
    public void setNama(String nama){this.nama = nama;}

    public int getAngkatan(){ return angkatan; }
    public void setAngkatan(int angkatan){this.angkatan = angkatan;}

    public String getJenisKelamin(){ return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin){this.jenisKelamin = jenisKelamin;}
}