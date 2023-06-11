/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Ardi Setyiawan
 */
public class Obat {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private String nama;
    private Double harga;
    private String jenis;
    private int stok;
    private String bahan;
    private String gambar;
    
    public Obat(String nama, Double harga, String jenis, int stok, String bahan, String gambar) {
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
        this.stok = stok;
        this.bahan = bahan;
        this.gambar = gambar;
    }


    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the harga
     */
    public Double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(Double harga) {
        this.harga = harga;
    }

    /**
     * @return the jenis
     */
    public String getJenis() {
        return jenis;
    }

    /**
     * @param jenis the jenis to set
     */
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    /**
     * @return the stok
     */
    public int getStok() {
        return stok;
    }

    /**
     * @param stok the stok to set
     */
    public void setStok(int stok) {
        this.stok = stok;
    }

    /**
     * @return the bahan
     */
    public String getBahan() {
        return bahan;
    }

    /**
     * @param bahan the bahan to set
     */
    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    /**
     * @return the gambar
     */
    public String getGambar() {
        return gambar;
    }

    /**
     * @param gambar the gambar to set
     */
    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    
    public void addtodb() throws SQLException {
        connect = Database.connectDB();
        String insertQuery = "Insert into tbobat(nama, harga, jenis,stok ,bahan, obatPic) values (?,?,?,?,?,?)";
        prepare = connect.prepareStatement(insertQuery);

        prepare.setString(1, this.getNama());
        prepare.setDouble(2, this.getHarga());
        prepare.setString(3, this.getJenis());
        prepare.setInt(4, this.getStok());
        prepare.setString(5, this.getBahan());
        prepare.setString(6, this.getGambar());

        prepare.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tambah Berhasil");
        alert.setContentText("Obat Berhasil di Tambah");
        alert.showAndWait();
    }
    
    public void deleteFromDb(String nama) throws SQLException{
        connect = Database.connectDB();
        String select = "Select * from tbobat where nama='"+ nama + "'";
        prepare =  connect.prepareStatement(select);
        result = prepare.executeQuery();
        result.next();
        int id_obat = result.getInt("id_obat");
        
        String deleteKeranjang = "Delete from tbkeranjang where id_obat=" + id_obat + "";
        prepare = connect.prepareStatement(deleteKeranjang);
        prepare.executeUpdate();
        
        
        String deleteQuery = "Delete from tbobat where id_obat=?";
        prepare = connect.prepareStatement(deleteQuery);
        prepare.setInt(1,id_obat);
        prepare.executeUpdate();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hapus Berhasil");
        alert.setContentText("Obat Berhasil di Hapus");
        alert.showAndWait();
    }
    
    public void updateToDb(String nama,Double harga,String jenis,int stok,String bahan,String obatPic,int Id) throws SQLException{
        connect = Database.connectDB();
        

        String updateQuery = "update tbobat set nama=?,harga=?,jenis=?,stok=?,bahan=?,obatPic=? where id_obat=?";
        
        prepare = connect.prepareStatement(updateQuery);
        
        prepare.setString(1, nama);
        prepare.setDouble(2, harga);
        prepare.setString(3, jenis);
        prepare.setInt(4, stok);
        prepare.setString(5, bahan);
        prepare.setString(6, obatPic);
        prepare.setInt(7, Id);
        
        prepare.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Berhasil");
        alert.setContentText("Obat Berhasil di Edit");
        alert.showAndWait();
    }
}
