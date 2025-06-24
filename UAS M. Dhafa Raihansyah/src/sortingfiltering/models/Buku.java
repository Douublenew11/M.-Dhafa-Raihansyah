/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sortingfiltering.models;

import java.time.LocalDate;

/**
 *
 * @author shakt
 */
public class Buku {
    private String kodeBuku;
    private String judul;
    private String pengarang;
    private String penerbit;
    private String tahunTerbit;
    private String edisi;
    private LocalDate tanggalPengadaan;
    private String kodeKategori;
    private String namaKategori;

    public Buku(String kodeBuku, String judul, String pengarang, String penerbit, String tahunTerbit,
                String edisi, LocalDate tanggalPengadaan, String kodeKategori, String namaKategori) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.edisi = edisi;
        this.tanggalPengadaan = tanggalPengadaan;
        this.kodeKategori = kodeKategori;
        this.namaKategori = namaKategori;
    }

    public Buku(String kodeBuku, String judul, String pengarang, String penerbit, String tahunTerbit,
                String edisi, LocalDate tanggalPengadaan, String kodeKategori) {
        this(kodeBuku, judul, pengarang, penerbit, tahunTerbit, edisi, tanggalPengadaan, kodeKategori, null);
    }

    // Getters
    public String getKodeBuku() { return kodeBuku; }
    public String getJudul() { return judul; }
    public String getPengarang() { return pengarang; }
    public String getPenerbit() { return penerbit; }
    public String getTahunTerbit() { return tahunTerbit; }
    public String getEdisi() { return edisi; }
    public LocalDate getTanggalPengadaan() { return tanggalPengadaan; }
    public String getKodeKategori() { return kodeKategori; }
    public String getNamaKategori() { return namaKategori; }

    // Setters
    public void setKodeBuku(String kodeBuku) { this.kodeBuku = kodeBuku; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setPengarang(String pengarang) { this.pengarang = pengarang; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }
    public void setTahunTerbit(String tahunTerbit) { this.tahunTerbit = tahunTerbit; }
    public void setEdisi(String edisi) { this.edisi = edisi; }
    public void setTanggalPengadaan(LocalDate tanggalPengadaan) { this.tanggalPengadaan = tanggalPengadaan; }
    public void setKodeKategori(String kodeKategori) { this.kodeKategori = kodeKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
}


