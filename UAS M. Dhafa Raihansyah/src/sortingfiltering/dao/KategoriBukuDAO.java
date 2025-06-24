package sortingfiltering.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sortingfiltering.models.KategoriBuku;

public class KategoriBukuDAO {
    public static List<KategoriBuku> getAllKategori() {
        List<KategoriBuku> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM kategori_buku")) {
            while (rs.next()) {
                list.add(new KategoriBuku(rs.getString("kode_kategori"), rs.getString("nama_kategori")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
