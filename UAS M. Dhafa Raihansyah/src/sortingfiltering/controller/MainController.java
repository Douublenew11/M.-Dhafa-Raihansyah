/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sortingfiltering.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.input.MouseEvent;
import sortingfiltering.Main;

import sortingfiltering.dao.BukuDAO;
import sortingfiltering.dao.KategoriBukuDAO;
import sortingfiltering.models.Buku;
import sortingfiltering.models.KategoriBuku;
import sortingfiltering.models.Session;

public class MainController {

    @FXML private TableView<Buku> tableBuku;
    @FXML private TableColumn<Buku, String> colKode, colJudul, colPengarang, colPenerbit, colTahun, colEdisi, colKategori;
    @FXML private TableColumn<Buku, LocalDate> colTanggal;

    @FXML private TextField tfJudul;
    @FXML private DatePicker dpFrom, dpTo;
    @FXML private ComboBox<KategoriBuku> cbKategori;
    @FXML private ComboBox<String> cbSort;
    @FXML private Button btnFilter, btnReset;
    
    @FXML private TextField txtKodeBuku;
    @FXML private ComboBox<KategoriBuku> cmbKategori;
    @FXML private TextField txtJudul;
    @FXML private TextField txtPengarang;
    @FXML private TextField txtPenerbit;
    @FXML private TextField txtTahunTerbit;
    @FXML private TextField txtEdisi;
    @FXML private DatePicker dpTanggalPengadaan;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    public void initialize() {
            
        if (Session.getInstance().getUsername() == null) {
        try {
            Main.changeScene("/filtersort/View/Login.fxml","Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
        // Isi ComboBox kategori dan sorting
        cbKategori.getItems().addAll(KategoriBukuDAO.getAllKategori());
        cbSort.getItems().addAll("Judul A-Z", "Judul Z-A", "Terbaru", "Terlama");
        cmbKategori.setItems(FXCollections.observableArrayList(KategoriBukuDAO.getAllKategori()));
        

        initTable();  // Konfigurasi kolom table
        loadData();   // Muat data awal
    }

    private void initTable() {
        colKode.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getKodeBuku()));
        colJudul.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getJudul()));
        colPengarang.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPengarang()));
        colPenerbit.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPenerbit()));
        colTahun.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTahunTerbit()));
        colEdisi.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEdisi()));
        colTanggal.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTanggalPengadaan()));
        colKategori.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNamaKategori())); // FIXED
    }

    @FXML private void onFilter() {
        loadData();
    }
    @FXML
    private void addBuku() {
        if (isInputValid()) {
            Buku b = getBukuFromForm();
            BukuDAO.insert(b);
            loadData();
            clearForm();
        }
    }
    
   @FXML
private void updateBuku() {
    // 1. Pastikan baris di‑tabel dipilih
    Buku selected = tableBuku.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("Pilih data terlebih dahulu!");
        return;
    }

    // 2. Validasi kolom input
    if (!isInputValid()) return;

    // 3. Ambil data terbaru dari form (kode buku boleh diubah atau tidak—sesuai kebutuhanmu)
    Buku b = getBukuFromForm();

    // 4. Update di database
    BukuDAO.update(b);

    // 5. Refresh tampilan dan bersihkan form
    loadData();
    clearForm();
}

@FXML
    private void handleTableClick(MouseEvent event) {
        Buku selected = tableBuku.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtKodeBuku.setText(selected.getKodeBuku());
            cmbKategori.setValue(cmbKategori.getItems().stream()
                .filter(k -> k.getKodeKategori().equals(selected.getKodeKategori()))
                .findFirst().orElse(null));
            txtJudul.setText(selected.getJudul());
            txtPengarang.setText(selected.getPengarang());
            txtPenerbit.setText(selected.getPenerbit());
            txtTahunTerbit.setText(String.valueOf(selected.getTahunTerbit()));
            txtEdisi.setText(String.valueOf(selected.getEdisi()));
            dpTanggalPengadaan.setValue(selected.getTanggalPengadaan());
        }
    }

@FXML
private void deleteBuku() {
    // 1. Pastikan baris di‑tabel dipilih
    Buku selected = tableBuku.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("Pilih data yang ingin dihapus!");
        return;
    }

    // 2. Hapus di database berdasar kode_buku terpilih
    BukuDAO.delete(selected.getKodeBuku());

    // 3. Refresh tampilan dan bersihkan form
    loadData();
    clearForm();
}

    
    

    @FXML private void onReset() {
        tfJudul.clear();
        dpFrom.setValue(null);
        dpTo.setValue(null);
        cbKategori.getSelectionModel().clearSelection();
        cbSort.getSelectionModel().clearSelection();
        loadData();
    }
    
    private boolean isInputValid() {
        if (txtKodeBuku.getText().isEmpty() || cmbKategori.getValue() == null ||
            txtJudul.getText().isEmpty() || txtPengarang.getText().isEmpty() ||
            txtPenerbit.getText().isEmpty() || txtTahunTerbit.getText().isEmpty() ||
            txtEdisi.getText().isEmpty() || dpTanggalPengadaan.getValue() == null) {
            showAlert("Semua data harus diisi!");
            return false;
        }
        return true;
    }
    
     private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
     
     private Buku getBukuFromForm() {
    KategoriBuku kategori = cmbKategori.getValue();
    return new Buku(
        txtKodeBuku.getText(),
        txtJudul.getText(),
        txtPengarang.getText(),
        txtPenerbit.getText(),
        txtTahunTerbit.getText(),
        txtEdisi.getText(),
        dpTanggalPengadaan.getValue(),
        kategori.getKodeKategori(),
        kategori.getNamaKategori()
    );
}

     private void clearForm() {
        txtKodeBuku.clear();
        cmbKategori.getSelectionModel().clearSelection();
        txtJudul.clear();
        txtPengarang.clear();
        txtPenerbit.clear();
        txtTahunTerbit.clear();
        txtEdisi.clear();
        dpTanggalPengadaan.setValue(null);
    }


    private void loadData() {
        String judul = tfJudul.getText();
        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();
        KategoriBuku kategori = cbKategori.getValue();
        String sort = cbSort.getValue();

        // Kirim kode_kategori ke DAO (bukan objek)
        String kodeKategori = (kategori != null) ? kategori.getKodeKategori() : null;

        ObservableList<Buku> data = FXCollections.observableArrayList(
            BukuDAO.getAllBuku(judul == null ? "" : judul, from, to, kodeKategori, sort)
        );
        tableBuku.setItems(data);
    }
}
