/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sortingfiltering.dao;

import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author shakt
 */
public class DBConnection {

    // URL koneksi database MySQL
    // Format: jdbc:mysql://[host]:[port]/[nama_database]
    private static final String URL = "jdbc:mysql://localhost:3306/uas_dhafa";

    // Username untuk mengakses database
    private static final String USER = "root";

    // Password untuk mengakses database (kosong dalam contoh ini)
    private static final String PASSWORD = "";

    /**
     * Method untuk koneksi ke database
     * @return Connection objek koneksi database
     * @throws SQLException jika terjadi error saat koneksi
     */
    public static java.sql.Connection getConnection() throws SQLException {
        // Menggunakan DriverManager untuk membuat koneksi
        // dengan parameter URL, USER, dan PASSWORD yang telah didefinisikan
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}