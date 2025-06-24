package sortingfiltering.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sortingfiltering.models.Buku;
import sortingfiltering.models.KategoriBuku;

public class BukuDAO {

    // Tambah buku ke database
    public static void insert(Buku b) {
        String sql = "INSERT INTO buku (kode_buku, kode_kategori, judul, pengarang, penerbit, tahun_terbit, edisi, tanggal_pengadaan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getKodeBuku());
            ps.setString(2, b.getKodeKategori());
            ps.setString(3, b.getJudul());
            ps.setString(4, b.getPengarang());
            ps.setString(5, b.getPenerbit());
            ps.setString(6, b.getTahunTerbit());
            ps.setString(7, b.getEdisi());
            ps.setDate(8, Date.valueOf(b.getTanggalPengadaan()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil semua buku dengan filter & sorting
    public static List<Buku> getAllBuku(String judulFilter, LocalDate from, LocalDate to, String kodeKategori, String sortOption) {
        List<Buku> list = new ArrayList<>();

        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "LEFT JOIN kategori_buku k ON b.kode_kategori = k.kode_kategori WHERE 1=1";

        if (judulFilter != null && !judulFilter.isEmpty()) sql += " AND b.judul LIKE ?";
        if (from != null && to != null) sql += " AND b.tanggal_pengadaan BETWEEN ? AND ?";
        if (kodeKategori != null) sql += " AND b.kode_kategori = ?";

        if (sortOption != null) {
            switch (sortOption) {
                case "Judul A-Z": sql += " ORDER BY b.judul ASC"; break;
                case "Judul Z-A": sql += " ORDER BY b.judul DESC"; break;
                case "Terbaru": sql += " ORDER BY b.tanggal_pengadaan DESC"; break;
                case "Terlama": sql += " ORDER BY b.tanggal_pengadaan ASC"; break;
            }
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            int idx = 1;
            if (judulFilter != null && !judulFilter.isEmpty()) stmt.setString(idx++, "%" + judulFilter + "%");
            if (from != null && to != null) {
                stmt.setDate(idx++, Date.valueOf(from));
                stmt.setDate(idx++, Date.valueOf(to));
            }
            if (kodeKategori != null) stmt.setString(idx++, kodeKategori);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Buku b = new Buku(
                    rs.getString("kode_buku"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("penerbit"),
                    rs.getString("tahun_terbit"),
                    rs.getString("edisi"),
                    rs.getDate("tanggal_pengadaan").toLocalDate(),
                    rs.getString("kode_kategori"),
                    rs.getString("nama_kategori")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public static void update(Buku b) {
        String sql = "UPDATE buku SET kode_kategori=?, judul=?, pengarang=?, penerbit=?, tahun_terbit=?, edisi=?, tanggal_pengadaan=? WHERE kode_buku=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeKategori());
            ps.setString(2, b.getJudul());
            ps.setString(3, b.getPengarang());
            ps.setString(4, b.getPenerbit());
             ps.setString(5, b.getTahunTerbit());
            ps.setString(6, b.getEdisi());
            ps.setDate(7, Date.valueOf(b.getTanggalPengadaan()));
            ps.setString(8, b.getKodeBuku());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String kodeBuku) {
        String sql = "DELETE FROM buku WHERE kode_buku=?";
         try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeBuku);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}