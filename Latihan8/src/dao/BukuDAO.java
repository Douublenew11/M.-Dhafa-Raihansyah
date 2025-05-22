/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import db.Koneksi;
import model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    public static List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";
        try (Connection conn = Koneksi.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Buku b = new Buku();
                b.setKodeBuku(rs.getString("kode_buku"));
                b.setKodeKategori(rs.getString("kode_kategori"));
                b.setJudul(rs.getString("judul"));
                b.setPengarang(rs.getString("pengarang"));
                b.setPenerbit(rs.getString("penerbit"));
                b.setTahunTerbit(rs.getInt("tahun_terbit"));
                b.setEdisi(rs.getInt("edisi"));
                b.setTanggalPengadaan(rs.getDate("tanggal_pengadaan").toLocalDate());
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(Buku b) {
        String sql = "INSERT INTO buku VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeBuku());
            ps.setString(2, b.getKodeKategori());
            ps.setString(3, b.getJudul());
            ps.setString(4, b.getPengarang());
            ps.setString(5, b.getPenerbit());
            ps.setInt(6, b.getTahunTerbit());
            ps.setInt(7, b.getEdisi());
            ps.setDate(8, Date.valueOf(b.getTanggalPengadaan()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Buku b) {
        String sql = "UPDATE buku SET kode_kategori=?, judul=?, pengarang=?, penerbit=?, tahun_terbit=?, edisi=?, tanggal_pengadaan=? WHERE kode_buku=?";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeKategori());
            ps.setString(2, b.getJudul());
            ps.setString(3, b.getPengarang());
            ps.setString(4, b.getPenerbit());
            ps.setInt(5, b.getTahunTerbit());
            ps.setInt(6, b.getEdisi());
            ps.setDate(7, Date.valueOf(b.getTanggalPengadaan()));
            ps.setString(8, b.getKodeBuku());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String kodeBuku) {
        String sql = "DELETE FROM buku WHERE kode_buku=?";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeBuku);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}