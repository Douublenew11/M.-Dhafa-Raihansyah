/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controller;

import dao.BukuDAO;
import dao.KategoriBukuDAO;
import db.Koneksi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Buku;
import model.KategoriBuku;

import java.time.LocalDate;

public class MainController {

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

    @FXML private TableView<Buku> tableBuku;
    @FXML private TableColumn<Buku, String> colKodeBuku;
    @FXML private TableColumn<Buku, String> colKategori;
    @FXML private TableColumn<Buku, String> colJudul;
    @FXML private TableColumn<Buku, String> colPengarang;
    @FXML private TableColumn<Buku, String> colPenerbit;
    @FXML private TableColumn<Buku, Integer> colTahunTerbit;
    @FXML private TableColumn<Buku, Integer> colEdisi;
    @FXML private TableColumn<Buku, LocalDate> colTanggalPengadaan;

    private ObservableList<Buku> dataBuku;

    @FXML
    public void initialize() {
   
        cmbKategori.setItems(FXCollections.observableArrayList(KategoriBukuDAO.getAll()));

       
        colKodeBuku.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKodeBuku()));
        colKategori.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKodeKategori()));
        colJudul.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getJudul()));
        colPengarang.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPengarang()));
        colPenerbit.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPenerbit()));
        colTahunTerbit.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getTahunTerbit()).asObject());
        colEdisi.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEdisi()).asObject());
        colTanggalPengadaan.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTanggalPengadaan()));

        loadData();
    }

    private void loadData() {
        dataBuku = FXCollections.observableArrayList(BukuDAO.getAll());
        tableBuku.setItems(dataBuku);
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
        if (tableBuku.getSelectionModel().getSelectedItem() == null) {
            showAlert("Pilih data terlebih dahulu!");
            return;
        }
        if (isInputValid()) {
            Buku b = getBukuFromForm();
            BukuDAO.update(b);
            loadData();
            clearForm();
        }
    }

    @FXML
    private void deleteBuku() {
        Buku b = tableBuku.getSelectionModel().getSelectedItem();
        if (b == null) {
            showAlert("Pilih data yang ingin dihapus!");
            return;
        }
        BukuDAO.delete(b.getKodeBuku());
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

    private Buku getBukuFromForm() {
        Buku b = new Buku();
        b.setKodeBuku(txtKodeBuku.getText());
        b.setKodeKategori(cmbKategori.getValue().getKodeKategori());
        b.setJudul(txtJudul.getText());
        b.setPengarang(txtPengarang.getText());
        b.setPenerbit(txtPenerbit.getText());
        b.setTahunTerbit(Integer.parseInt(txtTahunTerbit.getText()));
        b.setEdisi(Integer.parseInt(txtEdisi.getText()));
        b.setTanggalPengadaan(dpTanggalPengadaan.getValue());
        return b;
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
}
