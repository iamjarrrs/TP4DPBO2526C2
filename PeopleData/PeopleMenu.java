import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
* Class PeopleMenu bertindak sebagai frame utama GUI dan pengelola logika
* CRUD untuk data Mahasiswa
* */
public class PeopleMenu extends  JFrame{
    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTextField angkatanField;
    private JTable peopleTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JRadioButton lakiLakiRadioButton;
    private JRadioButton perempuanRadioButton;

    private ArrayList<Person> listPerson; // Menampung daftar objek Person (Mahasiswa)
    private ButtonGroup genderGroup;
    private int selectedIndex = -1; // Menyimpan indeks baris yang dipilih (-1 = tidak ada)


    public PeopleMenu() {
        listPerson = new ArrayList<>();

        // Grouping radioButton agar user hanya bisa memilih salah satu opsi jenis kelamin
        genderGroup = new ButtonGroup();
        genderGroup.add(lakiLakiRadioButton);
        genderGroup.add(perempuanRadioButton);
        lakiLakiRadioButton.setSelected(true);

        // memuat sata awal (dummy) dan menampilkannya
        populateList();
        setTable();

        // menyembunyikan tombol delet di awal karena belum ada baris tabel yang diklik
        deleteButton.setVisible(false);

        // ketika tombol add di tekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1){
                    insertdata();
                } else {
                    updateData();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0){
                    deleteData();
                }
            }
        });
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mendapatkan indeks baris yang diklik pengguna tabel
                selectedIndex = peopleTable.getSelectedRow();

                // Memindahkan nilai dari tabel ke field form input
                // Index kolom:
                /*
                * 0 = No
                * 1 = NIM
                * 2 = Nama
                * 3 = Angkatan
                * 4 = Jenis Kelamin
                * */
                nimField.setText(peopleTable.getValueAt(selectedIndex, 1).toString());
                namaField.setText(peopleTable.getValueAt(selectedIndex, 2).toString());
                angkatanField.setText(peopleTable.getValueAt(selectedIndex, 3).toString());

                // Mengatur pilihan RadioButton berdasarkan nilai pada tabel
                String jk = peopleTable.getValueAt(selectedIndex, 4).toString();
                if (jk.equalsIgnoreCase("Laki-Laki")){
                    lakiLakiRadioButton.setSelected(true);
                } else {
                    perempuanRadioButton.setSelected(true);
                }

                // Mengubah mode antarMuka ke mode Edit/Update
                addUpdateButton.setText("Update");
                deleteButton.setVisible(true); // memunculkan tombol delete
            }
        });
    }

    private void populateList(){
        listPerson.add(new Person("2301001", "Andi Pratama", 2023, "Laki-laki"));
        listPerson.add(new Person("2301002", "Siti Rahmawati", 2023, "Perempuan"));
        listPerson.add(new Person("2402015", "Budi Santoso", 2024, "Laki-laki"));
        listPerson.add(new Person("2402016", "Dewi Lestari", 2024, "Perempuan"));
        listPerson.add(new Person("2501801", "Fajar Hidayat", 2025, "Laki-laki"));
        listPerson.add(new Person("2501802", "Nabila Putri", 2025, "Perempuan"));
        listPerson.add(new Person("2501803", "Rizky Ramadhan", 2025, "Laki-laki"));
        listPerson.add(new Person("2501804", "Anisa Wijaya", 2025, "Perempuan"));
        listPerson.add(new Person("2603050", "Eko Prasetyo", 2026, "Laki-laki"));
        listPerson.add(new Person("2603051", "Maya Indah", 2026, "Perempuan"));
    }

    // untuk  merender / memperbarui data dari ArrayList ke dalam JTable
    public void setTable(){
        // Mendefinisikan header kolom tabel
        String[] columnNames = {"No", "NIM", "Nama", "Angkatan", "Jenis Kelamin"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // melakukan iterasi seluruh objek person di listPerson dan memasukkannya kedalam tabel
        for (int i = 0; i < listPerson.size(); i++){
            Person p = listPerson.get(i);
            Object[] rowData = {
                    (i + 1),
                    p.getNim(),
                    p.getNama(),
                    p.getAngkatan(),
                    p.getJenisKelamin()
            };
            model.addRow(rowData);
        }
            peopleTable.setModel(model);
    }

    // Method untuk mengambil teks nilai dari JRadioButton yang sedang terpilih
    private String getSelectedGender(){
        return lakiLakiRadioButton.isSelected() ? "Laki-laki" : "Perempuan";
    }

    public void insertdata(){
        try {
            // Mengambil input dari form
            String nim = nimField.getText();
            String nama = namaField.getText();
            int angkatan = Integer.parseInt(angkatanField.getText()); // Mengonversi teks angkatan ke integer
            String jenisKelamin = getSelectedGender();

            // Menambahkan objek Person baru ke ArrayList
            listPerson.add(new Person(nim, nama, angkatan, jenisKelamin));

            // Merender ulang tabel dan membersihkan form
            setTable();
            clearForm();

            // Notifikasi sukses
            JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil ditambahkan!");
        } catch (NumberFormatException ex) {
            // Menangani error jika input Angkatan bukan angka yang valid
            JOptionPane.showMessageDialog(this, "Angkatan harus berupa angka valid!");
        }
    }

    // Method untuk memperbarui data mahasiswa
    public void updateData(){
        try {
            // Mengambil nilai baru dari form
            String nim = nimField.getText();
            String nama = namaField.getText();
            int angkatan = Integer.parseInt(angkatanField.getText());
            String jenisKelamin = getSelectedGender();

            // Memperbarui atribut objek person pada indeks terpilih
            Person person = listPerson.get(selectedIndex);
            person.setNim(nim);
            person.setNama(nama);
            person.setAngkatan(angkatan);
            person.setJenisKelamin(jenisKelamin);

            // Merender ulang tabel dan membersihkan form
            setTable();
            clearForm();

            // Notifikasi Sukses
            JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil diUpdate");
        } catch (NumberFormatException ex){
            // Menangani Error jika input Angkatan bukan angka yang valid
            JOptionPane.showMessageDialog(this, "Angkatan harus berupa angka valid!");
        }
    }

    // Method untuk menghapus data mahasiswa
    public void deleteData(){
        // menampilkan Pop-up konfirmasi sebelum menghapus
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data mahasiswa ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        // jika pengguna menekan tombol 'YES'
        if (confirm == JOptionPane.YES_OPTION){
            listPerson.remove(selectedIndex);
            setTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil dihapus!");
        }
    }

    // Method untuk mereset/membersihkan field input form
    public void clearForm(){
        nimField.setText("");
        namaField.setText("");
        angkatanField.setText("");
        lakiLakiRadioButton.setSelected(true);

        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }

    // Main
    public static void main(String[] args){
        JFrame frame = new JFrame("Data Mahasiswa");
        PeopleMenu peopleMenuInstance = new PeopleMenu();
        frame.setContentPane(peopleMenuInstance.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
