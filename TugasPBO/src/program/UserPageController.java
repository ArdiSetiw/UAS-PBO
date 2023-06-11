/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package program;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ardi Setyiawan
 */
public class UserPageController implements Initializable {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public int id_akun;
    
    @FXML
    private Button btnDataObat;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label lblUsername;
    @FXML
    public AnchorPane panelData;
    @FXML
    private HBox hBox;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    
    @FXML
    private Button btnKeranjang;
    @FXML
    public AnchorPane panelObatDetail;
    public Label tes;
    
    private ObservableList<Obat> obatList;
    private ObservableList<Keranjang> cartList;
    @FXML
    private ImageView imgDetail;
    @FXML
    private Label lblNamaDetail;
    @FXML
    private Label lblHargaDetail;
    @FXML
    private Label lblJenisDetail;
    @FXML
    private Label lblStokDetail;
    @FXML
    private Label lblBahanDetail;
    @FXML
    private Label lblStokCart;
    @FXML
    private ImageView btnKurangJumlah;
    @FXML
    private ImageView btnTambahJumlah;
    @FXML
    private Button btnTambahKeKeranjang;
    @FXML
    private GridPane cartGrid;
    @FXML
    private AnchorPane panelCart;
    @FXML
    private Button btnCheckout;
    @FXML
    private Label lblInfoCart;
    @FXML
    private Label lblTotalHarga;
    @FXML
    private TextField txtSearch;
    @FXML
    private VBox obatDetailContainer;
    @FXML
    private AnchorPane btnBack;
    @FXML
    private AnchorPane btnLogout;
    @FXML
    private Button btnRiwayat;
    @FXML
    private AnchorPane panelRiwayat;
    @FXML
    private TableView<riwayat> tvRiwayat;
    @FXML
    private TableColumn<riwayat, String> cNamaRiwayat;
    @FXML
    private TableColumn<riwayat, Integer> cTotalRiwayat;
    @FXML
    private TableColumn<riwayat, String> cTanggalRiwayat;
    @FXML
    private ImageView btnClose;

    
    public void loadList() throws SQLException{
        if (obatList != null){
            obatList.clear();
        }
        
        connect = Database.connectDB();
        String SQL = "SELECT * FROM tbobat where nama like '%" + search + "%' or jenis like '%" + search + "%' or bahan like '%" + search + "%'";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        this.obatList = FXCollections.observableArrayList();
        while(result.next()){
            Obat obat = new Obat(result.getString("nama"),
                                result.getDouble("harga"),
                                result.getString("jenis"),
                                result.getInt("stok"),
                                result.getString("bahan"),
                                result.getString("obatPic"));
            
            obatList.add(obat);
        }
    }
    
    public void loadCart() throws SQLException{
        if (cartList != null){
            cartList.clear();
        }
        
        connect = Database.connectDB();
        String SQL = "SELECT tbobat.nama,tbkeranjang.jumlah,tbkeranjang.harga FROM tbobat JOIN tbkeranjang ON tbobat.id_obat = tbkeranjang.id_obat where id_akun = "+ id_akun +" and bayar = "+ 0 +"";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        this.cartList = FXCollections.observableArrayList();
        while(result.next()){
            Keranjang cart = new Keranjang(result.getString("nama"),result.getInt("jumlah"),result.getInt("harga"));
            
            cartList.add(cart);
        }
    
    }
    
    int stokDetail = 0;
    String namaDetail = "";
    int id_obat;
    public void openObatDetail(Obat obat) throws SQLException{
        
        connect = Database.connectDB();
        String SQL = "SELECT * FROM tbobat where nama='"+ obat.getNama() +"'";
        prepare = connect.prepareStatement(SQL);

        result = prepare.executeQuery(SQL);
        
        if(result.next()){
            Image image = new Image(System.getProperty("user.dir")+"\\src\\gambar\\obat\\"+result.getString("obatPic"));
            imgDetail.setImage(image);
            
            id_obat = result.getInt("id_obat");
            
            lblStokCart.setText("0");
            namaDetail = result.getString("nama");
            lblNamaDetail.setText(result.getString("nama"));
            lblHargaDetail.setText("Rp."+result.getString("harga"));
            lblJenisDetail.setText(result.getString("jenis"));
            lblStokDetail.setText(result.getString("stok"));
        
            lblBahanDetail.setText(result.getString("bahan"));
            
            panelData.setVisible(false);
            panelObatDetail.setVisible(true);
        }
        
    }
    
    private MyListener myListener;
    public void setGrid() throws SQLException{
        loadList();
        if (!obatList.isEmpty()) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Obat obat) {
                    try {
                        openObatDetail(obat);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        grid.getChildren().clear();
        int column = 0;
        int row = 1;
        for (int i = 0; i < obatList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemGrid.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemGridController itemController = fxmlLoader.getController();
                itemController.setData(obatList.get(i),myListener);
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                
                column++;
                grid.add(anchorPane, column, row); //(child,column,row)
                grid.setPrefWidth(225);
                grid.setPrefHeight(225);
                GridPane.setMargin(anchorPane, new Insets(10,10,10,10));
            } catch (IOException ex) {
                Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }
    
    private void setCartTambah(Keranjang cart) throws SQLException{
        connect = Database.connectDB();
        String SQL = "SELECT * FROM tbobat where nama='"+ cart.getNama() +"'";
        prepare = connect.prepareStatement(SQL);
        
        
        
        result = prepare.executeQuery(SQL);
        result.next();
        
        stokDetail = cart.getJumlah();
        
        int currStok = result.getInt("stok");
        id_obat = result.getInt("id_obat");
        if (currStok - 1 >= 0){
            String updateObat = "Update tbobat set stok = "+ (currStok - 1)  +" where id_obat = " + id_obat +"";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateObat);    
            prepare.execute();
            
            String updateKeranjang = "Update tbkeranjang set jumlah = "+ (stokDetail+1) + " where id_akun = "+ id_akun + " and id_obat = "+ result.getInt("id_obat") + " and bayar = 0";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateKeranjang);
            prepare.execute();
            
            stokDetail = 0;
            setGridCart();
        }else{
            System.out.println("Stok melebihi");
        }
    }
    
    private void setCartKurang(Keranjang cart) throws SQLException{
        connect = Database.connectDB();
        String SQL = "SELECT * FROM tbobat where nama='"+ cart.getNama() +"'";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        result.next();
        
        stokDetail = cart.getJumlah();
        
        int currStok = result.getInt("stok");
        id_obat = result.getInt("id_obat");
        if (cart.getJumlah() - 1 > 0){
            String updateObat = "Update tbobat set stok = "+ (currStok + 1)  +" where id_obat = " + id_obat +"";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateObat);    
            prepare.execute();
            
            String updateKeranjang = "Update tbkeranjang set jumlah = "+ (stokDetail-1) + " where id_akun = "+ id_akun + " and id_obat = "+ result.getInt("id_obat") + " and bayar = 0";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateKeranjang);
            prepare.execute();
            
            stokDetail = 0;
            
        }else{
            setCartHapus(cart);
        }
        setGridCart();
    }
    
    private void setCartHapus(Keranjang cart) throws SQLException{
        Alert alert;

        ButtonType yes = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert = new Alert(Alert.AlertType.WARNING,"Hapus " + cart.getNama() + " dari Keranjang?", yes, no);
        alert.setTitle("Tambah");
        Optional<ButtonType> iyah = alert.showAndWait();
        if(iyah.orElse(no) == yes){
            String SQL = "SELECT * FROM tbobat where nama='"+ cart.getNama() +"'";
            prepare = connect.prepareStatement(SQL);
            result = prepare.executeQuery(SQL);
            result.next();
            
            int currStok = result.getInt("stok");
            id_obat = result.getInt("id_obat");
            
            String updateObat = "Update tbobat set stok = "+ (currStok + cart.getJumlah())  +" where id_obat = " + id_obat +"";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateObat);    
            prepare.execute();
            
            String deleteKeranjang = "Delete from tbkeranjang where id_akun="+ id_akun + " and id_obat = " + id_obat +" and bayar = 0";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(deleteKeranjang);    
            prepare.execute();
            
            setGridCart();
            stokDetail = 0;
        }else{
            
        }
    }
    private int totalHarga;
    private cartListener listener;
    public void setGridCart() throws SQLException{
        loadCart();
        totalHarga = 0;
        if (!cartList.isEmpty()) {
            listener = new cartListener() {
                @Override
                public void cartTambah(Keranjang cart){
                    try {
                        setCartTambah(cart);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                @Override
                public void cartKurang(Keranjang cart){
                    try {
                        setCartKurang(cart);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                }
                @Override
                public void cartHapus(Keranjang cart){
                    try {
                        setCartHapus(cart);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        
        int column = 1;
        int row = 0;
        
        cartGrid.getChildren().clear();
        lblInfoCart.setVisible(false);
        for (int i = 0; i < cartList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("cartGrid.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                
                CartGridController cont = fxmlLoader.getController();
                cont.setData(cartList.get(i),listener);
                
                
                
                row++;
                cartGrid.add(anchorPane, column, row); //(child,column,row)
                cartGrid.setPrefWidth(300);
                cartGrid.setPrefHeight(100);
                GridPane.setMargin(anchorPane, new Insets(10,10,10,10));
                totalHarga += cartList.get(i).getJumlah() * cartList.get(i).getHarga();
            
            } catch (IOException ex) {
                Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        lblTotalHarga.setText("Rp."+String.valueOf(totalHarga));
        if(cartGrid.getChildren().isEmpty()){
            lblInfoCart.setVisible(true);
        }
        
    }
    
    public void setUsername(String user) {
        lblUsername.setText(user);
    }
    
    
    public void setImgProfile(String lokasi) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(lokasi));
        imgProfile.setImage(image);
    }
    
    public void setBgBtnDataObat(){
        btnKeranjang.setStyle("-fx-background-color: #1FAF9F; ");
        btnDataObat.setStyle("-fx-background-color: #019786; ");
        btnRiwayat.setStyle("-fx-background-color: #1FAF9F; ");
    }
    
    public void setBgBtnKeranjang(){
        btnKeranjang.setStyle("-fx-background-color: #019786; ");
        btnDataObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnRiwayat.setStyle("-fx-background-color: #1FAF9F; ");
    }
    
    public void setBgBtnRiwayat(){
        btnKeranjang.setStyle("-fx-background-color: #1FAF9F; ");
        btnDataObat.setStyle("-fx-background-color: #1FAF9F; ");
        btnRiwayat.setStyle("-fx-background-color: #019786; ");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            
            setGrid();
            obatDetailContainer.setEffect(new DropShadow(10,Color.BLACK));
        } catch (SQLException ex) {
            Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    


    @FXML
    private void btnDataObat_Clicked(MouseEvent event) {
        setBgBtnDataObat();
        panelData.setVisible(true);
        panelObatDetail.setVisible(false);
        panelCart.setVisible(false);
        panelRiwayat.setVisible(false);
        stokDetail = 0;
    }

    @FXML
    private void btnKeranjang_Clicked(MouseEvent event) throws SQLException {
        setBgBtnKeranjang();
        panelData.setVisible(false);
        panelObatDetail.setVisible(false);
        setGridCart();
        panelCart.setVisible(true);
        panelRiwayat.setVisible(false);
        stokDetail = 0;
    }

    @FXML
    private void btnKurangJumlah_Clicked(MouseEvent event) throws SQLException {
        String ambil_id = "SELECT * FROM tbobat WHERE id_obat = '"+ id_obat +"'";
        prepare = connect.prepareStatement(ambil_id);
        result = prepare.executeQuery();
        result.next();
        if(stokDetail > 0){
            stokDetail--;
        }
        lblStokCart.setText(String.valueOf(stokDetail));
    }

    @FXML
    private void btnTambahJumlah_Clicked(MouseEvent event) throws SQLException {
        String ambil_id = "SELECT * FROM tbobat WHERE id_obat = '"+ id_obat +"'";
        prepare = connect.prepareStatement(ambil_id);
        result = prepare.executeQuery();
        result.next();
        if(stokDetail < result.getInt("stok")){
            stokDetail++;
        }
        lblStokCart.setText(String.valueOf(stokDetail));
    }

    @FXML
    private void btnTambahKeKeranjang_Clicked(MouseEvent event) throws SQLException {
        if (stokDetail ==  0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("PERHATIAN!");
            alert.setContentText("Jumlah Obat Harus Lebih Dari 0");
            alert.showAndWait();
            
            return;
        }
        
        String selectKeranjang = "Select * From tbkeranjang where id_akun = " + id_akun + " and id_obat = "+ id_obat + " and bayar = "+ 0 +"";
        prepare = connect.prepareStatement(selectKeranjang);
        result = prepare.executeQuery();
        if (result.next()){
            int stokDb = result.getInt("jumlah");
            
            String selectObat = "SELECT * FROM tbobat WHERE id_obat = '"+ id_obat +"'";
            prepare = connect.prepareStatement(selectObat);
            result = prepare.executeQuery();
            result.next();
            Obat tempObat = new Obat(result.getString("nama"),result.getDouble("harga"),result.getString("jenis"),result.getInt("stok"),result.getString("bahan"),result.getString("obatPic"));
            
            String updateObat = "Update tbobat set stok = "+ (tempObat.getStok() - stokDetail)  +" where id_obat = " + id_obat +"";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateObat);    
            prepare.execute();
            
            
            
            String updateKeranjang = "Update tbkeranjang set jumlah = "+ (stokDb+stokDetail) + " where id_akun = "+ id_akun + " and id_obat = "+ id_obat + " and bayar = 0";
            connect = Database.connectDB();

            prepare = connect.prepareStatement(updateKeranjang);

            prepare.execute();
            
            
            
            
        }else{
            
            String selectObat = "SELECT * FROM tbobat WHERE id_obat = '"+ id_obat +"'";
            prepare = connect.prepareStatement(selectObat);
            result = prepare.executeQuery();
            result.next();
            
            String updateObat = "Update tbobat set stok = "+ (result.getInt("stok") - stokDetail)  +" where id_obat = " + id_obat +"";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateObat);    
            prepare.execute();
            
            String insertKeranjang = "Insert into tbkeranjang (id_akun,id_obat,jumlah,harga,bayar) values (?,?,?,?,0)";
            connect = Database.connectDB();

            prepare = connect.prepareStatement(insertKeranjang);
            prepare.setInt(1, id_akun);
            prepare.setInt(2, id_obat);
            prepare.setInt(3, stokDetail);
            prepare.setDouble(4, result.getDouble("harga"));

            prepare.execute();
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berhasil di Tambah!");
        alert.setContentText("Obat Berhasil Ditambah ke Keranjang");
        alert.showAndWait();
        
        stokDetail = 0;
        setGrid();
        btnDataObat_Clicked(event);
    }

    @FXML
    private void btnCheckout_Clicked(MouseEvent event) throws SQLException {
        Alert alert;
        
        ButtonType yes = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);
        if(!cartList.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING,"Yakin ingin checkOut ? ", yes, no);
            alert.setTitle("Tambah");
            Optional<ButtonType> iyah = alert.showAndWait();
            if(iyah.orElse(no) == yes){
                String bayar = "UPDATE tbkeranjang SET bayar = 1 WHERE id_akun = ? AND bayar = 0";
                prepare = connect.prepareStatement(bayar);
                prepare.setInt(1, id_akun);
                prepare.execute();
                
                String query = "INSERT INTO tbcheckout(id_akun, totalHarga,tanggal) VALUES(?,?,?)";

                prepare = connect.prepareStatement(query);
                prepare.setInt(1, id_akun);
                prepare.setInt(2, totalHarga);

                Date date = new Date(new java.util.Date().getTime());
                prepare.setDate(3, date);

                prepare.execute();
                
                setGridCart();
            }
        }
        
    }
    String search = "";
    @FXML
    private void txtSearch_Pressed(KeyEvent event) throws SQLException {
        
        search = txtSearch.getText();
        setGrid();
    }

    @FXML
    private void btnBack_Clicked(MouseEvent event) {
        btnDataObat_Clicked(event);
    }

    @FXML
    private void btnLogout_Clicked(MouseEvent event) {
        try {
            btnLogout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root, 814, 508));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void btnRiwayat_Clicked(MouseEvent event) throws SQLException {
        loadRiwayat();
        setBgBtnRiwayat();
        panelData.setVisible(false);
        panelObatDetail.setVisible(false);
        panelCart.setVisible(false);
        panelRiwayat.setVisible(true);
        stokDetail = 0;
    }
    
    private ObservableList<riwayat> riwayatList;
    public void loadRiwayat() throws SQLException{
        if (riwayatList != null){
            riwayatList.clear();
        }
        
        connect = Database.connectDB();
        String SQL = "SELECT tbakun.nama,tbcheckout.totalHarga,tbcheckout.tanggal from tbcheckout join tbakun on tbakun.id_akun = tbcheckout.id_akun where tbcheckout.id_akun = "+ id_akun +"";
        prepare = connect.prepareStatement(SQL);
        result = prepare.executeQuery(SQL);
        this.riwayatList = FXCollections.observableArrayList();
        while(result.next()){
            riwayat rwyt = new riwayat(result.getString("nama"),
                                result.getInt("totalHarga"),
                                result.getString("tanggal"));
            
            riwayatList.add(rwyt);
        }
        
        cNamaRiwayat.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cTotalRiwayat.setCellValueFactory(new PropertyValueFactory<> ("total"));
        cTanggalRiwayat.setCellValueFactory(new PropertyValueFactory<> ("tanggal"));

        tvRiwayat.setItems(riwayatList);
    }

    @FXML
    private void btnClose_Clicked(MouseEvent event) {
        System.exit(0);
    }
    
}
