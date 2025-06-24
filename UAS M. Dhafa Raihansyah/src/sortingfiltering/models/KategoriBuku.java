/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sortingfiltering.models;

/**
 *
 * @author shakt
 */
public class KategoriBuku {
    private String kodeKategori;
    private String namaKategori;

    public KategoriBuku(String kodeKategori, String namaKategori) {
        this.kodeKategori = kodeKategori;
        this.namaKategori = namaKategori;
    }

    public String getKodeKategori() { return kodeKategori; }
    public String getNamaKategori() { return namaKategori; }

    @Override
    public String toString() { return namaKategori; }
}