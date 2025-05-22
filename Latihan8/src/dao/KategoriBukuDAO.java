/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.Koneksi;
import model.KategoriBuku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriBukuDAO {

    public static List<KategoriBuku> getAll() {
        List<KategoriBuku> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori_buku";
        try (Connection conn = Koneksi.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KategoriBuku k = new KategoriBuku();
                k.setKodeKategori(rs.getString("kode_kategori"));
                k.setNamaKategori(rs.getString("nama_kategori"));
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
